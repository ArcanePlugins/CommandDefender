package me.lokka30.commanddefender.core.util;

import org.jetbrains.annotations.NotNull;

public interface Colorizer {
    @NotNull
    String colorize(final @NotNull String msg);
}
