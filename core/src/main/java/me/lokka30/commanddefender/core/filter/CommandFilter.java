package me.lokka30.commanddefender.core.filter;

import de.leonhard.storage.Yaml;
import me.lokka30.commanddefender.core.Core;
import me.lokka30.commanddefender.core.filter.set.CommandSet;
import me.lokka30.commanddefender.core.filter.set.action.Action;
import me.lokka30.commanddefender.core.filter.set.action.ActionHandler;
import me.lokka30.commanddefender.core.filter.set.condition.Condition;
import me.lokka30.commanddefender.core.filter.set.condition.ConditionHandler;
import me.lokka30.commanddefender.core.filter.set.option.Option;
import me.lokka30.commanddefender.core.filter.set.option.OptionHandler;
import me.lokka30.commanddefender.core.filter.set.option.postprocess.PostProcessOption;
import me.lokka30.commanddefender.core.filter.set.option.preprocess.PreProcessOption;
import me.lokka30.commanddefender.core.player.UniversalPlayer;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Locale;
import java.util.Optional;

public final class CommandFilter {

    private final Core core;
    public CommandFilter(final Core core) { this.core = core; }

    private final LinkedList<CommandSet> commandSets = new LinkedList<>();

    public boolean canAccess(final @NotNull UniversalPlayer player, @NotNull final String[] args) {
        for(final CommandSet set : commandSets) {
            final CommandAccessStatus status = set.getAccessStatus(player, args);
            if(status != CommandAccessStatus.UNKNOWN) {
                return (status == CommandAccessStatus.ALLOW);
            }
        }
        // command sets don't specify the command -> return default status:
        return core.fileHandler().settings().data().get("default-command-status", true);
    }

    public void load() {
        // load all command sets from the config
        parseCommandSets();

        // update the tab completion for all online players
        // so that it matches the new command sets
        core.updateTabCompletionForAllPlayers();
    }

    private void parseCommandSets() {
        // reference to the settings data for cleaner code
        final Yaml settings = core.fileHandler().settings().data();

        // iterate thru all command sets in the settings file
        settings.getSection("command-sets").singleLayerKeySet().stream()

                // make sure the command set is enabled
                .filter(identifier -> !settings.get("command-sets." + identifier + ".enabled", false))

                // feed it to the individual parse method
                .forEach(this::parseCommandSet);
    }

    private void parseCommandSet(final @NotNull String identifier) {
        final Yaml settings = core.fileHandler().settings().data();
        final String path = "command-sets." + identifier;

        final CommandAccessStatus type;
        final double conditionsPercentageRequired = settings.get(path + ".conditions.percentage-required", 0.0);

        switch (settings.get(path + ".type", "DENY").toUpperCase(Locale.ROOT)) {
            case "DENY" -> type = CommandAccessStatus.DENY;
            case "ALLOW" -> type = CommandAccessStatus.ALLOW;
            default -> {
                type = CommandAccessStatus.DENY;
                core.logger().error(
                        "Command set '&b" + identifier + "&7' has an invalid &btype&7 specified, expecting '&b" +
                                "ALLOW&7' or '&bDENY&7'. CommandDefender will assume this set is in &bDENY&7 mode. " +
                                "Fix this ASAP.");
            }
        }

        final CommandSet commandSet = new CommandSet(
                identifier,
                type,
                new HashSet<>(),
                conditionsPercentageRequired,
                new HashSet<>(),
                new HashSet<>(),
                new HashSet<>()
        );

        parseCommandSetConditions(commandSet, identifier);
        parseCommandSetActions(commandSet, identifier);
        parseCommandSetOptions(commandSet, identifier);

        commandSets.add(commandSet);
    }

    private void parseCommandSetConditions(final @NotNull CommandSet commandSet, final @NotNull String identifier) {
        final Yaml settings = core.fileHandler().settings().data();
        final String path = "command-sets." + identifier + ".conditions";

        final HashSet<Condition> conditions = new HashSet<>();
        for(ConditionHandler conditionHandler : core.conditionHandlers()) {
            final Optional<Condition> condition = conditionHandler.parse(commandSet, settings.getSection(path));
            if(condition.isEmpty()) continue;
            commandSet.conditions().add(condition.get());
        }
    }

    private void parseCommandSetActions(final @NotNull CommandSet commandSet, final @NotNull String identifier) {
        final Yaml settings = core.fileHandler().settings().data();
        final String path = "command-sets." + identifier + ".actions";

        final HashSet<Action> actions = new HashSet<>();
        for(ActionHandler actionHandler : core.actionHandlers()) {
            final Optional<Action> action = actionHandler.parse(commandSet, settings.getSection(path));
            if(action.isEmpty()) continue;
            commandSet.actions().add(action.get());
        }
    }

    private void parseCommandSetOptions(final @NotNull CommandSet commandSet, final @NotNull String identifier) {
        final Yaml settings = core.fileHandler().settings().data();
        final String path = "command-sets." + identifier + ".options";

        for(OptionHandler optionHandler : core.optionHandlers()) {
            final Optional<Option> option = optionHandler.parse(commandSet, settings.getSection(path));
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
