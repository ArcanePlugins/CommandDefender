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

    private final CommandDefender instance;

    public CommandDefenderCommand(CommandDefender instance) {
        this.instance = instance;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if (!sender.hasPermission("commanddefender.command")) {
            instance.messagesFile.getConfig().getStringList("command.no-permission").forEach(message ->
                    sender.sendMessage(MessageUtils.colorizeAll(message
                            .replace("%prefix%", instance.getPrefix())
                    )));
            return true;
        }

        if (args.length == 0) {
            instance.messagesFile.getConfig().getStringList("command.main").forEach(message ->
                    sender.sendMessage(MessageUtils.colorizeAll(message
                            .replace("%prefix%", instance.getPrefix())
                            .replace("%prefix%", Objects.requireNonNull(instance.messagesFile.getConfig().getString("prefix")))
                            .replace("%version%", instance.getDescription().getVersion())
                            .replace("%label%", label)
                    )));

        } else if (args.length == 1) {
            if (args[0].equalsIgnoreCase("reload")) {
                if (sender.hasPermission("commanddefender.command.reload")) {
                    instance.messagesFile.getConfig().getStringList("command.reload.start").forEach(message ->
                            sender.sendMessage(MessageUtils.colorizeAll(message
                                    .replace("%prefix%", instance.getPrefix())
                            )));

                    instance.loadFiles();

                    if (Utils.classExists("org.bukkit.event.player.PlayerCommandSendEvent")) {
                        Bukkit.getOnlinePlayers().forEach(Player::updateCommands);
                    }

                    instance.messagesFile.getConfig().getStringList("command.reload.complete").forEach(message ->
                            sender.sendMessage(MessageUtils.colorizeAll(message
                                    .replace("%prefix%", instance.getPrefix())
                            )));
                } else {
                    instance.messagesFile.getConfig().getStringList("command.no-permission").forEach(message ->
                            sender.sendMessage(MessageUtils.colorizeAll(message
                                    .replace("%prefix%", instance.getPrefix())
                            )));
                }
            } else if (args[0].equalsIgnoreCase("backup")) {
                if (sender.hasPermission("commanddefender.command.backup")) {
                    instance.messagesFile.getConfig().getStringList("command.backup.start").forEach(message ->
                            sender.sendMessage(MessageUtils.colorizeAll(message
                                    .replace("%prefix%", instance.getPrefix())
                            )));

                    sender.sendMessage(MessageUtils.colorizeAll("&c&oThis feature is not yet implemented."));

                    instance.messagesFile.getConfig().getStringList("command.backup.complete").forEach(message ->
                            sender.sendMessage(MessageUtils.colorizeAll(message
                                    .replace("%prefix%", instance.getPrefix())
                            )));
                } else {
                    instance.messagesFile.getConfig().getStringList("command.no-permission").forEach(message ->
                            sender.sendMessage(MessageUtils.colorizeAll(message
                                    .replace("%prefix%", instance.getPrefix())
                            )));
                }
            } else if (args[0].equalsIgnoreCase("info")) {
                if (sender.hasPermission("commanddefender.command.info")) {
                    final String joiner = instance.messagesFile.getConfig().getString("command.joiner");

                    instance.messagesFile.getConfig().getStringList("command.info").forEach(message -> {
                        assert joiner != null;
                        assert instance.getDescription().getDescription() != null;

                        sender.sendMessage(MessageUtils.colorizeAll(message
                                .replace("%prefix%", instance.getPrefix())
                                .replace("%version%", instance.getDescription().getVersion())
                                .replace("%description%", instance.getDescription().getDescription())
                                .replace("%supportedVersions%", "Most of them!")
                                .replace("%contributors%", String.join(joiner, Utils.getContributors()))
                                .replace("%authors%", String.join(joiner, instance.getDescription().getAuthors()))
                        ));
                    });

                } else {
                    instance.messagesFile.getConfig().getStringList("command.no-permission").forEach(message ->
                            sender.sendMessage(MessageUtils.colorizeAll(message
                                    .replace("%prefix%", instance.getPrefix())
                            )));
                }
            } else {
                instance.messagesFile.getConfig().getStringList("command.usage").forEach(message ->
                        sender.sendMessage(MessageUtils.colorizeAll(message
                                .replace("%prefix%", instance.getPrefix())
                                .replace("%label%", label)
                        )));
            }
        } else {
            instance.messagesFile.getConfig().getStringList("command.usage").forEach(message ->
                    sender.sendMessage(MessageUtils.colorizeAll(message
                            .replace("%prefix%", instance.getPrefix())
                            .replace("%label%", label)
                    )));
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if (args.length == 1) {
            return Arrays.asList("reload", "info", "backup");
        }
        return Collections.emptyList();
    }
}