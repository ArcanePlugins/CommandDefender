package me.lokka30.commanddefender;

import me.lokka30.commanddefender.utils.ListMode;
import me.lokka30.commanddefender.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class CommandManager {

    private final CommandDefender instance;

    public CommandManager(final CommandDefender instance) {
        this.instance = instance;
    }

    public List<String[]> listedCommands;
    public ListMode commandsListMode;

    public void load() {
        listedCommands = new ArrayList<>();
        for (String listedCommand : instance.settingsFile.getConfig().getStringList("commands.list")) {
            listedCommands.add(listedCommand.split(" "));
        }

        commandsListMode = ListMode.parse(instance.settingsFile.getConfig().getString("commands.mode"));
    }

    private boolean isListed(String[] ranCommand) {

        for (final String[] listedCommand : listedCommands) {
            for (int i = 0; i < Math.min(listedCommand.length, ranCommand.length); i++) {
                final String ranCommandCurrent = ranCommand[i].toLowerCase();
                final String listedCommandCurrent = listedCommand[i].toLowerCase();

                if (i == 0 && listedCommandCurrent.equals("/*")) {
                    continue;
                }

                if (listedCommandCurrent.equals("*")) {
                    continue;
                }

                if (listedCommandCurrent.equals(ranCommandCurrent + "*")) {
                    continue;
                }

                if (ranCommandCurrent.equals(listedCommandCurrent) || ranCommandCurrent.equals(listedCommandCurrent.replace("\\*", "*"))) {
                    return true;
                }

                break;
            }
        }

        return false;
    }

    public boolean isBlocked(String ranCommand) {
        switch (commandsListMode) {
            case ALL:
                return true;
            case WHITELIST:
                return !isListed(ranCommand.split(" "));
            case BLACKLIST:
                return isListed(ranCommand.split(" "));
            case INVALID:
                Utils.logger.error("Invalid commands list mode in the settings file! ALL commands will be blocked until you fix this!");
                return true;
            default:
                throw new IllegalStateException("Unexpected ListMode " + commandsListMode.toString() + "!");
        }
    }
}
