package me.lokka30.commanddefender.core.command;

import org.jetbrains.annotations.NotNull;

import java.util.HashSet;

public interface UniversalCommand {

    @NotNull
    HashSet<String> getLabels();

    void execute(@NotNull final UniversalCommandSender sender, @NotNull final String[] args);

    @NotNull
    HashSet<String> getTabSuggestions(@NotNull final UniversalCommandSender sender, @NotNull final String[] args);

}
