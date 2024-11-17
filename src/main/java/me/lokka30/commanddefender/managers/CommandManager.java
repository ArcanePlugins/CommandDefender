package me.lokka30.commanddefender.managers;

import me.lokka30.commanddefender.CommandDefender;
import me.lokka30.commanddefender.utils.Utils;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class CommandManager {

    private final CommandDefender plugin;

    private final HashMap<Integer, PrioritisedList> prioritisedListMap = new HashMap<>();

    public CommandManager(final CommandDefender plugin) {
        this.plugin = plugin;
    }

    public void load() {
        prioritisedListMap.clear();

        if (!plugin.settingsFile.getConfig().getBoolean("priorities.enable-command-blocking", true)) {
            return;
        }

        int priority = 1;
        while (plugin.settingsFile.getConfig().contains("priorities." + priority)) {

            final String listModeStr = plugin.settingsFile.getConfig().getString("priorities." + priority + ".mode");

            PrioritisedList prioritisedList = new PrioritisedList(
                    listModeStr == null || listModeStr.isEmpty() ? null : ListMode.fromString(listModeStr),
                    getSplitCommandSetFromList(plugin.settingsFile.getConfig().getStringList("priorities." + priority + ".list")),
                    plugin.settingsFile.getConfig().getStringList("priorities." + priority + ".deny-message")
            );

            prioritisedListMap.put(priority, prioritisedList);

            priority++;
        }
    }

    private HashSet<String[]> getSplitCommandSetFromList(List<String> list) {
        HashSet<String[]> set = new HashSet<>(list.size());
        for (String listed : list) {
            set.add(listed.toLowerCase().split(" "));
        }
        return set;
    }

    public BlockedStatus getBlockedStatus(Player player, String[] ranCommand) {

        if (!plugin.settingsFile.getConfig().getBoolean("priorities.enable-command-blocking", true)) {
            return new BlockedStatus(false, null);
        }

        final ListMode defaultListMode = ListMode.fromString(plugin.settingsFile.getConfig().getString("priorities.unlisted"));

        if (plugin.settingsFile.getConfig().getBoolean("enable-allow-deny-permissions")) {
            if (player.hasPermission("commanddefender.allow." + ranCommand[0].toLowerCase()))
                return new BlockedStatus(false, null);
            if (player.hasPermission("commanddefender.allow.*"))
                return new BlockedStatus(false, null);
            if (player.hasPermission("commanddefender.deny." + ranCommand[0].toLowerCase()))
                return new BlockedStatus(true, null);
            if (player.hasPermission("commanddefender.deny.*"))
                return new BlockedStatus(true, null);
        }

        // Go in reverse so that a higher 'priority' actually has a higher priority.
        for (int priority = prioritisedListMap.size(); priority > 0; priority--) {

            PrioritisedList prioritisedList = prioritisedListMap.get(priority);

            ListMode listMode = prioritisedList.listMode == null ? defaultListMode : prioritisedList.listMode;

            // Check for permissions that override the list mode in the setting.
            if (player.hasPermission("commanddefender.allow." + priority)) listMode = ListMode.ALLOW;
            if (player.hasPermission("commanddefender.deny." + priority)) listMode = ListMode.DENY;

            // For each listed command in the prioritised list,
            for (String[] listedCommand : prioritisedList.listedCommands) {

                // Go through each arg in the listed command, make sure it does not exceed either listed or ran command length, otherwise NPE :)
                for (int arg = 0; arg < Math.min(listedCommand.length, ranCommand.length); arg++) {

                    // If listedCommand is '/*' then determine
                    if (arg == 0 && listedCommand[arg].equals("/*")) {
                        return new BlockedStatus(listMode == ListMode.DENY, prioritisedList.denyMessage);
                    }

                    // Block the last arg of the listed command or if it is the * character. go to next listedCommand if args does not match
                    if (listedCommand[arg].equals("*") || ranCommand[arg].equalsIgnoreCase(listedCommand[arg].replace("\\*", "*"))) {
                        if (arg == listedCommand.length - 1) {
                            return new BlockedStatus(listMode == ListMode.DENY, prioritisedList.denyMessage);
                        }
                    } else {
                        break;
                    }
                }
            }
        }

        // Command is not allowed/blocked in any prioritised list so return default.
        return new BlockedStatus(defaultListMode == ListMode.DENY, null);
    }

    private enum ListMode {
        ALLOW, DENY;

        public static ListMode fromString(String str) {
            if (str == null || str.isEmpty()) {
                warnInvalid(str);
                return DENY; // Safest bet to use 'DENY' as a default.
            }

            switch (str.toUpperCase()) {
                case "ALLOW":
                case "A":
                case "ALLOWED":
                case "ALLOWING":
                    return ALLOW;

                case "DENY":
                case "D":
                case "DENIED":
                case "DENYING":
                    return DENY;

                default:
                    warnInvalid(str);
                    return DENY; // Safest bet to use 'DENY' as a default.
            }
        }

        private static void warnInvalid(String str) {
            Utils.logger.error("CommandManager encountered an invalid ListMode in your configuration:");
            Utils.logger.error("Invalid ListMode specified somewhere in your settings file. You set '&r" + str + "&7', but was expecting &bALLOW&7 or &bDENY&7. Try CTRL+F the file for it.");
            Utils.logger.error("The plugin will force-deny commands in this list as a safety measure until you fix it. (Please do so as soon as possible!)");
        }
    }

    private static class PrioritisedList {
        final ListMode listMode;
        final HashSet<String[]> listedCommands;
        final List<String> denyMessage;

        public PrioritisedList(ListMode listMode, HashSet<String[]> listedCommands, List<String> denyMessage) {
            this.listMode = listMode;
            this.listedCommands = listedCommands;
            this.denyMessage = denyMessage;
        }
    }

    public static class BlockedStatus {
        public final boolean isBlocked;
        public final List<String> denyMessage;

        public BlockedStatus(boolean isBlocked, List<String> denyMessage) {
            this.isBlocked = isBlocked;
            this.denyMessage = denyMessage;
        }
    }
}