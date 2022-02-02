package me.lokka30.commanddefender.core;

import me.lokka30.commanddefender.core.file.FileHandler;
import me.lokka30.commanddefender.core.filter.CommandFilter;
import me.lokka30.commanddefender.core.util.universal.PlatformHandler;
import me.lokka30.commanddefender.core.util.universal.UniversalLogger;
import org.jetbrains.annotations.NotNull;

public interface Core {

    /* Data */

    @NotNull
    FileHandler fileHandler();

    @NotNull
    String dataFolder();

    /* Utilities */

    @NotNull
    UniversalLogger logger();

    @NotNull
    String colorize(final @NotNull String msg);

    /* Other */

    @NotNull
    CommandFilter commandFilter();

    @NotNull
    PlatformHandler platformHandler();

    void updateTabCompletionForAllPlayers();

}