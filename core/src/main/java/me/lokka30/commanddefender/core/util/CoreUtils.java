package me.lokka30.commanddefender.core.util;

import java.util.Collection;
import java.util.HashMap;
import java.util.Locale;
import java.util.Objects;
import org.jetbrains.annotations.NotNull;

public class CoreUtils {

    // This class's methods are static, use them as such.
    private CoreUtils() {
        throw new UnsupportedOperationException("Attempted instantiation of utility-type class");
    }

    // Turns a string that is `FORMATTED_LIKE_THIS` into `Formatted Like This`.
    @NotNull
    public static String formatConstantTypeStr(final @NotNull String str) {
        Objects.requireNonNull(str, "str");

        final String[] words = str.replace("_", " ").split(" ");
        for (int i = 0; i < words.length; i++) {
            words[i] = words[i].substring(0, 1).toUpperCase(Locale.ROOT) + words[i].substring(1)
                .toLowerCase(Locale.ROOT);
        }
        return String.join(" ", words);
    }

    // Check if a certain class exists. Used for compatibility purposes. Results are cached for performance.
    private static final HashMap<String, Boolean> classExistsCache = new HashMap<>();

    public static boolean classExists(final @NotNull String classpath) {
        Objects.requireNonNull(classpath, "classpath");

        if (classExistsCache.containsKey(classpath)) {
            return classExistsCache.get(classpath);
        }

        try {
            Class.forName(classpath);
            classExistsCache.put(classpath, true);
            return true;
        } catch (ClassNotFoundException ex) {
            classExistsCache.put(classpath, false);
            return false;
        }
    }

    public static double ensureBetween(final double min, final double max, final double current) {
        if (current < min) {
            return min;
        }
        if (current > max) {
            return min;
        }
        return current;
    }

    public static boolean containsIgnoreCase(final @NotNull Collection<String> collection,
        final @NotNull String what) {
        for (String str : collection) {
            if (str.equalsIgnoreCase(what)) {
                return true;
            }
        }
        return false;
    }

}
