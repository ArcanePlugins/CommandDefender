package me.lokka30.commanddefender.core.filter;

import de.leonhard.storage.Yaml;
import me.lokka30.commanddefender.core.Commons;
import me.lokka30.commanddefender.core.filter.set.CommandSet;
import me.lokka30.commanddefender.core.filter.set.CommandSetPreset;
import me.lokka30.commanddefender.core.filter.set.action.Action;
import me.lokka30.commanddefender.core.filter.set.action.ActionHandler;
import me.lokka30.commanddefender.core.filter.set.condition.Condition;
import me.lokka30.commanddefender.core.filter.set.condition.ConditionHandler;
import me.lokka30.commanddefender.core.filter.set.option.Option;
import me.lokka30.commanddefender.core.filter.set.option.OptionHandler;
import me.lokka30.commanddefender.core.filter.set.option.postprocess.PostProcessOption;
import me.lokka30.commanddefender.core.filter.set.option.postprocess.type.ActionPredicateOverride;
import me.lokka30.commanddefender.core.filter.set.option.preprocess.PreProcessOption;
import me.lokka30.commanddefender.core.filter.set.option.preprocess.type.BypassPermission;
import me.lokka30.commanddefender.core.filter.set.option.preprocess.type.FilterContext;
import me.lokka30.commanddefender.core.util.universal.UniversalPlayer;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public final class CommandFilter {

    private final LinkedList<CommandSetPreset> presets = new LinkedList<>();
    private final LinkedList<CommandSet> commandSets = new LinkedList<>();

    public boolean canAccess(final @NotNull FilterContextType contextType, final @NotNull UniversalPlayer player, @NotNull final String[] args) {
        commandSetIterator:
        for(final CommandSet set : commandSets) {

            // pre process options
            for(final PreProcessOption option : set.preProcessOptions()) {

                if(option instanceof BypassPermission.BypassPermissionOption bpo) {
                    // Check Bypass Permission option
                    if(player.hasPermission(bpo.bypassPermission())) {
                        continue commandSetIterator;
                    }
                } else if(option instanceof FilterContext.FilterContextOption fco) {
                    // Check Filter Context Type option
                    boolean contains = false;
                    for(FilterContextType contextTypeInArray : fco.contextTypes()) {
                        if (contextTypeInArray.equals(contextType)) {
                            contains = true;
                            break;
                        }
                    }
                    if(!contains) {
                        continue commandSetIterator;
                    }
                } else {
                    Commons.core.logger().error("Unexpected pre-process option " + option.getClass().getSimpleName() + "&7'.");
                }
            }

            // processing
            final CommandAccessStatus status = set.getAccessStatus(player, args);

            // post-process options
            boolean ignoreFilteringContext = false, ignoreCommandAccessStatus = false;

            for(final PostProcessOption option : set.postProcessOptions()) {
                if(option instanceof ActionPredicateOverride.ActionPredicateOverrideOption apoo) {
                    // Action Predicate Override option
                    ignoreFilteringContext = apoo.ignoreFilteringContext();
                    ignoreCommandAccessStatus = apoo.ignoreCommandAccessStatus();
                } else {
                    Commons.core.logger().error("Unexpected post-process option '&b" + option.getClass().getSimpleName() + "&7'.");
                }
            }

            /* time to run the actions (if the checks agree with it) */

            final boolean statusCheck = status == CommandAccessStatus.DENY || (ignoreCommandAccessStatus && status == CommandAccessStatus.ALLOW);
            final boolean filterContextCheck = contextType == FilterContextType.COMMAND_EXECUTION || ignoreFilteringContext;

            if(statusCheck && filterContextCheck) {
                // run the actions.
                set.actions().forEach(action -> action.run(player));
            }

            /* return the status of the command set */
            if(status != CommandAccessStatus.UNKNOWN) {
                return (status == CommandAccessStatus.ALLOW);
            }
        }
        // command sets don't specify the command -> return default status:
        return Commons.core.fileHandler().settings().data().get("default-command-status", true);
    }

    public void load() {
        // load all command sets from the config
        parseCommandSets();

        // update the tab completion for all online players
        // so that it matches the new command sets
        Commons.core.updateTabCompletionForAllPlayers();
    }

    private void parseCommandSets() {
        // reference to the settings data for cleaner code
        final Yaml settings = Commons.core.fileHandler().settings().data();

        // iterate thru all presets in the settings file
        presets.clear();
        settings.getSection("presets").singleLayerKeySet().forEach(this::parsePreset);

        // iterate thru all command sets in the settings file
        commandSets.clear();
        settings.getSection("command-sets").singleLayerKeySet().stream()

                // make sure the command set is enabled
                .filter(identifier -> !settings.get("command-sets." + identifier + ".enabled", false))

                // feed it to the individual parse method
                .forEach(this::parseCommandSet);
    }

    private void parsePreset(final @NotNull String identifier) {
        final Yaml settings = Commons.core.fileHandler().settings().data();
        final String path = "presets." + identifier;
        presets.add(new CommandSetPreset(identifier, settings.getSection(path)));
    }

    private void parseCommandSet(final @NotNull String identifier) {
        final Yaml settings = Commons.core.fileHandler().settings().data();
        final String path = "command-sets." + identifier;

        final CommandAccessStatus type;
        final double conditionsPercentageRequired = settings.get(path + ".conditions.percentage-required", 100.0D);

        switch (settings.get(path + ".type", "DENY").toUpperCase(Locale.ROOT)) {
            case "DENY" -> type = CommandAccessStatus.DENY;
            case "ALLOW" -> type = CommandAccessStatus.ALLOW;
            default -> {
                type = CommandAccessStatus.DENY;
                Commons.core.logger().error(
                        "Command set '&b" + identifier + "&7' has an invalid &btype&7 specified, expecting '&b" +
                                "ALLOW&7' or '&bDENY&7'. CommandDefender will assume this set is in &bDENY&7 mode. " +
                                "Fix this ASAP.");
            }
        }

        final CommandSet commandSet = new CommandSet(
                /* General */
                identifier,
                type,
                new HashSet<>(), //presets

                /* Conditions */
                new HashSet<>(), // conditions
                conditionsPercentageRequired,

                /* Actions */
                new HashSet<>(), // actions

                /* Options */
                new HashSet<>(), // preProcessOptions
                new HashSet<>() // postProcessOptions
        );

        parseCommandSetPresets(commandSet, identifier);
        parseCommandSetConditions(commandSet, identifier);
        parseCommandSetActions(commandSet, identifier);
        parseCommandSetOptions(commandSet, identifier);

        commandSets.add(commandSet);
    }

    private void parseCommandSetPresets(final @NotNull CommandSet commandSet, final @NotNull String identifier) {
        final Yaml settings = Commons.core.fileHandler().settings().data();
        final List<String> presetIds = settings.getStringList("command-sets." + identifier + ".use-presets");

        presets.forEach(preset -> {
            if(presetIds.contains(preset.identifier())) {
                if(commandSet.presets().contains(preset)) {
                    Commons.core.logger().error("Duplicate preset '" + preset.identifier() + "' specified for " +
                            "command set '" + commandSet.identifier() + "'. Remove duplicate entries ASAP.");
                } else {
                    commandSet.presets().add(preset);
                }
            }
        });
    }

    private void parseCommandSetConditions(final @NotNull CommandSet commandSet, final @NotNull String identifier) {
        final Yaml settings = Commons.core.fileHandler().settings().data();

        final HashSet<Condition> conditions = new HashSet<>();
        for(ConditionHandler conditionHandler : Commons.conditionHandlers) {
            final Optional<Condition> condition = conditionHandler.parse(
                    commandSet,
                    settings.getSection("command-sets." + identifier)
            );
            if(condition.isEmpty()) continue;
            commandSet.conditions().add(condition.get());
        }
    }

    private void parseCommandSetActions(final @NotNull CommandSet commandSet, final @NotNull String identifier) {
        final Yaml settings = Commons.core.fileHandler().settings().data();

        final HashSet<Action> actions = new HashSet<>();
        for(ActionHandler actionHandler : Commons.actionHandlers) {
            final Optional<Action> action = actionHandler.parse(
                    commandSet,
                    settings.getSection("command-sets." + identifier)
            );
            if(action.isEmpty()) continue;
            commandSet.actions().add(action.get());
        }
    }

    private void parseCommandSetOptions(final @NotNull CommandSet commandSet, final @NotNull String identifier) {
        final Yaml settings = Commons.core.fileHandler().settings().data();

        for(OptionHandler optionHandler : Commons.optionHandlers) {
            final Optional<Option> option = optionHandler.parse(
                    commandSet,
                    settings.getSection("command-sets." + identifier)
            );
            if(option.isEmpty()) continue;
            if(option.get() instanceof PreProcessOption) {
                commandSet.preProcessOptions().add((PreProcessOption) option.get());
            } else if(option.get() instanceof PostProcessOption) {
                commandSet.postProcessOptions().add((PostProcessOption) option.get());
            } else {
                throw new IllegalStateException(option.toString());
            }
        }
    }
}
