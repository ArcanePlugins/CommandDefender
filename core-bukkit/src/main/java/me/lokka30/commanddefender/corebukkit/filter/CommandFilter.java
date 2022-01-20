package me.lokka30.commanddefender.corebukkit.filter;

import me.lokka30.commanddefender.core.filter.CommandAccessStatus;
import me.lokka30.commanddefender.core.filter.set.CommandSet;
import me.lokka30.commanddefender.core.player.UniversalPlayer;
import me.lokka30.commanddefender.corebukkit.BukkitCore;
import me.lokka30.commanddefender.corebukkit.util.BukkitUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.LinkedList;

public class CommandFilter {

    private final LinkedList<CommandSet> commandSets = new LinkedList<>();

    public boolean canAccess(final UniversalPlayer player, final String[] args) {
        for(final CommandSet set : commandSets) {
            final CommandAccessStatus status = set.accessStatus(player, args);
            if(status != CommandAccessStatus.UNKNOWN) {
                return (status == CommandAccessStatus.ALLOWED);
            }
        }
        // command sets don't specify the command -> return default status:
        return BukkitCore.getInstance().fileHandler().settings().data().get("default-command-status", true);
    }

    public void load() {
        parseCommandSets();
        updateTabCompletionSlowly();
    }

    private void parseCommandSets() {
        // TODO ...
    }

    // TODO Update to universal
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
