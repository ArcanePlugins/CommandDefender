package me.lokka30.commanddefender.corebukkit.util.universal;

import me.lokka30.commanddefender.core.Commons;
import me.lokka30.commanddefender.core.debug.DebugCategory;
import me.lokka30.commanddefender.core.util.universal.UniversalLogger;
import me.lokka30.commanddefender.corebukkit.util.BukkitUtils;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;

public class BukkitLogger implements UniversalLogger {

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
        return BukkitUtils.colorize(Commons.DEFAULT_PREFIX + msg);
    }

    @Override
    public void debug(final @NotNull DebugCategory category, final @NotNull String msg) {
        Bukkit.getLogger().info(colorizeAndPrefix(Commons.DEBUG_LOGGING_PREFIX + msg));
    }

}
