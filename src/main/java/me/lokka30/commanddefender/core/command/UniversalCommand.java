package me.lokka30.commanddefender.core.command;

import org.jetbrains.annotations.NotNull;

import java.util.HashSet;

public interface UniversalCommand {

    // lists the main label that the command can be ran by
    @NotNull
    String getBaseLabel();

    // lists the aliases that a command can be ran by, as alternatives to the base label
    @NotNull
    HashSet<String> getBaseAliasLabels();

    void execute(@NotNull final UniversalCommandSender sender, @NotNull final String[] args);

    @NotNull
    HashSet<String> getTabSuggestions(@NotNull final UniversalCommandSender sender, @NotNull final String[] args);

}
