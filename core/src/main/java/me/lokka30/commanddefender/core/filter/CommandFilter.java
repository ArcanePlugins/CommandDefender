package me.lokka30.commanddefender.core.filter;

import de.leonhard.storage.Yaml;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import me.lokka30.commanddefender.core.Commons;
import me.lokka30.commanddefender.core.debug.DebugCategory;
import me.lokka30.commanddefender.core.debug.DebugHandler;
import me.lokka30.commanddefender.core.filter.set.CommandSet;
import me.lokka30.commanddefender.core.filter.set.CommandSetPreset;
import me.lokka30.commanddefender.core.filter.set.action.Action;
import me.lokka30.commanddefender.core.filter.set.action.ActionHandler;
import me.lokka30.commanddefender.core.filter.set.condition.Condition;
import me.lokka30.commanddefender.core.filter.set.condition.ConditionHandler;
import me.lokka30.commanddefender.core.filter.set.option.Option;
import me.lokka30.commanddefender.core.filter.set.option.OptionHandler;
import me.lokka30.commanddefender.core.filter.set.option.postprocess.ActionPredicateOverride;
import me.lokka30.commanddefender.core.filter.set.option.preprocess.BypassPermission;
import me.lokka30.commanddefender.core.filter.set.option.preprocess.Context;
import me.lokka30.commanddefender.core.util.CoreUtils;
import me.lokka30.commanddefender.core.util.universal.UniversalPlayer;
import org.jetbrains.annotations.NotNull;

public final class CommandFilter {

    private final LinkedList<CommandSetPreset> presets = new LinkedList<>();
    private final LinkedList<CommandSet> commandSets = new LinkedList<>();

