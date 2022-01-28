package me.lokka30.commanddefender.core.util;

import org.jetbrains.annotations.NotNull;

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

}
