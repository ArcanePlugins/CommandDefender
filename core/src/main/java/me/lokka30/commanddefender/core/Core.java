package me.lokka30.commanddefender.core;

import me.lokka30.commanddefender.core.file.FileHandler;
import me.lokka30.commanddefender.core.util.universal.UniversalLogger;
import org.jetbrains.annotations.NotNull;

public interface Core {

    @NotNull UniversalLogger logger();

    @NotNull String colorize(final @NotNull String msg);

    @NotNull FileHandler fileHandler();

    void updateTabCompletionForAllPlayers();

}