    public boolean canAccess(
        final @NotNull FilterContextType contextType,
        final @NotNull UniversalPlayer player,
        final @NotNull String[] args
    ) {
        final boolean debugLog = DebugHandler.isDebugCategoryEnabled(
            DebugCategory.COMMAND_FILTER_ACCESSIBILITY);
        if (debugLog) {
            Commons.core().logger().debug(DebugCategory.COMMAND_FILTER_ACCESSIBILITY, String.format(
                "Checking if &f%s&7 can access &8[&b%s&8]&7 with &bFilterContextType.%s&7.",
                player.name(),
                String.join("&7, &b", args),
                contextType
            ));
        }

        if (
            Commons.core().fileHandler().advancedSettings().data()
                .get("operator-status-bypasses-processing", true)
        ) {
            if (player.isOp()) {
                if (debugLog) {
                    Commons.core().logger().debug(DebugCategory.COMMAND_FILTER_ACCESSIBILITY, String.format(
                        "&f%s&7 is an operator! Bypassing processing.",
                        player.name()
                    ));
                }
                return true;
            }
        }

        commandSetIterator:
        for (final CommandSet set : commandSets) {

            // pre process options
            for (final Option option : set.preProcessOptions()) {

                if (option instanceof BypassPermission.BypassPermissionOption bpo) {
                    // Check Bypass Permission option
                    if (player.hasPermission(bpo.bypassPermission())) {
                        if (debugLog) {
                            Commons.core().logger()
                                .debug(DebugCategory.COMMAND_FILTER_ACCESSIBILITY, String.format(
                                    "&f%s&7 has bypass permission for command set &b%s&7.",
                                    player.name(),
                                    set.identifier()
                                ));
                        }
                        continue commandSetIterator;
                    }
                } else if (option instanceof Context.ContextOption fco) {
                    // Check Filter Context Type option
                    boolean contains = false;
                    for (FilterContextType contextTypeInArray : fco.contextTypes()) {
                        if (contextTypeInArray.equals(contextType)) {
                            contains = true;
                            break;
                        }
                    }
                    if (!contains) {
                        if (debugLog) {
                            Commons.core().logger()
                                .debug(DebugCategory.COMMAND_FILTER_ACCESSIBILITY, String.format(
                                    "Filter context &b%s&7 is not used for command set &b%s &8(&7available: &b%s&8)&7.",
                                    contextType,
                                    set.identifier(),
                                    Arrays.toString(fco.contextTypes())
                                ));
                        }
                        continue commandSetIterator;
                    }
                } else {
                    Commons.core().logger().error(
                        "Unexpected pre-process option &b" + option.getClass().getSimpleName()
                            + "&7'!");
                }
            }

            // processing
            final CommandAccessStatus status = set.getAccessStatus(player, args);
            if (debugLog) {
                Commons.core().logger()
                    .debug(DebugCategory.COMMAND_FILTER_ACCESSIBILITY, String.format(
                        "Status for command set &b%s&7 is %s&7.",
                        set.identifier(),
                        switch(status) {
                            case ALLOW -> "&aallow";
                            case UNKNOWN -> "&eunknown";
                            case DENY -> "&cdeny";
                        }
                    ));
            }

            // post-process options
            boolean ignoreFilteringContext = false, ignoreCommandAccessStatus = false;

            for (final Option option : set.postProcessOptions()) {
                if (option instanceof ActionPredicateOverride.ActionPredicateOverrideOption apoo) {
                    // Action Predicate Override option
                    ignoreFilteringContext = apoo.ignoreFilteringContext();
                    ignoreCommandAccessStatus = apoo.ignoreCommandAccessStatus();
                    if (debugLog) {
                        Commons.core().logger()
                            .debug(DebugCategory.COMMAND_FILTER_ACCESSIBILITY, String.format(
                                "Post processing options for command set &b%s&7 are "
                                    + "&8[&7IgnoreFilteringContext: %s&8]&7, "
                                    + "&8[&7IgnoreCommandAccessStatus: %s&8]&7.",
                                set.identifier(),
                                ignoreFilteringContext ? "&aYes" : "&cNo",
                                ignoreCommandAccessStatus ? "&aYes" : "&cNo"
                            ));
                    }
                } else {
                    Commons.core().logger().error(
                        "Unexpected post-process option '&b" + option.getClass().getSimpleName()
                            + "&7'!");
                }
            }

            /* time to run the actions (if the checks agree with it) */

            final boolean statusCheck =
                status == CommandAccessStatus.DENY || (ignoreCommandAccessStatus
                    && status == CommandAccessStatus.ALLOW);
            final boolean filterContextCheck =
                contextType == FilterContextType.COMMAND_EXECUTION || ignoreFilteringContext;

            if (statusCheck && filterContextCheck) {
                // run the actions.
                if (debugLog) {
                    Commons.core().logger()
                        .debug(DebugCategory.COMMAND_FILTER_ACCESSIBILITY, String.format(
                            "Running actions for command set &b%s&7, since the status and filter context checks passed.",
                            set.identifier()
                        ));
                }
                set.actions().forEach(action -> action.run(player, args));
            }

            /* return the status of the command set */
            if (status != CommandAccessStatus.UNKNOWN) {
                return (status == CommandAccessStatus.ALLOW);
            }
        }

        // command sets don't specify the command -> return default status:

        if (debugLog) {
            Commons.core().logger().debug(DebugCategory.COMMAND_FILTER_ACCESSIBILITY, String.format(
                "Returning default command status for &f%s&7.",
                player.name()
            ));
        }

        return Commons.core().fileHandler().settings().data().get("default-command-status", "DENY")
            .equalsIgnoreCase("ALLOW");
    }

    public void load() {
        // load all command sets from the config
        Commons.core().logger().info("Parsing command sets...");
        parseCommandSets();

        // update the tab completion for all online players
        // so that it matches the new command sets
        Commons.core().logger().info("Updating command suggestions for all online players...");
        Commons.core().updateTabCompletionForAllPlayers();
    }

