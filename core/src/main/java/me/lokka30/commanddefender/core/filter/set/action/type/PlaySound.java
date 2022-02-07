package me.lokka30.commanddefender.core.filter.set.action.type;

import de.leonhard.storage.sections.FlatFileSection;
import java.util.Optional;
import me.lokka30.commanddefender.core.Commons;
import me.lokka30.commanddefender.core.debug.DebugCategory;
import me.lokka30.commanddefender.core.debug.DebugHandler;
import me.lokka30.commanddefender.core.filter.set.CommandSet;
import me.lokka30.commanddefender.core.filter.set.CommandSetPreset;
import me.lokka30.commanddefender.core.filter.set.action.Action;
import me.lokka30.commanddefender.core.filter.set.action.ActionHandler;
import me.lokka30.commanddefender.core.util.universal.UniversalPlayer;
import me.lokka30.commanddefender.core.util.universal.UniversalSound;
import org.jetbrains.annotations.NotNull;

/**
 * @author lokka30 (original author)
 * @author drives_a_ford (stack overflow fix)
 */
public class PlaySound implements ActionHandler {

    @Override
    public @NotNull String identifier() {
        return "play-sound";
    }

    @Override
    public @NotNull Optional<Action> parse(final @NotNull CommandSet parentSet,
        final @NotNull FlatFileSection section) {
        final boolean debugLog = DebugHandler.isDebugCategoryEnabled(DebugCategory.ACTIONS);

        final String path = "actions." + identifier();

        String soundId = null;
        if (section.contains(path + ".id")) {
            soundId = section.getString(path + ".id");
        } else {
            for (CommandSetPreset preset : parentSet.presets()) {
                if (preset.section().contains(path + ".id")) {
                    soundId = preset.section().getString(path + ".id");
                    break;
                }
            }
        }
        if (debugLog) {
            Commons.core().logger().debug(DebugCategory.CONDITIONS, String.format(
                "PlaySound - the sound ID is '&b%s&7'.",
                soundId == null ? "&cN/A" : soundId
            ));
        }
        if (soundId == null) {
            return Optional.empty();
        }

        Double volume = null;
        if (section.contains(path + ".volume")) {
            volume = section.getDouble(path + ".volume");
        } else {
            for (CommandSetPreset preset : parentSet.presets()) {
                if (preset.section().contains(path + ".volume")) {
                    volume = preset.section().getDouble(path + ".volume");
                    break;
                }
            }
        }
        if (debugLog) {
            Commons.core().logger().debug(DebugCategory.CONDITIONS, String.format(
                "PlaySound - the sound volume is '&b%s&7'.",
                volume == null ? "&cN/A" : volume
            ));
        }
        if (volume == null) {
            Commons.core().logger().error(
                "Command set '&b" + parentSet.identifier() + "&7' has an invalid '&b" + identifier()
                    + "&7'" +
                    " action configured: the '&bvolume&7' value was not set. Fix this ASAP.");
            return Optional.empty();
        }

        Double pitch = null;
        if (section.contains(path + ".pitch")) {
            pitch = section.getDouble(path + ".pitch");
        } else {
            for (CommandSetPreset preset : parentSet.presets()) {
                if (preset.section().contains(path + ".pitch")) {
                    pitch = preset.section().getDouble(path + ".pitch");
                    break;
                }
            }
        }
        if (debugLog) {
            Commons.core().logger().debug(DebugCategory.CONDITIONS, String.format(
                "PlaySound - the sound pitch is '&b%s&7'.",
                pitch == null ? "&cN/A" : pitch
            ));
        }
        if (pitch == null) {
            Commons.core().logger().error(
                "Command set '&b" + parentSet.identifier() + "&7' has an invalid '&b" + identifier()
                    + "&7'" +
                    " action configured: the '&bpitch&7' value was not set. Fix this ASAP.");
            return Optional.empty();
        }

        final UniversalSound platformSpecificSound = Commons.core().platformHandler()
            .buildPlatformSpecificSound(soundId, volume, pitch);
        if (debugLog) {
            Commons.core().logger().debug(DebugCategory.CONDITIONS, String.format(
                "PlaySound - the universal sound has been built, using the class '&b%s&7'.",
                platformSpecificSound.getClass().getSimpleName()
            ));
        }

        return Optional.of(new PlaySoundAction(
            platformSpecificSound
        ));
    }

    public record PlaySoundAction(
        @NotNull UniversalSound sound
    ) implements Action {

        @Override
        public void run(@NotNull UniversalPlayer player, @NotNull String[] args) {
            if (DebugHandler.isDebugCategoryEnabled(DebugCategory.CONDITIONS)) {
                Commons.core().logger().debug(DebugCategory.CONDITIONS, String.format(
                    "PlaySound - playing sound &b%s&7 to &f%s&7.",
                    sound().identifier(),
                    player.name()
                ));
            }

            player.playSound(sound);
        }

    }

}
