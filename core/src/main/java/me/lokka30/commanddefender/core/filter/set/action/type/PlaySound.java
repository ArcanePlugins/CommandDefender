package me.lokka30.commanddefender.core.filter.set.action.type;

import de.leonhard.storage.sections.FlatFileSection;
import me.lokka30.commanddefender.core.Commons;
import me.lokka30.commanddefender.core.filter.set.CommandSet;
import me.lokka30.commanddefender.core.filter.set.CommandSetPreset;
import me.lokka30.commanddefender.core.filter.set.action.Action;
import me.lokka30.commanddefender.core.filter.set.action.ActionHandler;
import me.lokka30.commanddefender.core.util.universal.UniversalPlayer;
import me.lokka30.commanddefender.core.util.universal.UniversalSound;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class PlaySound implements ActionHandler {

    @Override
    public @NotNull String identifier() {
        return "play-sound";
    }

    @Override
    public @NotNull Optional<Action> parse(final @NotNull CommandSet parentSet, final @NotNull FlatFileSection section) {
        final String path = "actions." + identifier();

        String soundId = null;
        if(section.contains(path + ".id")) {
            soundId = section.getString(path + ".id");
        } else {
            for(CommandSetPreset preset : parentSet.presets()) {
                if(preset.section().contains(path + ".id")) {
                    soundId = preset.section().getString(path + ".id");
                    break;
                }
            }
        }
        if(soundId == null) {
            Commons.core.logger().error("Command set '&b" + parentSet.identifier() + "&7' has an invalid '&b" + identifier() + "&7'" +
                    " action configured: the '&bid&&' value was not set. Fix this ASAP.");
            return Optional.empty();
        }

        Double volume = null;
        if(section.contains(path + ".volume")) {
            volume = section.getDouble(path + ".volume");
        } else {
            for(CommandSetPreset preset : parentSet.presets()) {
                if(preset.section().contains(path + ".volume")) {
                    volume = preset.section().getDouble(path + ".volume");
                    break;
                }
            }
        }
        if(volume == null) {
            Commons.core.logger().error("Command set '&b" + parentSet.identifier() + "&7' has an invalid '&b" + identifier() + "&7'" +
                    " action configured: the '&bvolume&&' value was not set. Fix this ASAP.");
            return Optional.empty();
        }

        Double pitch = null;
        if(section.contains(path + ".pitch")) {
            pitch = section.getDouble(path + ".pitch");
        } else {
            for(CommandSetPreset preset : parentSet.presets()) {
                if(preset.section().contains(path + ".pitch")) {
                    pitch = preset.section().getDouble(path + ".pitch");
                    break;
                }
            }
        }
        if(pitch == null) {
            Commons.core.logger().error("Command set '&b" + parentSet.identifier() + "&7' has an invalid '&b" + identifier() + "&7'" +
                    " action configured: the '&bpitch&&' value was not set. Fix this ASAP.");
            return Optional.empty();
        }

        return Optional.of(new PlaySoundAction(
                parentSet,
                Commons.core.platformHandler().buildPlatformSpecificSound(soundId, volume, pitch)
        ));
    }

    public record PlaySoundAction(
            @NotNull CommandSet parentSet,
            @NotNull UniversalSound sound
    ) implements Action {

        @Override
        public void run(@NotNull UniversalPlayer player) {
            player.playSound(sound);
        }

    }
}
