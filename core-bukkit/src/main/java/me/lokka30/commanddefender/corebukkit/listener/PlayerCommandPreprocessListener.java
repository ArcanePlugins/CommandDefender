package me.lokka30.commanddefender.corebukkit.listener;

import me.lokka30.commanddefender.core.Commons;
import me.lokka30.commanddefender.core.debug.DebugCategory;
import me.lokka30.commanddefender.core.debug.DebugHandler;
import me.lokka30.commanddefender.core.filter.FilterContextType;
import me.lokka30.commanddefender.core.util.CoreUtils;
import me.lokka30.commanddefender.corebukkit.listener.misc.ListenerExt;
import me.lokka30.commanddefender.corebukkit.util.universal.BukkitPlatformHandler;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class PlayerCommandPreprocessListener implements ListenerExt {

    @Override
    public boolean compatibleWithServer() {
        return CoreUtils.classExists("org.bukkit.event.player.PlayerCommandPreprocessEvent");
    }

    @EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
    public void onPlayerCommandPreprocess(final PlayerCommandPreprocessEvent event) {
        if (!Commons.core().commandFilter().canAccess(
            FilterContextType.COMMAND_EXECUTION,
            BukkitPlatformHandler.bukkitPlayerToUniversal(event.getPlayer()),
            BukkitPlatformHandler.bukkitCommandMessageToUniversal(event.getMessage())
        )) {
            event.setCancelled(true);
        }

        if (DebugHandler.isDebugCategoryEnabled(DebugCategory.COMMAND_EXECUTION_LISTENER)) {
            Commons.core().logger().debug(DebugCategory.COMMAND_EXECUTION_LISTENER, String.format(
                "%s tried to run %s, cancellation status: %s",
                event.getPlayer().getName(),
                event.getMessage(),
                event.isCancelled()
            ));
        }
    }

}
