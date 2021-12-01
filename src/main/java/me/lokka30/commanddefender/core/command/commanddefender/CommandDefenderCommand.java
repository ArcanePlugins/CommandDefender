package me.lokka30.commanddefender.core.command.commanddefender;

import me.lokka30.commanddefender.core.command.UniversalCommand;
import me.lokka30.commanddefender.core.command.UniversalCommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.HashSet;

public class CommandDefenderCommand implements UniversalCommand {

    private final HashSet<String> BASE_ALIAS_LABELS = new HashSet<>(Collections.singletonList("cd"));

    @Override
    public @NotNull String getBaseLabel() {
        return "commanddefender";
    }

    @Override
    public @NotNull HashSet<String> getBaseAliasLabels() {
        return BASE_ALIAS_LABELS;
    }

    @Override
    public void execute(@NotNull UniversalCommandSender sender, @NotNull String[] args) {
        //TODO
    }

    @Override
    public @NotNull HashSet<String> getTabSuggestions(@NotNull UniversalCommandSender sender, @NotNull String[] args) {
        //TODO
        return null;
    }
}
