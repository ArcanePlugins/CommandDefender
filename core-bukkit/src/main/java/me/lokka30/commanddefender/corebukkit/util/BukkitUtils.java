package me.lokka30.commanddefender.corebukkit.util;

import org.jetbrains.annotations.NotNull;

public class BukkitUtils {

    private BukkitUtils() { throw new UnsupportedOperationException(); }

    // would use Optional, but we're targeting Java 8 :sob:. This will suffice.
    //TODO nope we're using Java 17 now! time to upgrade this!
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
            return net.md_5.bungee.api.ChatColor.translateAlternateColorCodes('&', msg);
        } else {
            return org.bukkit.ChatColor.translateAlternateColorCodes('&', msg);
        }
    }

}
