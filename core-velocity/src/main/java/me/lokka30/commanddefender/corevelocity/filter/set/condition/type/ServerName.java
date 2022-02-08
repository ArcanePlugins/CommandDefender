package me.lokka30.commanddefender.corevelocity.filter.set.condition.type;

import de.leonhard.storage.sections.FlatFileSection;
import java.util.List;
import java.util.Optional;
import me.lokka30.commanddefender.core.filter.set.CommandSet;
import me.lokka30.commanddefender.core.filter.set.CommandSetPreset;
import me.lokka30.commanddefender.core.filter.set.condition.Condition;
import me.lokka30.commanddefender.core.util.universal.UniversalPlayer;
import org.jetbrains.annotations.NotNull;

public class ServerName
    //implements ConditionHandler
{

    //TODO fix and register

    //@Override
    public @NotNull String identifier() {
        return "server-name";
    }

    //@Override
    public @NotNull Optional<Condition> parse(@NotNull final CommandSet parentSet, @NotNull final FlatFileSection section) {
        List<String> contents = null;
        if(section.contains("conditions.server-name.contents")) {
            contents = section.getStringList("conditions.server-name.contents");
        } else {
            for(CommandSetPreset preset : parentSet.presets()) {
                if(preset.section().contains("conditions.server-name.contents")) {
                    contents = preset.section().getStringList("conditions.server-name.contents");
                    break;
                }
            }
        }
        if(contents == null) return Optional.empty();

        boolean inverse = false;
        if(section.contains("conditions.server-name.inverse")) {
            inverse = section.getBoolean("conditions.server-name.inverse");
        } else {
            for(CommandSetPreset preset : parentSet.presets()) {
                if(preset.section().contains("conditions.server-name.inverse")) {
                    inverse = preset.section().getBoolean("conditions.server-name.inverse");
                    break;
                }
            }
        }

        return Optional.of(new ServerNameCondition(
            contents, inverse
        ));
    }

    public record ServerNameCondition(
        @NotNull List<String> contents,
        boolean inverse
    ) implements Condition {

    @Override
    public boolean appliesTo(@NotNull UniversalPlayer player, @NotNull String[] args) {
        String PLAYER_SEVER_NAME_HERE = null; //TODO
        return contents.contains(PLAYER_SEVER_NAME_HERE) != inverse();
    }

}

}
