package me.lokka30.commanddefender.core.command.commanddefender;

import me.lokka30.commanddefender.core.Commons;
import me.lokka30.commanddefender.core.util.universal.UniversalCommand;
import me.lokka30.commanddefender.core.util.universal.UniversalCommandSender;
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
            sender.sendChatMessage("Invalid usage: subcommand not specified.");
            sender.sendChatMessage("For a list of subcommands, run '/" + args[0] + " help'.");
        } else {
            switch(args[1].toUpperCase(Locale.ROOT)) {
                case "HELP":
                    sender.sendChatMessage("Help subcommand not implemented yet.");
                    break;
                case "RELOAD":
                    if(sender.name().equals("lokka30")) {
                        sender.sendChatMessage("Reloading");
                        Commons.core().fileHandler().load(true);
                        Commons.core().debugHandler().load();
                        Commons.core().commandFilter().load();
                        sender.sendChatMessage("Reload complete");
                    }
                    break;
                case "INFO":
                case "BACKUP":
                case "DEBUG":
                    sender.sendChatMessage("Subcommand not implemented yet.");
                    break;
                default:
                    sender.sendChatMessage("Invalid usage: '" + args[1] + "' is not a valid subcommand.");
                    sender.sendChatMessage("For a list of subcommands, run '/" + args[0] + " help'.");
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
            case "BACKUP":
            case "DEBUG":
                //TODO
                return Collections.singletonList("Subcommand tab suggestions not implemented yet.");
            default:
                return Collections.emptyList();
        }
    }

    @Override
    public @NotNull String baseUsage() {
        return "/" + labels()[0] + " <help/reload/info/debug>";
    }
}
