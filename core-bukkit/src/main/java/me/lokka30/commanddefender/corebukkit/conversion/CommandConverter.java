package me.lokka30.commanddefender.corebukkit.conversion;

import me.lokka30.commanddefender.core.command.UniversalCommand;
import me.lokka30.commanddefender.core.command.UniversalCommandSender;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class CommandConverter {

    public static TabExecutor toBukkitCommand(UniversalCommand universalCommand) {
        return new TabExecutor() {
            @Override
            public boolean onCommand(final @NotNull CommandSender sender, final @NotNull Command cmd, final @NotNull String label, final @NotNull String[] args) {
                universalCommand.run(toUniversalSender(sender), toUniversalArgs(label, args));
                return true;
            }

            @Override @NotNull
            public List<String> onTabComplete(final @NotNull CommandSender sender, final @NotNull Command cmd, final @NotNull String label, final @NotNull String[] args) {
                return universalCommand.generateTabSuggestions(toUniversalSender(sender), toUniversalArgs(label, args));
            }
        };
    }

    public static UniversalCommandSender toUniversalSender(final CommandSender bukkitSender) {
        return new UniversalCommandSender() {
            @Override
            public @NotNull String name() {
                return bukkitSender.getName();
            }

            @Override
            public void sendMessage(@NotNull String msg) {
                bukkitSender.sendMessage(msg);
            }

            @Override
            public boolean hasPermission(@NotNull String permission) {
                return bukkitSender.hasPermission(permission);
            }
        };
    }

    public static String[] toUniversalArgs(final String label, final String[] bukkitArgs) {
        final LinkedList<String> args = new LinkedList<>();
        args.add(label);
        Collections.addAll(args, bukkitArgs);
        return args.toArray(new String[0]);
    }
}
