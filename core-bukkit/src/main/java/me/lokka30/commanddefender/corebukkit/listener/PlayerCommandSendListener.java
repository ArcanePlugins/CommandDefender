package me.lokka30.commanddefender.corebukkit.listener;

import me.lokka30.commanddefender.core.util.CoreUtils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandSendEvent;

public class PlayerCommandSendListener implements Listener, ListenerMetadata {

    @Override
    public boolean compatibleWithServer() {
        return CoreUtils.classExists("org.bukkit.event.player.PlayerCommandSendEvent");
    }

    @EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
    public void onPlayerCommandSend(final PlayerCommandSendEvent event) {
        //TODO
    }
}
