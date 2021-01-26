package me.lokka30.commanddefender;

import me.lokka30.microlib.MicroUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerCommandSendEvent;

import java.util.Objects;

public class CommandListeners implements Listener {

    private final CommandDefender instance;

    public CommandListeners(CommandDefender instance) {
        this.instance = instance;
    }

    public void registerListeners() {
        Bukkit.getPluginManager().registerEvents(this, instance);

        //Check if Minecraft 1.13+ is installed.
        if (instance.isOneThirteen()) {
            Bukkit.getPluginManager().registerEvents(new NewCommandListeners(), instance);
        }
    }

    public boolean isBlocked(final Player player, String command) {
        command = command.toLowerCase();

        if (player.hasPermission("commanddefender.bypass.*") || player.hasPermission("commanddefender.bypass." + command.replaceFirst("/", ""))) {
            return false;
        } else {
            return instance.commandManager.isBlocked(command);
        }
    }

    @EventHandler
    public void onCommand(final PlayerCommandPreprocessEvent event) {
        final Player player = event.getPlayer();
        final String command = event.getMessage();

        if (isBlocked(player, command)) {
            event.setCancelled(true);
            player.sendMessage(MicroUtils.colorize(Objects.requireNonNull(instance.messagesFile.getConfig().getString("cancelled-blocked"))
                    .replace("%prefix%", Objects.requireNonNull(instance.messagesFile.getConfig().getString("prefix"))))
                    .replace("%command%", command));
        } else if (command.contains(":") && instance.settingsFile.getConfig().getBoolean("block-colons")) {
            event.setCancelled(true);
            player.sendMessage(MicroUtils.colorize(Objects.requireNonNull(instance.messagesFile.getConfig().getString("cancelled-colon"))
                    .replace("%prefix%", Objects.requireNonNull(instance.messagesFile.getConfig().getString("prefix")))));
        }
    }

    private class NewCommandListeners implements Listener {
        @EventHandler
        public void onCommandSend(final PlayerCommandSendEvent event) {
            // Remove blocked commands from the suggestions list.
            event.getCommands().removeIf(command -> isBlocked(event.getPlayer(), "/" + command));

            // Remove commands with colons, if enabled, such as /bukkit:help.
            if (instance.settingsFile.getConfig().getBoolean("block-colons")) {
                event.getCommands().removeIf(command -> command.contains(":"));
            }
        }
    }
}
