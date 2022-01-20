package me.lokka30.commanddefender.core.filter.set.action.type;

import me.lokka30.commanddefender.core.filter.set.action.Action;
import me.lokka30.commanddefender.core.player.UniversalPlayer;
import org.jetbrains.annotations.NotNull;

public class SendMessageAction implements Action {

    public SendMessageAction(
            final @NotNull String[] message
    ) {
        this.message = message;
    }

    private final String[] message;

    @Override
    public void run(@NotNull UniversalPlayer player) {
        for(String line : message) {
            player.sendChatMessage(line);
        }
    }
}
