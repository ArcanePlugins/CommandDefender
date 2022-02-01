package me.lokka30.commanddefender.corebukkit.util;

import me.lokka30.commanddefender.core.util.CoreUtils;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class BukkitUtils {

    // This class's methods are static, use them as such.
    private BukkitUtils() { throw new UnsupportedOperationException("Attempted instantiation of utility-type class"); }

    // Translate the color codes in a given msg (e.g. `&a` = lime/green).
    @NotNull
    public static String colorize(final @NotNull String msg) {
        Objects.requireNonNull(msg, "msg");
        if(msg.isEmpty()) return msg;

        if(CoreUtils.classExists("net.md_5.bungee.api.ChatColor")) {
            return net.md_5.bungee.api.ChatColor.translateAlternateColorCodes('&', msg);
        } else {
            return org.bukkit.ChatColor.translateAlternateColorCodes('&', msg);
        }
    }

}
