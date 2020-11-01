package me.lokka30.commanddefender;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerCommandSendEvent;

import java.util.List;
import java.util.Objects;

public class CommandListener implements Listener {

    private final CommandDefender instance;

    public CommandListener(CommandDefender instance) {
        this.instance = instance;
    }

    private String colorize(String msg) {
        return ChatColor.translateAlternateColorCodes('&', msg);
    }

    public boolean isBlocked(Player player, String command) {
        if (player.hasPermission("commanddefender.bypass.*") || player.hasPermission("commanddefender.bypass." + command)) {
            return false;
        } else {
            final List<String> commandsList = instance.settingsCfg.getStringList("commands.list");
            switch (Objects.requireNonNull(instance.settingsCfg.getString("commands.mode")).toUpperCase()) {
                case "WHITELIST":
                    return !commandsList.contains(command);
                case "BLACKLIST":
                    return commandsList.contains(command);
                default:
                    instance.logger.error("&cERROR: &7You have not specified a valid mode in '&bsettings.yml&7' under '&bcommands.mode&7'! Must be either '&bWHITELIST&7' or '&bBLACKLIST&7'. &fCommandDefender will not block any commands until this is fixed!");
                    return false;
            }
        }
    }

    @EventHandler
    public void onCommand(final PlayerCommandPreprocessEvent event) {
        final Player player = event.getPlayer();
        final String command = event.getMessage().split(" ")[0];

        if (isBlocked(player, command)) {
            event.setCancelled(true);
            player.sendMessage(colorize(Objects.requireNonNull(instance.messagesCfg.getString("cancelled-blocked"))
                    .replace("%prefix%", Objects.requireNonNull(instance.messagesCfg.getString("prefix")))
                    .replace("%command%", command)));
        } else if (command.contains(":") && instance.settingsCfg.getBoolean("block-colons")) {
            event.setCancelled(true);
            player.sendMessage(colorize(Objects.requireNonNull(instance.messagesCfg.getString("cancelled-colon"))
                    .replace("%prefix%", Objects.requireNonNull(instance.messagesCfg.getString("prefix")))));
        }
    }

    @EventHandler
    public void onCommandSend(final PlayerCommandSendEvent event) {
        // Remove blocked commands from the suggestions list.
        event.getCommands().removeIf(command -> isBlocked(event.getPlayer(), "/" + command));

        // Remove commands with colons, if enabled, such as /bukkit:help.
        if (instance.settingsCfg.getBoolean("block-colons")) {
            event.getCommands().removeIf(command -> command.contains(":"));
        }
    }
}
