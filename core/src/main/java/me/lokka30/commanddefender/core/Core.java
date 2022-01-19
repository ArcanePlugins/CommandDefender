package me.lokka30.commanddefender.core;

import me.lokka30.commanddefender.core.debug.DebugHandler;
import me.lokka30.commanddefender.core.file.FileHandler;
import me.lokka30.commanddefender.core.log.Logger;
import org.jetbrains.annotations.NotNull;

public interface Core {

    @NotNull Logger logger();
    @NotNull FileHandler fileHandler();
    @NotNull DebugHandler debugHandler();

    @NotNull String colorize(final @NotNull String msg);
}