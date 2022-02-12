package me.lokka30.commanddefender.corebukkit.listener;

import java.util.LinkedList;
import me.lokka30.commanddefender.core.Commons;
import me.lokka30.commanddefender.core.debug.DebugCategory;
import me.lokka30.commanddefender.core.debug.DebugHandler;
import me.lokka30.commanddefender.core.filter.FilterContextType;
import me.lokka30.commanddefender.core.util.CoreUtils;
import me.lokka30.commanddefender.corebukkit.listener.misc.ListenerExt;
import me.lokka30.commanddefender.corebukkit.util.universal.BukkitPlatformHandler;
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
        if(!Commons.core().fileHandler().advancedSettings().data()
            .get("listeners.player-command-send.enabled", true)) {
            return;
        }

        final boolean debugLog = DebugHandler.isDebugCategoryEnabled(DebugCategory.COMMAND_SUGGESTION_LISTENER);
        LinkedList<String> originalForDebugging = null;

        if (debugLog) {
            Commons.core().logger().debug(DebugCategory.COMMAND_SUGGESTION_LISTENER, String.format(
                "&f%s&7 is being sent the commands &8(&cbefore&7 interception&8)&7: &8[&b%s&8] (&b%s &7items&8)&7.",
                event.getPlayer().getName(),
                String.join("&7, &b", event.getCommands()),
                event.getCommands().size()
            ));

            originalForDebugging = new LinkedList<>(event.getCommands());
        }

        event.getCommands().removeIf(command -> !Commons.core().commandFilter().canAccess(
            FilterContextType.COMMAND_SUGGESTION,
            BukkitPlatformHandler.bukkitPlayerToUniversal(event.getPlayer()),
            ("/" + command).split(" ")
        ));

        if (debugLog) {
            originalForDebugging.removeAll(event.getCommands());

            Commons.core().logger().debug(DebugCategory.COMMAND_SUGGESTION_LISTENER, String.format(
                "&f%s&7 is being sent the commands &8(&aafter&7 interception&8)&7: &8[&b%s&8] (&b%s &7items&8)&7. The following items were removed: &8[&b%s&8] (&b%s &7items&8)&7.",
                event.getPlayer().getName(),
                String.join("&7, &b", event.getCommands()),
                event.getCommands().size(),
                originalForDebugging,
                originalForDebugging.size()
            ));
        }
    }

}
