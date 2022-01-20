package me.lokka30.commanddefender.corebukkit.listener;

import me.lokka30.commanddefender.corebukkit.util.BukkitUtils;
import org.bukkit.event.Listener;

public class PlayerCommandSendListener implements Listener, ListenerInfo {

    @Override
    public boolean compatibleWithServer() {
        return BukkitUtils.serverHasPlayerCommandSendEvent();
    }
}
