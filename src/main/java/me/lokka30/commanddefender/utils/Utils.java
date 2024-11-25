package me.lokka30.commanddefender.utils;

import java.util.Arrays;
import java.util.List;

public class Utils {

    public static boolean classExists(final String path) {
        try {
            Class.forName(path);
            return true;
        } catch (ClassNotFoundException ex) {
            return false;
        }
    }

    public static List<String> getContributors() {
        return Arrays.asList("TheJoshua", "Artel");
    }
}