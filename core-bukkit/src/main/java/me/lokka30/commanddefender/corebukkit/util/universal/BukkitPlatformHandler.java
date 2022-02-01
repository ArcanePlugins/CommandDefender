package me.lokka30.commanddefender.corebukkit.util.universal;

import me.lokka30.commanddefender.core.util.universal.*;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class BukkitPlatformHandler implements PlatformHandler {

    @NotNull
    public static TabExecutor universalCommandToBukkit(final @NotNull UniversalCommand universalCommand) {
        return new TabExecutor() {
            @Override
            public boolean onCommand(final @NotNull CommandSender sender, final @NotNull Command cmd, final @NotNull String label, final @NotNull String[] args) {
                universalCommand.run(
                        bukkitCommandSenderToUniversal(sender),
                        bukkitCommandArgsToUniversal(label, args)
                );
                return true;
            }

            @Override @NotNull
            public List<String> onTabComplete(final @NotNull CommandSender sender, final @NotNull Command cmd, final @NotNull String label, final @NotNull String[] args) {
                return universalCommand.generateTabSuggestions(
                        bukkitCommandSenderToUniversal(sender),
                        bukkitCommandArgsToUniversal(label, args)
                );
            }
        };
    }

    @NotNull
    public static UniversalCommandSender bukkitCommandSenderToUniversal(final @NotNull CommandSender bukkitSender) {
        return new UniversalCommandSender() {
            @Override
            public @NotNull String name() {
                return bukkitSender.getName();
            }

            @Override
            public void sendChatMessage(@NotNull String msg) {
                bukkitSender.sendMessage(msg);
            }

            @Override
            public boolean hasPermission(@NotNull String permission) {
                return bukkitSender.hasPermission(permission);
            }
        };
    }

    @NotNull
    public static String[] bukkitCommandArgsToUniversal(final @NotNull String label, final @NotNull String[] bukkitArgs) {
        final LinkedList<String> args = new LinkedList<>();
        args.add(label);
        Collections.addAll(args, bukkitArgs);
        return args.toArray(new String[0]);
    }

    @NotNull
    public static String[] bukkitCommandMessageToUniversal(final @NotNull String commandMsg) {
        return commandMsg.split(" ");
    }

    @NotNull
    public static BukkitPlayer universalPlayerToBukkit(final @NotNull UniversalPlayer player) {
        return new BukkitPlayer(player.uuid(), Objects.requireNonNull(Bukkit.getPlayer(player.uuid())));
    }

    @NotNull
    public static UniversalPlayer bukkitPlayerToUniversal(final @NotNull Player player) {
        return new UniversalPlayer() {
            @Override
            public UUID uuid() {
                return player.getUniqueId();
            }

            @Override
            public void sendChatMessage(@NotNull String msg) {
                player.sendMessage(msg);
            }

            @Override
            public boolean hasPermission(@NotNull String permission) {
                return player.hasPermission(permission);
            }
        };
    }

    @Override
    public UniversalSound buildPlatformSpecificSound(@NotNull String identifier, double volume, double pitch) {
        return new BukkitSound(identifier, volume, pitch);
    }
}