    private void parseCommandSets() {

        final boolean debugLog = DebugHandler.isDebugCategoryEnabled(
            DebugCategory.COMMAND_FILTER_PARSING);

        final Yaml settings = Commons.core().fileHandler().settings().data();

        // iterate thru all presets in the settings file
        presets.clear();
        settings.getSection("presets").singleLayerKeySet().forEach(this::parsePreset);

        // iterate thru all command sets in the settings file
        commandSets.clear();
        settings.getSection("command-sets").singleLayerKeySet().stream()

            // make sure the command set is enabled
            .filter(identifier -> settings.get("command-sets." + identifier + ".enabled", false))

            // feed it to the individual parse method
            .forEach(this::parseCommandSet);
    }

    private void parsePreset(final @NotNull String identifier) {

        final Yaml settings = Commons.core().fileHandler().settings().data();
        final String path = "presets." + identifier;

        presets.add(new CommandSetPreset(identifier, settings.getSection(path)));

        if (DebugHandler.isDebugCategoryEnabled(DebugCategory.COMMAND_FILTER_PARSING)) {
            Commons.core().logger().debug(DebugCategory.COMMAND_FILTER_PARSING, String.format(
                "Loaded preset &b%s&7, there are now &b%s&7 presets.",
                identifier,
                presets.size()
            ));
        }
    }

