package me.lokka30.commanddefender.core.filter.set.condition.type;

import de.leonhard.storage.sections.FlatFileSection;
import me.lokka30.commanddefender.core.Commons;
import me.lokka30.commanddefender.core.filter.set.CommandSet;
import me.lokka30.commanddefender.core.filter.set.condition.Condition;
import me.lokka30.commanddefender.core.filter.set.condition.ConditionHandler;
import me.lokka30.commanddefender.core.util.ModalList;
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
            @NotNull ModalList<String> plugins,
            boolean inverse
    ) implements Condition {

        @Override
        public boolean appliesTo(@NotNull UniversalPlayer player, @NotNull String[] args) {
            final String pluginName = Commons.getCore().pluginThatRegisteredCommand(args[0].substring(1));
            if(pluginName == null) { return false; }
            return plugins.includes(pluginName) != inverse();
        }

    }

}
