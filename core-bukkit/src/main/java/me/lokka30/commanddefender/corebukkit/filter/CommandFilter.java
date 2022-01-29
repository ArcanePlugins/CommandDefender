package me.lokka30.commanddefender.corebukkit.filter;

import de.leonhard.storage.Yaml;
import de.leonhard.storage.sections.FlatFileSection;
import me.lokka30.commanddefender.core.Core;
import me.lokka30.commanddefender.core.filter.CommandAccessStatus;
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
import me.lokka30.commanddefender.corebukkit.BukkitCore;
import me.lokka30.commanddefender.corebukkit.util.BukkitUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Locale;
import java.util.Optional;

public class CommandFilter {

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
        return BukkitCore.instance().fileHandler().settings().getData().get("default-command-status", true);
    }

    public void load() {
        // load all command sets from the config
        parseCommandSets();

        // update the tab completion for all online players
        // so that it matches the new command sets
        updateTabCompletionSlowly();
    }

    private void parseCommandSets() {
        // reference to the settings data for cleaner code
        final Yaml settings = BukkitCore.instance().fileHandler().settings().getData();

        // iterate thru all command sets in the settings file
        settings.getSection("command-sets").singleLayerKeySet().stream()

                // make sure the command set is enabled
                .filter(identifier -> !settings.get("command-sets." + identifier + ".enabled", false))

                // feed it to the individual parse method
                .forEach(this::parseCommandSet);
    }

    private void parseCommandSet(final @NotNull String identifier) {
        final Yaml settings = BukkitCore.instance().fileHandler().settings().getData();
        final String path = "command-sets." + identifier;

        final CommandAccessStatus type;
        final HashSet<Condition> conditions;
        final HashSet<Action> actions;
        final HashSet<PreProcessOption> preProcessOptions = new HashSet<>();
        final HashSet<PostProcessOption> postProcessOptions = new HashSet<>();
        final double conditionsPercentageRequired = settings.get(path + ".conditions.percentage-required", 0.0);

        switch (settings.get(path + ".type", "DENY").toUpperCase(Locale.ROOT)) {
            case "DENY" -> type = CommandAccessStatus.DENY;
            case "ALLOW" -> type = CommandAccessStatus.ALLOW;
            default -> {
                type = CommandAccessStatus.DENY;
                BukkitCore.instance().logger().error(
                        "Command set '&b" + identifier + "&7' has an invalid &btype&7 specified, expecting '&b" +
                                "ALLOW&7' or '&bDENY&7'. CommandDefender will assume this set is in &bDENY&7 mode. " +
                                "Fix this ASAP.");
            }
        }

        conditions = parseCommandSetConditions(identifier);
        actions = parseCommandSetActions(identifier);

        parseCommandSetOptions(identifier).forEach(option -> {
            if(option instanceof PreProcessOption) {
                preProcessOptions.add((PreProcessOption) option);
            } else if(option instanceof PostProcessOption) {
                postProcessOptions.add((PostProcessOption) option);
            } else {
                throw new IllegalStateException(option.toString());
            }
        });

        commandSets.add(new CommandSet(
                identifier,
                type,
                conditions,
                conditionsPercentageRequired,
                actions,
                preProcessOptions,
                postProcessOptions
        ));
    }

    private HashSet<Condition> parseCommandSetConditions(final @NotNull String identifier) {
        final Yaml settings = BukkitCore.instance().fileHandler().settings().getData();
        final String path = "command-sets." + identifier + ".conditions";
        final FlatFileSection section = settings.getSection(path);

        final HashSet<Condition> conditions = new HashSet<>();
        for(ConditionHandler conditionHandler : core.conditionHandlers()) {
            final Optional<Condition> condition = conditionHandler.parse(section);
            if(condition.isEmpty()) continue;
            conditions.add(condition.get());
        }
        return conditions;
    }

    private HashSet<Action> parseCommandSetActions(final @NotNull String identifier) {
        final Yaml settings = BukkitCore.instance().fileHandler().settings().getData();
        final String path = "command-sets." + identifier + ".actions";
        final FlatFileSection section = settings.getSection(path);

        final HashSet<Action> actions = new HashSet<>();
        for(ActionHandler actionHandler : core.actionHandlers()) {
            final Optional<Action> action = actionHandler.parse(section);
            if(action.isEmpty()) continue;
            actions.add(action.get());
        }
        return actions;
    }

    private HashSet<Option> parseCommandSetOptions(final @NotNull String identifier) {
        final Yaml settings = BukkitCore.instance().fileHandler().settings().getData();
        final String path = "command-sets." + identifier + ".options";
        final FlatFileSection section = settings.getSection(path);

        final HashSet<Option> options = new HashSet<>();
        for(OptionHandler optionHandler : core.optionHandlers()) {
            final Optional<Option> option = optionHandler.parse(settings.getSection(path));
            if(option.isEmpty()) continue;
            options.add(option.get());
        }
        return options;
    }

    // TODO Update to universal
    //
    // We want to do this slowly as to not haul
    // the server with update requests
    private void updateTabCompletionSlowly() {
        if(!BukkitUtils.serverHasPlayerCommandSendEvent()) return;

        final LinkedList<Player> players = new LinkedList<>(Bukkit.getOnlinePlayers());
        if(players.size() == 0) return;
        final int[] index = {0}; // this is necessary due to the inner class below

        new BukkitRunnable() {
            @Override
            public void run() {
                players.get(index[0]).updateCommands();
                index[0]++;
            }
        }.runTaskTimer(BukkitCore.instance(), 1L, 5L);
        // every quarter of a second, CD will update each player's tab completion commands list.
    }
}
