package me.lokka30.commanddefender.utils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import me.lokka30.microlib.messaging.MicroLogger;

public class Utils {

    public static final MicroLogger logger = new MicroLogger("&b&lCommandDefender:&7 ");

    public static boolean classExists(final String path) {
        try {
            Class.forName(path);
            return true;
        } catch(ClassNotFoundException ex) {
            return false;
        }
    }

    public static List<String> getSupportedServerVersions() {
        return Arrays.asList("1.18", "1.17", "1.16", "1.15", "1.14", "1.13", "1.12", "1.11", "1.10", "1.9", "1.8", "1.7");
    }

    public static List<String> getContributors() {
        return Collections.singletonList("none");
    }
}
