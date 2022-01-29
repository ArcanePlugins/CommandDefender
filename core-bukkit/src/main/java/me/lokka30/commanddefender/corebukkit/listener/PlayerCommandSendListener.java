package me.lokka30.commanddefender.corebukkit.listener;

import me.lokka30.commanddefender.corebukkit.util.BukkitUtils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandSendEvent;

public class PlayerCommandSendListener implements Listener, CDListener {

    @Override
    public boolean compatibleWithServer() {
        return BukkitUtils.serverHasPlayerCommandSendEvent();
    }

    @EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
    public void onPlayerCommandSend(final PlayerCommandSendEvent event) {
        //TODO
    }
}
