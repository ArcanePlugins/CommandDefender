package me.lokka30.commanddefender.core.util;

import org.bukkit.ChatColor;
import org.jetbrains.annotations.NotNull;

public class MessageUtil {

    @NotNull
    public static String colorize(@NotNull final String msg) {
        switch (Commons.IMPLEMENTATION) {
            case BUKKIT:
                if(Implementation.isSpigot()) {
                    return net.md_5.bungee.api.ChatColor.translateAlternateColorCodes('&', msg);
                } else {
                    return ChatColor.translateAlternateColorCodes('&', msg);
                }
            case BUNGEE:
                return net.md_5.bungee.api.ChatColor.translateAlternateColorCodes('&', msg);
            case VELOCITY:
                //TODO
                return msg;
            case SPONGE:
                //TODO
                return msg;
            default:
                throw new IllegalStateException("Unexpected value: " + Commons.IMPLEMENTATION);
        }
    }
}
