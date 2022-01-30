package me.lokka30.commanddefender.core.util.universal;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface UniversalCommand {

    @NotNull
    String[] labels();

    void run(final @NotNull UniversalCommandSender sender, final @NotNull String[] args);

    @NotNull
    List<String> generateTabSuggestions(final @NotNull UniversalCommandSender sender, final @NotNull String[] args);

    @NotNull
    String baseUsage();

}
