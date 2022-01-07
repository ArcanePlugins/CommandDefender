package me.lokka30.commanddefender.corebukkit.util;

import org.apache.commons.lang.WordUtils;
import org.jetbrains.annotations.NotNull;

public class BukkitUtils {

    private BukkitUtils() { throw new UnsupportedOperationException(); }

    private static final BukkitColorizer colorizer = new BukkitColorizer();
    public static @NotNull BukkitColorizer getColorizer() { return colorizer; }

    @NotNull
    public static String formatEnumConstant(final @NotNull String str) {
        return WordUtils.capitalizeFully(str.replace("_", " "));
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
