package me.lokka30.commanddefender.corebukkit.listener;

import com.destroystokyo.paper.event.server.AsyncTabCompleteEvent;
import me.lokka30.commanddefender.core.Commons;
import me.lokka30.commanddefender.core.util.CoreUtils;
import me.lokka30.commanddefender.corebukkit.listener.misc.ListenerExt;
import org.bukkit.event.EventHandler;

public class AsyncTabCompleteListener implements ListenerExt {

    @Override
    public boolean compatibleWithServer() {
        return CoreUtils.classExists("com.destroystokyo.paper.event.server.AsyncTabCompleteEvent");
    }

    @EventHandler
    public void onTabComplete(final AsyncTabCompleteEvent event) {
        Commons.core().logger().warning(String.format(
            "AsyncTabCompleteListener Debug - sender: &f%s&7, buffer: &b%s&7, completions: &8[&b%s&8]&7.",
            event.getSender().getName(),
            event.getBuffer(),
            String.join("&7, &b", event.getCompletions())
        ));
    }
}
