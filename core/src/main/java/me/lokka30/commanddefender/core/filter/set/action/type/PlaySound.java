package me.lokka30.commanddefender.core.filter.set.action.type;

import de.leonhard.storage.sections.FlatFileSection;
import me.lokka30.commanddefender.core.filter.set.CommandSet;
import me.lokka30.commanddefender.core.filter.set.action.Action;
import me.lokka30.commanddefender.core.filter.set.action.ActionHandler;
import me.lokka30.commanddefender.core.util.universal.UniversalPlayer;
import me.lokka30.commanddefender.core.util.universal.UniversalSound;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class PlaySound implements ActionHandler {

    CommandSet parentSet;
    @Override
    public @NotNull CommandSet parentSet() {
        return parentSet;
    }

    @Override
    public @NotNull String identifier() {
        return "play-sound";
    }

    @Override
    public @NotNull Optional<Action> parse(final @NotNull CommandSet parentSet, final @NotNull FlatFileSection section) {
        this.parentSet = parentSet;
        //TODO parse from command set and presets
        return Optional.empty();
    }

    public record PlaySoundAction(
            @NotNull UniversalSound sound
    ) implements Action {

        @Override
        public void run(@NotNull UniversalPlayer player) {
            player.playSound(sound);
        }

    }
}
