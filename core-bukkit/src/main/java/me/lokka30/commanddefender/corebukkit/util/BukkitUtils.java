package me.lokka30.commanddefender.corebukkit.util;

import org.apache.commons.lang.WordUtils;
import org.jetbrains.annotations.NotNull;

public class BukkitUtils {

    private BukkitUtils() { throw new UnsupportedOperationException(); }

    @NotNull
    public static String formatEnumConstant(final @NotNull String str) {
        return WordUtils.capitalizeFully(str.replace("_", " "));
    }

    // can be replaced with an optional<boolean> later on.
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

}
