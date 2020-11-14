package me.lokka30.commanddefender;

import me.lokka30.microlib.MicroUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CDCommand implements TabExecutor {

    private final CommandDefender instance;

    public CDCommand(CommandDefender instance) {
        this.instance = instance;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length == 0) {
            for (String message : instance.messagesFile.getConfig().getStringList("command.main")) {
                sender.sendMessage(MicroUtils.colorize(message)
                        .replace("%prefix%", Objects.requireNonNull(instance.messagesFile.getConfig().getString("prefix")))
                        .replace("%version%", instance.getDescription().getVersion())
                        .replace("%label%", label));
            }
        } else if (args.length == 1) {
            if (args[0].equalsIgnoreCase("reload")) {
                if (sender.hasPermission("commanddefender.reload")) {
                    sender.sendMessage(MicroUtils.colorize(Objects.requireNonNull(instance.messagesFile.getConfig().getString("command.reload.start"))
                            .replace("%prefix%", Objects.requireNonNull(instance.messagesFile.getConfig().getString("prefix")))));
                    try {
                        instance.loadFiles();
                    } catch (IOException exception) {
                        exception.printStackTrace();
                    }
                    Bukkit.getOnlinePlayers().forEach(Player::updateCommands);
                    sender.sendMessage(MicroUtils.colorize(Objects.requireNonNull(instance.messagesFile.getConfig().getString("command.reload.complete"))
                            .replace("%prefix%", Objects.requireNonNull(instance.messagesFile.getConfig().getString("prefix")))));
                } else {
                    sender.sendMessage(MicroUtils.colorize(Objects.requireNonNull(instance.messagesFile.getConfig().getString("command.no-permission"))
                            .replace("%prefix%", Objects.requireNonNull(instance.messagesFile.getConfig().getString("prefix")))));
                }
            } else if (args[0].equalsIgnoreCase("backup")) {
                if (sender.hasPermission("commanddefender.backup")) {
                    sender.sendMessage(MicroUtils.colorize(Objects.requireNonNull(instance.messagesFile.getConfig().getString("command.backup.start"))
                            .replace("%prefix%", Objects.requireNonNull(instance.messagesFile.getConfig().getString("prefix")))));
                    sender.sendMessage("&c&oThis feature is not yet implemented.");
                    sender.sendMessage(MicroUtils.colorize(Objects.requireNonNull(instance.messagesFile.getConfig().getString("command.backup.complete"))
                            .replace("%prefix%", Objects.requireNonNull(instance.messagesFile.getConfig().getString("prefix")))));
                } else {
                    sender.sendMessage(MicroUtils.colorize(Objects.requireNonNull(instance.messagesFile.getConfig().getString("command.no-permission"))
                            .replace("%prefix%", Objects.requireNonNull(instance.messagesFile.getConfig().getString("prefix")))));
                }
            } else {
                sender.sendMessage(MicroUtils.colorize(Objects.requireNonNull(instance.messagesFile.getConfig().getString("command.usage"))
                        .replace("%prefix%", Objects.requireNonNull(instance.messagesFile.getConfig().getString("prefix"))
                                .replace("%label%", label))));
            }
        } else {
            sender.sendMessage(MicroUtils.colorize(Objects.requireNonNull(instance.messagesFile.getConfig().getString("command.usage"))
                    .replace("%prefix%", Objects.requireNonNull(instance.messagesFile.getConfig().getString("prefix"))
                            .replace("%label%", label))));
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        List<String> suggestions = new ArrayList<>();

        if (args.length == 0) {
            suggestions.add("reload");
            suggestions.add("backup");
        }

        return suggestions;
    }
}
