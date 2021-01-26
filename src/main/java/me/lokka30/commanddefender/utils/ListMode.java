package me.lokka30.commanddefender.utils;

public enum ListMode {
    WHITELIST, BLACKLIST, ALL, INVALID;

    public static ListMode parse(String str) {
        if (str == null || str.isEmpty()) {
            return INVALID;
        }

        try {
            return valueOf(str.toUpperCase());
        } catch (IllegalStateException ex) {
            return INVALID;
        }

    }
}
