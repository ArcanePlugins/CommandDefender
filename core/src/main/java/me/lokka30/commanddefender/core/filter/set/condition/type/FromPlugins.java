package me.lokka30.commanddefender.core.filter.set.condition.type;

import de.leonhard.storage.sections.FlatFileSection;
import me.lokka30.commanddefender.core.filter.set.CommandSet;
import me.lokka30.commanddefender.core.filter.set.condition.Condition;
import me.lokka30.commanddefender.core.filter.set.condition.ConditionHandler;
import me.lokka30.commanddefender.core.util.universal.UniversalPlayer;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class FromPlugins implements ConditionHandler {

    @Override
    public @NotNull String identifier() {
        return "from-plugins";
    }

    @Override
    public @NotNull Optional<Condition> parse(final @NotNull CommandSet parentSet, final @NotNull FlatFileSection section) {
        //TODO parse from command set and presets
        return Optional.empty();
    }

    public record FromPluginsCondition(
            @NotNull String[] plugins,
            boolean inverse
    ) implements Condition {
        @Override
        public boolean appliesTo(@NotNull UniversalPlayer player, @NotNull String[] args) {
            boolean isFromPlugin = false;
            //TODO
            return isFromPlugin != inverse();
        }
    }

}
