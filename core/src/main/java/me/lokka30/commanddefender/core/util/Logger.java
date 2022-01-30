package me.lokka30.commanddefender.core.util;

import me.lokka30.commanddefender.core.debug.DebugCategory;
import org.jetbrains.annotations.NotNull;

public interface Logger {
    void info(final @NotNull String msg);
    void warning(final @NotNull String msg);
    void error(final @NotNull String msg);
    void debug(final @NotNull DebugCategory category, final @NotNull String msg);
}
