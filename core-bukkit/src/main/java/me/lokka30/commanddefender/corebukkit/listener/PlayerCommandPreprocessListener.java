package me.lokka30.commanddefender.corebukkit.listener;

import org.bukkit.event.Listener;

public class PlayerCommandPreprocessListener implements Listener, ListenerInfo {

    @Override
    public boolean compatibleWithServer() {
        return true;
    }
}
