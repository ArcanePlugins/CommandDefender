package me.lokka30.commanddefender.bukkit.util;

public class CompatUtil {

    private CompatUtil() {
        throw new UnsupportedOperationException("Initialization of utility-type class.");
    }

    public static boolean serverHasPlayerCommandSendEvent() {
        try {
            Class.forName("org.bukkit.event.player.PlayerCommandSendEvent");
        } catch (ClassNotFoundException e) {
            return false;
        }
        return true;
    }

}
