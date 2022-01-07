package me.lokka30.commanddefender.corebukkit.util;

import me.lokka30.commanddefender.core.util.Colorizer;
import org.jetbrains.annotations.NotNull;

public class BukkitColorizer implements Colorizer {

    @Override @NotNull
    public String colorize(final @NotNull String msg) {
        if(msg.isEmpty()) return msg;

        if(useSpigotMethod()) {
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

    private boolean knowsWhetherToUseSpigotMethodOrNot = false;
    private boolean useSpigotMethod;

    private boolean useSpigotMethod() {
        if(knowsWhetherToUseSpigotMethodOrNot) return useSpigotMethod;
        knowsWhetherToUseSpigotMethodOrNot = true;

        try {
            Class.forName("net.md_5.bungee.api.ChatColor");
        } catch(ClassNotFoundException ex) {
            useSpigotMethod = false;
            return false;
        }
        useSpigotMethod = true;
        return true;
    }
}
