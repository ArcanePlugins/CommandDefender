package me.lokka30.commanddefender.commands;

import me.lokka30.commanddefender.CommandDefender;
import me.lokka30.commanddefender.utils.Utils;
import me.lokka30.microlib.messaging.MessageUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class CommandDefenderCommand implements TabExecutor {

    private final CommandDefender plugin;

    public CommandDefenderCommand(CommandDefender plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if (!sender.hasPermission("commanddefender.command")) {
            plugin.messagesFile.getConfig().getStringList("command.no-permission").forEach(message ->
                    sender.sendMessage(MessageUtils.colorizeAll(message
                            .replace("%prefix%", plugin.getPrefix())
                    )));
            return true;
        }

        if (args.length == 0) {
            plugin.messagesFile.getConfig().getStringList("command.main").forEach(message ->
                    sender.sendMessage(MessageUtils.colorizeAll(message
                            .replace("%prefix%", plugin.getPrefix())
                            .replace("%prefix%", Objects.requireNonNull(plugin.messagesFile.getConfig().getString("prefix")))
                            .replace("%version%", plugin.getDescription().getVersion())
                            .replace("%label%", label)
                    )));
            return true;
        } else if (args.length == 1) {
            if (args[0].equalsIgnoreCase("reload")) {
                if (!sender.hasPermission("commanddefender.command.reload")) {
                    plugin.messagesFile.getConfig().getStringList("command.no-permission").forEach(message ->
                            sender.sendMessage(MessageUtils.colorizeAll(message
                                    .replace("%prefix%", plugin.getPrefix())
                            )));
                    return true;
                }

                plugin.messagesFile.getConfig().getStringList("command.reload.start").forEach(message ->
                        sender.sendMessage(MessageUtils.colorizeAll(message
                                .replace("%prefix%", plugin.getPrefix())
                        )));

                plugin.loadFiles();

                if (Utils.classExists("org.bukkit.event.player.PlayerCommandSendEvent")) {
                    Bukkit.getOnlinePlayers().forEach(Player::updateCommands);
                }

                plugin.messagesFile.getConfig().getStringList("command.reload.complete").forEach(message ->
                        sender.sendMessage(MessageUtils.colorizeAll(message
                                .replace("%prefix%", plugin.getPrefix())
                        )));
                return true;
            } else if (args[0].equalsIgnoreCase("info")) {
                if (!sender.hasPermission("commanddefender.command.info")) {
                    plugin.messagesFile.getConfig().getStringList("command.no-permission").forEach(message ->
                            sender.sendMessage(MessageUtils.colorizeAll(message
                                    .replace("%prefix%", plugin.getPrefix())
                            )));
                    return true;
                }

                final String joiner = plugin.messagesFile.getConfig().getString("command.joiner");

                plugin.messagesFile.getConfig().getStringList("command.info").forEach(message -> {
                    assert joiner != null;
                    assert plugin.getDescription().getDescription() != null;

                    sender.sendMessage(MessageUtils.colorizeAll(message
                            .replace("%prefix%", plugin.getPrefix())
                            .replace("%version%", plugin.getDescription().getVersion())
                            .replace("%description%", plugin.getDescription().getDescription())
                            .replace("%supportedVersions%", "Most of them!")
                            .replace("%contributors%", String.join(joiner, Utils.getContributors()))
                            .replace("%authors%", String.join(joiner, plugin.getDescription().getAuthors()))
                    ));
                });
                return true;
            } else {
                plugin.messagesFile.getConfig().getStringList("command.usage").forEach(message ->
                        sender.sendMessage(MessageUtils.colorizeAll(message
                                .replace("%prefix%", plugin.getPrefix())
                                .replace("%label%", label)
                        )));
                return true;
            }
        } else {
            plugin.messagesFile.getConfig().getStringList("command.usage").forEach(message ->
                    sender.sendMessage(MessageUtils.colorizeAll(message
                            .replace("%prefix%", plugin.getPrefix())
                            .replace("%label%", label)
                    )));
            return true;
        }
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if (args.length == 1) {
            return Arrays.asList("reload", "info");
        }
        return Collections.emptyList();
    }
}