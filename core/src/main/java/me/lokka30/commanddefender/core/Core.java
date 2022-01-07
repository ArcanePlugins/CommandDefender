package me.lokka30.commanddefender.core;

import me.lokka30.commanddefender.core.debug.DebugHandler;
import me.lokka30.commanddefender.core.file.FileHandler;
import me.lokka30.commanddefender.core.log.Logger;
import org.jetbrains.annotations.NotNull;

public interface Core {

    @NotNull FileHandler getFileHandler();
    @NotNull Logger getCoreLogger();
    @NotNull DebugHandler getDebugHandler();
}