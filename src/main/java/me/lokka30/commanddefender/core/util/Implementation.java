package me.lokka30.commanddefender.core.util;

import org.jetbrains.annotations.Nullable;

public enum Implementation {

    BUKKIT,

    BUNGEE,

    VELOCITY,

    SPONGE;

    @Nullable
    private static Boolean isSpigot = null;

    public static boolean isSpigot() {
        if(isSpigot != null) {
            return isSpigot;
        }

        try {
            Class.forName("net.md_5.bungee.api.ChatColor");
        } catch(ClassNotFoundException ex) {
            isSpigot = false;
            return false;
        }

        isSpigot = true;
        return true;
    }

}
