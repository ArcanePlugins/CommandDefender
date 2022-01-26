package me.lokka30.commanddefender.core.filter.set.action.type;

import de.leonhard.storage.sections.FlatFileSection;
import me.lokka30.commanddefender.core.filter.set.action.Action;
import me.lokka30.commanddefender.core.filter.set.action.ActionHandler;
import me.lokka30.commanddefender.core.filter.set.condition.Condition;
import me.lokka30.commanddefender.core.player.UniversalPlayer;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class PlaySound implements ActionHandler {

    @Override
    public @NotNull String getIdentifier() {
        return "play-sound";
    }

    @Override
    public @NotNull Optional<Condition> parse(final @NotNull FlatFileSection section) {
        //TODO
        return Optional.empty();
    }

    public record PlaySoundAction(
            @NotNull String soundId,
            double volume,
            double pitch
    ) implements Action {

        @Override
        public void run(@NotNull UniversalPlayer player) {
            //TODO need to figure out how to implement this.
        }

    }
}
