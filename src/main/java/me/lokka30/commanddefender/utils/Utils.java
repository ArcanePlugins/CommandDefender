package me.lokka30.commanddefender.utils;

import java.util.Arrays;
import java.util.List;
import me.lokka30.microlib.messaging.MicroLogger;

public class Utils {
    public static final MicroLogger logger = new MicroLogger("&b&lCommandDefender:&7 ");

    public static boolean classExists(final String path) {
        try {
            Class.forName(path);
            return true;
        } catch (ClassNotFoundException ex) {
            return false;
        }
    }

    public static List<String> getContributors() {
        return Arrays.asList("TheJoshua", "JLM / ArtelGG");
    }
}