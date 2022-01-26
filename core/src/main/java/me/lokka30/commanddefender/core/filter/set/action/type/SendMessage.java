package me.lokka30.commanddefender.core.filter.set.action.type;

import de.leonhard.storage.sections.FlatFileSection;
import me.lokka30.commanddefender.core.filter.set.action.Action;
import me.lokka30.commanddefender.core.filter.set.action.ActionHandler;
import me.lokka30.commanddefender.core.filter.set.condition.Condition;
import me.lokka30.commanddefender.core.player.UniversalPlayer;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class SendMessage implements ActionHandler {

    @Override
    public @NotNull String getIdentifier() {
        return "send-message";
    }

    @Override
    public @NotNull Optional<Condition> parse(@NotNull FlatFileSection section) {
        //TODO
        return Optional.empty();
    }

    public record SendMessageAction(
            @NotNull String[] message
    ) implements Action {

        @Override
        public void run(@NotNull UniversalPlayer player) {
            for(String line : message) {
                player.sendChatMessage(line);
            }
        }
    }

}