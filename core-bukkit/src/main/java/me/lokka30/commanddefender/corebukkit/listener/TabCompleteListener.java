package me.lokka30.commanddefender.corebukkit.listener;

import me.lokka30.commanddefender.core.Commons;
import me.lokka30.commanddefender.core.util.CoreUtils;
import me.lokka30.commanddefender.corebukkit.listener.misc.ListenerExt;
import org.bukkit.event.EventHandler;
import org.bukkit.event.server.TabCompleteEvent;

public class TabCompleteListener implements ListenerExt {

    @Override
    public boolean compatibleWithServer() {
        return CoreUtils.classExists("org.bukkit.event.server.TabCompleteEvent");
    }

    @EventHandler
    public void onTabComplete(final TabCompleteEvent event) {
        Commons.core().logger().warning(String.format(
            "TabCompleteListener Debug - sender: &f%s&7, buffer: &b%s&7, completions: &8[&b%s&8]&7.",
            event.getSender().getName(),
            event.getBuffer(),
            String.join("&7, &b", event.getCompletions())
        ));
    }
}
