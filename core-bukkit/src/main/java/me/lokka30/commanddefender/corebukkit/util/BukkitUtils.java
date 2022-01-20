package me.lokka30.commanddefender.corebukkit.util;

import org.apache.commons.lang.WordUtils;
import org.jetbrains.annotations.NotNull;

public class BukkitUtils {

    private BukkitUtils() { throw new UnsupportedOperationException(); }

    @NotNull
    public static String formatEnumConstant(final @NotNull String str) {
        return WordUtils.capitalizeFully(str.replace("_", " "));
    }

    // would use Optional, but we're targeting Java 8 :sob:. This will suffice.
    private static byte serverHasPlayerCommandSendEvent = 0;
    public static boolean serverHasPlayerCommandSendEvent() {
        switch(serverHasPlayerCommandSendEvent) {
            case 0:
                try {
                    Class.forName("org.bukkit.event.player.PlayerCommandSendEvent");
                } catch (ClassNotFoundException e) {
                    serverHasPlayerCommandSendEvent = 2;
                    return false;
                }
                serverHasPlayerCommandSendEvent = 1;
                return true;
            case 1:
                return true;
            case 2:
                return false;
            default:
                throw new IllegalStateException("Unexpected value: " + serverHasPlayerCommandSendEvent);
        }
    }

    // would use Optional, but we're targeting Java 8 :sob:. This will suffice.
    private static byte serverHasBungeeChatColorAPI = 0;
    public static boolean serverHasBungeeChatColorAPI() {
        switch(serverHasBungeeChatColorAPI) {
            case 0:
                try {
                    Class.forName("net.md_5.bungee.api.ChatColor");
                } catch (ClassNotFoundException e) {
                    serverHasBungeeChatColorAPI = 2;
                    return false;
                }
                serverHasBungeeChatColorAPI = 1;
                return true;
            case 1:
                return true;
            case 2:
                return false;
            default:
                throw new IllegalStateException("Unexpected value: " + serverHasBungeeChatColorAPI);
        }
    }

    @NotNull
    public static String colorize(final @NotNull String msg) {
        if(msg.isEmpty()) return msg;

        if(BukkitUtils.serverHasBungeeChatColorAPI()) {
            return colorizeSpigot(msg);
        } else {
            return colorizeBukkit(msg);
        }
    }

    @NotNull
    private static String colorizeBukkit(final @NotNull String msg) {
        return org.bukkit.ChatColor.translateAlternateColorCodes('&', msg);
    }

    @NotNull
    private static String colorizeSpigot(final @NotNull String msg) {
        return net.md_5.bungee.api.ChatColor.translateAlternateColorCodes('&', msg);
    }

}
