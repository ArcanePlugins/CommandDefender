package me.lokka30.commanddefender.core;

import me.lokka30.commanddefender.core.file.FileHandler;
import me.lokka30.commanddefender.core.file.external.type.ExternalFile;
import me.lokka30.commanddefender.core.filter.CommandFilter;
import me.lokka30.commanddefender.core.util.universal.PlatformHandler;
import me.lokka30.commanddefender.core.util.universal.UniversalLogger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Set;

public interface Core {

    @NotNull
    FileHandler fileHandler();

    @NotNull
    String dataFolder();

    @NotNull
    UniversalLogger logger();

    @NotNull
    String colorize(final @NotNull String msg);

    @NotNull
    CommandFilter commandFilter();

    @NotNull
    PlatformHandler platformHandler();

    void updateTabCompletionForAllPlayers();

    @Nullable
    String pluginThatRegisteredCommand(final @NotNull String command);

    @NotNull
    Set<String> aliasesOfCommand(final @NotNull String command);

    void replaceFileWithDefault(final ExternalFile externalFile);

    void checkForUpdates(); //TODO

}