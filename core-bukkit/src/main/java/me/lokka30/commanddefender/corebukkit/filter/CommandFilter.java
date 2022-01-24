package me.lokka30.commanddefender.corebukkit.filter;

import de.leonhard.storage.Yaml;
import me.lokka30.commanddefender.core.Core;
import me.lokka30.commanddefender.core.filter.CommandAccessStatus;
import me.lokka30.commanddefender.core.filter.set.CommandSet;
import me.lokka30.commanddefender.core.filter.set.action.Action;
import me.lokka30.commanddefender.core.filter.set.condition.Condition;
import me.lokka30.commanddefender.core.filter.set.option.Option;
import me.lokka30.commanddefender.core.player.UniversalPlayer;
import me.lokka30.commanddefender.corebukkit.BukkitCore;
import me.lokka30.commanddefender.corebukkit.util.BukkitUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Locale;

public class CommandFilter {

    private final Core core;
    public CommandFilter(final Core core) { this.core = core; }

    private final LinkedList<CommandSet> commandSets = new LinkedList<>();

    public boolean canAccess(final UniversalPlayer player, final String[] args) {
        for(final CommandSet set : commandSets) {
            final CommandAccessStatus status = set.getAccessStatus(player, args);
            if(status != CommandAccessStatus.UNKNOWN) {
                return (status == CommandAccessStatus.ALLOW);
            }
        }
        // command sets don't specify the command -> return default status:
        return BukkitCore.getInstance().getFileHandler().getSettings().getData().get("default-command-status", true);
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
        final Yaml settings = BukkitCore.getInstance().getFileHandler().getSettings().getData();

        // iterate thru all command sets in the settings file
        settings.getSection("command-sets").singleLayerKeySet().stream()

                // make sure the command set is enabled
                .filter(identifier -> !settings.get("command-sets." + identifier + ".enabled", false))

                // feed it to the individual parse method
                .forEach(this::parseCommandSet);
    }

    private void parseCommandSet(final String identifier) {
        final Yaml settings = BukkitCore.getInstance().getFileHandler().getSettings().getData();
        final String path = "command-sets." + identifier;

        final CommandAccessStatus type;
        final HashSet<Condition> conditions;
        final HashSet<Action> actions;
        final HashSet<Option> options;
        final double conditionsPercentageRequired = settings.get(path + ".conditions.percentage-required", 0.0);

        switch(settings.get(path + ".type", "DENY").toUpperCase(Locale.ROOT)) {
            case "DENY":
                type = CommandAccessStatus.DENY;
                break;
            case "ALLOW":
                type = CommandAccessStatus.ALLOW;
                break;
            default:
                type = CommandAccessStatus.DENY;
                BukkitCore.getInstance().getCoreLogger().error(
                        "Command set '&b" + identifier + "&7' has an invalid &btype&7 specified, expecting '&b" +
                                "ALLOW&7' or '&bDENY&7'. CommandDefender will assume this set is in &bDENY&7 mode. " +
                                "Fix this ASAP.");
                break;
        }

        conditions = parseCommandSetConditions(identifier);
        actions = parseCommandSetActions(identifier);
        options = parseCommandSetOptions(identifier);

        commandSets.add(new CommandSet(
                identifier,
                type,
                conditions,
                actions,
                options,
                conditionsPercentageRequired
        ));
    }

    private HashSet<Condition> parseCommandSetConditions(final String identifier) {
        final Yaml settings = BukkitCore.getInstance().getFileHandler().getSettings().getData();
        final String path = "command-sets." + identifier + ".conditions";
        final HashSet<Condition> conditions = new HashSet<>();

        for(String conditionId : core.getRegisteredConditions()) {
        }

        return conditions;
    }

    private HashSet<Action> parseCommandSetActions(final String identifier) {
        final Yaml settings = BukkitCore.getInstance().getFileHandler().getSettings().getData();
        final String path = "command-sets." + identifier + ".actions";
        final HashSet<Action> actions = new HashSet<>();

        for(String actionId : core.getRegisteredActions()) {
        }

        return actions;
    }

    private HashSet<Option> parseCommandSetOptions(final String identifier) {
        final Yaml settings = BukkitCore.getInstance().getFileHandler().getSettings().getData();
        final String path = "command-sets." + identifier + ".options";
        final HashSet<Option> options = new HashSet<>();

        for(String optionId : core.getRegisteredOptions()) {
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
        }.runTaskTimer(BukkitCore.getInstance(), 1L, 5L);
        // every quarter of a second, CD will update each player's tab completion commands list.
    }
}
