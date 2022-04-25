package me.lokka30.commanddefender.core.command.commanddefender;

import java.util.Collections;
import java.util.List;
import java.util.Locale;
import me.lokka30.commanddefender.core.Commons;
import me.lokka30.commanddefender.core.util.universal.UniversalCommand;
import me.lokka30.commanddefender.core.util.universal.UniversalCommandSender;
import org.jetbrains.annotations.NotNull;

public class CommandDefenderCommand implements UniversalCommand {

    @Override
    @NotNull
    public String[] labels() {
        return LABELS;
    }

    private final String[] LABELS = new String[]{"commanddefender", "cd", "cmddef"};

    @Override
    public void run(@NotNull UniversalCommandSender sender, @NotNull String[] args) {
        if (args.length == 1) {
            sender.sendChatMessage("&cInvalid usage: subcommand not specified.");
            sender.sendChatMessage("&7For a list of subcommands, run '/" + args[0] + " help'.");
        } else {
            switch (args[1].toUpperCase(Locale.ROOT)) {
                case "HELP":
                    sender.sendChatMessage("&cHelp subcommand not implemented yet.");
                    break;
                case "RELOAD":
                    if(!sender.hasPermission("commanddefender.command.commanddefender.reload")) {
                        sender.sendChatMessage("&cNo permission");
                        return;
                    }
                    sender.sendChatMessage("&7Reloading...");
                    try {
                        Commons.core().fileHandler().load(true);
                        Commons.core().debugHandler().load();
                        Commons.core().commandFilter().load();
                        sender.sendChatMessage("&aReload complete :)");
                    } catch (Exception ex) {
                        sender.sendChatMessage(
                            "&cErrors occured whilst reloading - check console immediately. :(");
                        ex.printStackTrace();
                    }
                    break;
                case "INFO":
                case "BACKUP":
                case "DEBUG":
                    sender.sendChatMessage("&cSubcommand not implemented yet.");
                    break;
                default:
                    sender.sendChatMessage(
                        "&cInvalid usage: '" + args[1] + "' is not a valid subcommand.");
                    sender.sendChatMessage(
                        "&7For a list of subcommands, run '/" + args[0] + " help'.");
                    break;
            }
        }
    }

    @Override
    public @NotNull List<String> generateTabSuggestions(@NotNull UniversalCommandSender sender,
        @NotNull String[] args) {
        if (args.length == 1) {
            return Collections.emptyList();
        }
        switch (args[1].toUpperCase(Locale.ROOT)) {
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
