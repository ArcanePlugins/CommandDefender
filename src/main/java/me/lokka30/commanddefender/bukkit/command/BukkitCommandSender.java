package me.lokka30.commanddefender.bukkit.command;

import me.lokka30.commanddefender.core.command.UniversalCommandSender;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class BukkitCommandSender implements UniversalCommandSender {

    @NotNull private final CommandSender sender;
    public BukkitCommandSender(@NotNull final CommandSender sender) { this.sender = sender; }

    @Override
    public void sendChatMessage(@NotNull String message) {
        sender.sendMessage(message);
    }

    @Override
    public boolean hasPermission(@NotNull String permission) {
        return sender.hasPermission(permission);
    }
}
