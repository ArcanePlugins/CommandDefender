package me.lokka30.commanddefender.corebukkit.logging;

import me.lokka30.commanddefender.core.debug.DebugCategory;
import me.lokka30.commanddefender.core.log.Logger;
import me.lokka30.commanddefender.core.util.Constants;
import me.lokka30.commanddefender.corebukkit.util.BukkitUtils;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;

public class BukkitLogger implements Logger {

    @Override
    public void info(final @NotNull String msg) {
        Bukkit.getLogger().info(simpleColorize(msg));
    }

    @Override
    public void warning(final @NotNull String msg) {
        Bukkit.getLogger().warning(simpleColorize(msg));
    }

    @Override
    public void error(final @NotNull String msg) {
        Bukkit.getLogger().severe(simpleColorize(msg));
    }

    @NotNull
    private String simpleColorize(final @NotNull String msg) {
        return BukkitUtils.getColorizer().colorize(Constants.DEFAULT_PREFIX + msg);
    }

    @Override
    public void debug(final @NotNull DebugCategory category, final @NotNull String msg) {
        Bukkit.getLogger().info(BukkitUtils.getColorizer().colorize(
                String.format(
                        Constants.DEBUG_LOGGING_PREFIX,
                        BukkitUtils.formatEnumConstant(category.toString())
                ) + msg
        ));
    }
}
