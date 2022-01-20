package me.lokka30.commanddefender.corebukkit.util;

import org.jetbrains.annotations.NotNull;

public class BukkitColorizer {

    @NotNull
    public String colorize(final @NotNull String msg) {
        if(msg.isEmpty()) return msg;

        if(BukkitUtils.serverHasBungeeChatColorAPI()) {
            return colorizeSpigot(msg);
        } else {
            return colorizeBukkit(msg);
        }
    }

    @NotNull
    private String colorizeBukkit(final @NotNull String msg) {
        return org.bukkit.ChatColor.translateAlternateColorCodes('&', msg);
    }

    @NotNull
    private String colorizeSpigot(final @NotNull String msg) {
        return net.md_5.bungee.api.ChatColor.translateAlternateColorCodes('&', msg);
    }
}
