package me.lokka30.commanddefender.corebukkit.listener;

import me.lokka30.commanddefender.core.Commons;
import me.lokka30.commanddefender.core.debug.DebugCategory;
import me.lokka30.commanddefender.core.debug.DebugHandler;
import me.lokka30.commanddefender.core.util.CoreUtils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerCommandSendEvent;

public class PlayerCommandSendListener implements ListenerExt {

    @Override
    public boolean compatibleWithServer() {
        return CoreUtils.classExists("org.bukkit.event.player.PlayerCommandSendEvent");
    }

    @EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
    public void onPlayerCommandSend(final PlayerCommandSendEvent event) {
        if (DebugHandler.isDebugCategoryEnabled(DebugCategory.COMMAND_SUGGESTION_LISTENER)) {
            Commons.core().logger().debug(DebugCategory.COMMAND_SUGGESTION_LISTENER, String.format(
                "%s is being sent the commands %s",
                event.getPlayer().getName(),
                event.getCommands()
            ));
        }
        //TODO
    }

}
