package me.lokka30.commanddefender.corebukkit.listener;

import org.bukkit.event.Listener;

public interface CDListener extends Listener {

    boolean compatibleWithServer();

}
