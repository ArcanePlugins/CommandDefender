package me.lokka30.commanddefender.utils;

import me.lokka30.microlib.MicroLogger;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Utils {

    public static final MicroLogger logger = new MicroLogger("&b&lCommandDefender: &7");

    public static boolean isOneThirteen() {
        try {
            Class.forName("org.bukkit.event.player.PlayerCommandSendEvent");
        } catch (ClassNotFoundException e) {
            return false;
        }
        return true;
    }

    public static List<String> getSupportedServerVersions() {
        return Arrays.asList("1.16", "1.15", "1.14", "1.13", "1.12", "1.11", "1.10", "1.9", "1.8", "1.7");
    }

    public static List<String> getContributors() {
        return Collections.singletonList("none");
    }
}