    private void parseCommandSet(final @NotNull String identifier) {

        if (DebugHandler.isDebugCategoryEnabled(DebugCategory.COMMAND_FILTER_PARSING)) {
            Commons.core().logger().debug(DebugCategory.COMMAND_FILTER_PARSING, String.format(
                "Started parsing command set &b%s&7...",
                identifier
            ));
        }

        final Yaml settings = Commons.core().fileHandler().settings().data();
        final String path = "command-sets." + identifier;

        final CommandAccessStatus type;
        final double conditionsPercentageRequired = CoreUtils.ensureBetween(0.0, 1.0,
            settings.get(path + ".conditions.percentage-required", 100.0D) / 100);

        switch (settings.get(path + ".type", "DENY").toUpperCase(Locale.ROOT)) {
            case "DENY" -> type = CommandAccessStatus.DENY;
            case "ALLOW" -> type = CommandAccessStatus.ALLOW;
            default -> {
                type = CommandAccessStatus.DENY;
                Commons.core().logger().error(
                    "Command set '&b" + identifier
                        + "&7' has an invalid &btype&7 specified, was expecting '&b" +
                        "ALLOW&7' or '&bDENY&7'. CommandDefender will assume this set is in &bDENY&7 mode "
                        +
                        "for security purposes. Fix this ASAP.");
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

        if (DebugHandler.isDebugCategoryEnabled(DebugCategory.COMMAND_FILTER_PARSING)) {
            Commons.core().logger().debug(DebugCategory.COMMAND_FILTER_PARSING, String.format(
                "Finished parsing command set '&b%s&7', there are now &b%s&7 command sets loaded.",
                identifier,
                commandSets.size()
            ));
        }
    }

    private void parseCommandSetPresets(final @NotNull CommandSet commandSet,
        final @NotNull String identifier) {

        final Yaml settings = Commons.core().fileHandler().settings().data();

        final List<String> presetIds = settings.getStringList(
            "command-sets." + identifier + ".use-presets");

        if (DebugHandler.isDebugCategoryEnabled(DebugCategory.COMMAND_FILTER_PARSING)) {
            Commons.core().logger().debug(DebugCategory.COMMAND_FILTER_PARSING, String.format(
                "Command set &b%s&7 specifies &b%s&7 preset IDs: &8[&b%s&8]&7.",
                identifier,
                presetIds.size(),
                String.join("&7, &b", presetIds)
            ));
        }

        presetIds.forEach(presetId -> {
            Optional<CommandSetPreset> preset = presets.stream()
                .filter(val -> val.identifier().equals(presetId))
                .findFirst();

            if(preset.isPresent()) {
                if(commandSet.presets().stream().anyMatch(val -> val.identifier().equals(presetId))) {
                    Commons.core().logger()
                        .warning("Duplicate preset '&b" + presetId + "&7' specified for " +
                            "command set '&b" + commandSet.identifier()
                            + "&7'.");
                } else {
                    commandSet.presets().add(preset.get());
                }
            } else {
                Commons.core().logger().error("Command set '&b" + identifier
                    + "&7' specifies a preset that doesn't exist: '&b" + presetId + "&7'. Fix this ASAP.");
            }
        });
    }

    private void parseCommandSetConditions(final @NotNull CommandSet commandSet,
        final @NotNull String identifier) {

        final Yaml settings = Commons.core().fileHandler().settings().data();

        for (ConditionHandler conditionHandler : Commons.conditionHandlers) {
            final Optional<Condition> condition = conditionHandler.parse(
                commandSet,
                settings.getSection("command-sets." + identifier)
            );
            if (condition.isEmpty()) {
                continue;
            }
            commandSet.conditions().add(condition.get());

            if (DebugHandler.isDebugCategoryEnabled(DebugCategory.COMMAND_FILTER_PARSING)) {
                Commons.core().logger().debug(DebugCategory.COMMAND_FILTER_PARSING, String.format(
                    "Parsed condition &b%s&7 for command set &b%s&7. It now has &b%s&7 conditions.",
                    condition.get().getClass().getSimpleName(),
                    identifier,
                    commandSet.conditions().size()
                ));
            }
        }
    }

    private void parseCommandSetActions(final @NotNull CommandSet commandSet,
        final @NotNull String identifier) {

        final Yaml settings = Commons.core().fileHandler().settings().data();

        for (ActionHandler actionHandler : Commons.actionHandlers) {
            final Optional<Action> action = actionHandler.parse(
                commandSet,
                settings.getSection("command-sets." + identifier)
            );
            if (action.isEmpty()) {
                continue;
            }
            commandSet.actions().add(action.get());

            if (DebugHandler.isDebugCategoryEnabled(DebugCategory.COMMAND_FILTER_PARSING)) {
                Commons.core().logger().debug(DebugCategory.COMMAND_FILTER_PARSING, String.format(
                    "Parsed action &b%s&7 for command set &b%s&7. It now has &b%s&7 actions.",
                    action.get().getClass().getSimpleName(),
                    identifier,
                    commandSet.actions().size()
                ));
            }
        }
    }

    private void parseCommandSetOptions(final @NotNull CommandSet commandSet,
        final @NotNull String identifier) {

        final Yaml settings = Commons.core().fileHandler().settings().data();
        final boolean debugLog = DebugHandler.isDebugCategoryEnabled(
            DebugCategory.COMMAND_FILTER_PARSING);

        for (OptionHandler optionHandler : Commons.optionHandlers) {
            final Optional<Option> option = optionHandler.parse(
                commandSet,
                settings.getSection("command-sets." + identifier)
            );
            if (option.isEmpty()) {
                continue;
            }
            final Option got = option.get();

            if (debugLog) {
                Commons.core().logger().debug(DebugCategory.COMMAND_FILTER_PARSING, String.format(
                    "Option '&b%s&7' processing stage is '&b%s&7'.",
                    got.getClass().getSimpleName(),
                    CoreUtils.formatConstantTypeStr(got.processingStage().toString())
                ));
            }

            switch (got.processingStage()) {
                case PRE_PROCESS:
                    commandSet.preProcessOptions().add(got); break;
                case POST_PROCESS:
                    commandSet.postProcessOptions().add(got); break;
                default:
                    throw new IllegalStateException("Unexpected state: " + got.processingStage());
            }

            if (debugLog) {
                Commons.core().logger().debug(DebugCategory.COMMAND_FILTER_PARSING, String.format(
                    "Parsed option '&b%s&7' for command set '&b%s&7'. " +
                        "There are currently: pre-process options - &b%s&7; post-process options - &b%s&7.",
                    got.getClass().getSimpleName(),
                    identifier,
                    commandSet.preProcessOptions().size(),
                    commandSet.postProcessOptions().size()
                ));
            }
        }
    }

}
