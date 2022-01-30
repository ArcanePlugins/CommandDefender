package me.lokka30.commanddefender.corebukkit.util;

import me.lokka30.commanddefender.core.debug.DebugCategory;
import me.lokka30.commanddefender.core.util.Constants;
import me.lokka30.commanddefender.core.util.Logger;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;

public class BukkitLogger implements Logger {

    @Override
    public void info(final @NotNull String msg) {
        Bukkit.getLogger().info(colorizeAndPrefix(msg));
    }

    @Override
    public void warning(final @NotNull String msg) {
        Bukkit.getLogger().warning(colorizeAndPrefix(msg));
    }

    @Override
    public void error(final @NotNull String msg) {
        Bukkit.getLogger().severe(colorizeAndPrefix(msg));
    }

    @NotNull
    private String colorizeAndPrefix(final @NotNull String msg) {
        return BukkitUtils.colorize(Constants.DEFAULT_PREFIX + msg);
    }

    @Override
    public void debug(final @NotNull DebugCategory category, final @NotNull String msg) {
        Bukkit.getLogger().info(colorizeAndPrefix(Constants.DEBUG_LOGGING_PREFIX + msg));
    }

}
