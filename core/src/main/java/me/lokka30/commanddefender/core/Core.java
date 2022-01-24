package me.lokka30.commanddefender.core;

import me.lokka30.commanddefender.core.log.Logger;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;

public interface Core {

    @NotNull Logger getCoreLogger();

    @NotNull String colorize(final @NotNull String msg);

    @NotNull HashSet<String> getRegisteredConditions();
    @NotNull HashSet<String> getRegisteredActions();
    @NotNull HashSet<String> getRegisteredOptions();

}