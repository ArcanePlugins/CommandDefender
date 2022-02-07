package me.lokka30.commanddefender.corebukkit.filter.set.condition.type;

import de.leonhard.storage.sections.FlatFileSection;
import java.util.List;
import java.util.Optional;
import me.lokka30.commanddefender.core.filter.set.CommandSet;
import me.lokka30.commanddefender.core.filter.set.CommandSetPreset;
import me.lokka30.commanddefender.core.filter.set.condition.Condition;
import me.lokka30.commanddefender.core.filter.set.condition.ConditionHandler;
import me.lokka30.commanddefender.core.util.universal.UniversalPlayer;
import me.lokka30.commanddefender.corebukkit.util.universal.BukkitPlatformHandler;
import org.jetbrains.annotations.NotNull;

public class WorldName
    implements ConditionHandler
{

    @Override
    public @NotNull String identifier() {
        return "world-name";
    }

    @Override
    public @NotNull Optional<Condition> parse(@NotNull final CommandSet parentSet, @NotNull final FlatFileSection section) {
        List<String> contents = null;
        if(section.contains("conditions.world-name.contents")) {
            contents = section.getStringList("conditions.world-name.contents");
        } else {
            for(CommandSetPreset preset : parentSet.presets()) {
                if(preset.section().contains("conditions.world-name.contents")) {
                    contents = preset.section().getStringList("conditions.world-name.contents");
                    break;
                }
            }
        }
        if(contents == null) return Optional.empty();

        boolean inverse = false;
        if(section.contains("conditions.world-name.inverse")) {
            inverse = section.getBoolean("conditions.world-name.inverse");
        } else {
            for(CommandSetPreset preset : parentSet.presets()) {
                if(preset.section().contains("conditions.world-name.inverse")) {
                    inverse = preset.section().getBoolean("conditions.world-name.inverse");
                    break;
                }
            }
        }

        return Optional.of(new WorldNameCondition(
            contents, inverse
        ));
    }

    public record WorldNameCondition(
            @NotNull List<String> contents,
            boolean inverse
    ) implements Condition {

        @Override
        public boolean appliesTo(@NotNull UniversalPlayer player, @NotNull String[] args) {
            return contents.contains(BukkitPlatformHandler.universalPlayerToBukkit(player).player().getWorld().getName()) != inverse();
        }

    }

}
