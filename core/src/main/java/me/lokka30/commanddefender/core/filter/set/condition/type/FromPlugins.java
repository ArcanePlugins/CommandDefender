package me.lokka30.commanddefender.core.filter.set.condition.type;

import de.leonhard.storage.sections.FlatFileSection;
import me.lokka30.commanddefender.core.filter.set.condition.Condition;
import me.lokka30.commanddefender.core.filter.set.condition.ConditionHandler;
import me.lokka30.commanddefender.core.player.UniversalPlayer;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class FromPlugins implements ConditionHandler {

    @Override
    public @NotNull String getIdentifier() {
        return "from-plugins";
    }

    @Override
    public @NotNull Optional<Condition> parse(FlatFileSection section) {

        return Optional.empty();
    }

    public static class FromPluginsCondition implements Condition {
        public FromPluginsCondition(
                final @NotNull String[] plugins,
                final boolean inverse
        ) {
            this.plugins = plugins;
            this.inverse = inverse;
        }
        private final String[] plugins;
        private final boolean inverse;

        @Override
        public boolean appliesTo(@NotNull UniversalPlayer player, @NotNull String[] args) {
            return false != inverse; //TODO need to figure out how to implement this.
        }
    }

}
