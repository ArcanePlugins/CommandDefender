package me.lokka30.commanddefender.corebukkit.command.commanddefender;

import me.lokka30.commanddefender.core.command.UniversalCommand;
import me.lokka30.commanddefender.core.command.UniversalCommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class CommandDefenderCommand implements UniversalCommand {

    @Override @NotNull
    public String[] labels() {
        return LABELS;
    }
    private final String[] LABELS = new String[]{"commanddefender", "cd", "cmddef"};

    @Override
    public void run(@NotNull UniversalCommandSender sender, @NotNull String[] args) {
        if(args.length == 1) {
            sender.sendMessage("Invalid usage: subcommand not specified.");
            sender.sendMessage("For a list of subcommands, run '/" + args[0] + " help'.");
        } else {
            switch(args[1].toUpperCase(Locale.ROOT)) {
                case "HELP":
                case "RELOAD":
                case "INFO":
                case "DEBUG":
                default:
                    sender.sendMessage("Invalid usage: '" + args[1] + "' is not a valid subcommand.");
                    sender.sendMessage("For a list of subcommands, run '/" + args[0] + " help'.");
                    break;
            }
        }
    }

    @Override
    public @NotNull List<String> generateTabSuggestions(@NotNull UniversalCommandSender sender, @NotNull String[] args) {
        if(args.length == 1) return Collections.emptyList();
        switch(args[1].toUpperCase(Locale.ROOT)) {
            case "HELP":
            case "RELOAD":
            case "INFO":
            case "DEBUG":
                //TODO
            default:
                return Collections.emptyList();
        }
    }

    @Override
    public @NotNull String baseUsage() {
        return "/" + labels()[0] + " <help/reload/info/debug>";
    }
}
