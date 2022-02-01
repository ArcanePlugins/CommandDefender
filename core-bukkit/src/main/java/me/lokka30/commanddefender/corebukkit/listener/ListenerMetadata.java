package me.lokka30.commanddefender.corebukkit.listener;

import org.bukkit.event.Listener;

public interface ListenerMetadata extends Listener {

    // If this method is intended to be ran more than once for a specific class
    // during runtime then it is recommended to cache this value. Currently CommandDefender
    // only uses it once per listener per startup, so there's no point in caching it
    // to avoid pointless over-engineering.
    boolean compatibleWithServer();

}
