package me.lokka30.commanddefender.core.command.commanddefender.subcommand;

import me.lokka30.commanddefender.core.command.UniversalCommand;
import me.lokka30.commanddefender.core.command.UniversalCommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;

public class ExecuteSubcommand implements UniversalCommand {

    @Override
    public @NotNull String getBaseLabel() {
        return "execute";
    }

    @Override
    public @NotNull HashSet<String> getBaseAliasLabels() {
        return new HashSet<>();
    }

    @Override
    public void execute(@NotNull UniversalCommandSender sender, @NotNull String[] args) {
        //TODO
    }

    @Override
    public @NotNull HashSet<String> getTabSuggestions(@NotNull UniversalCommandSender sender, @NotNull String[] args) {
        return new HashSet<>();
    }
}
