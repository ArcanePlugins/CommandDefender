package me.lokka30.commanddefender.core.util;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Locale;

public class CoreUtils {

    @NotNull
    public static String formatEnumConstant(final @NotNull String str) {
        final String[] words = str.replace("_", " ").split(" ");
        for(int i = 0; i < words.length; i++) {
            words[i] = words[i].substring(0, 1).toUpperCase(Locale.ROOT) + words[i].substring(1).toLowerCase(Locale.ROOT);
        }
        return String.join(" ", words);
    }

    private static final HashMap<String, Boolean> classExistsCache = new HashMap<>();
    public static boolean classExists(final @NotNull String classpath) {
        if(classExistsCache.containsKey(classpath)) {
            return classExistsCache.get(classpath);
        }

        try {
            Class.forName(classpath);
            classExistsCache.put(classpath, true);
            return true;
        } catch(ClassNotFoundException ex) {
            classExistsCache.put(classpath, false);
            return false;
        }
    }

}
