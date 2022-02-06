package me.lokka30.commanddefender.core.filter.set.condition.type;

import de.leonhard.storage.sections.FlatFileSection;
import me.lokka30.commanddefender.core.Commons;
import me.lokka30.commanddefender.core.debug.DebugCategory;
import me.lokka30.commanddefender.core.debug.DebugHandler;
import me.lokka30.commanddefender.core.filter.set.CommandSet;
import me.lokka30.commanddefender.core.filter.set.CommandSetPreset;
import me.lokka30.commanddefender.core.filter.set.condition.Condition;
import me.lokka30.commanddefender.core.filter.set.condition.ConditionHandler;
import me.lokka30.commanddefender.core.util.CoreUtils;
import me.lokka30.commanddefender.core.util.universal.UniversalPlayer;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;

public class FromPlugins implements ConditionHandler {

    @Override
    public @NotNull String identifier() {
        return "from-plugins";
    }

    @Override
    public @NotNull Optional<Condition> parse(final @NotNull CommandSet parentSet, final @NotNull FlatFileSection section) {
        final String mainPath = "conditions." + identifier();

        List<String> contents = null;
        final String contentsPath = mainPath + ".contents";
        if(section.contains(contentsPath)) {
            contents = section.getStringList(contentsPath);
        } else {
            for(CommandSetPreset preset : parentSet.presets()) {
                if (preset.section().contains(contentsPath)) {
                    contents = section.getStringList(contentsPath);
                    break;
                }
            }
        }
        if(contents == null) { return Optional.empty(); }

        boolean inverse = false;
        final String inversePath = mainPath + ".inverse";
        if(section.contains(inversePath)) {
            inverse = section.getBoolean(inversePath);
        } else {
            for(CommandSetPreset preset : parentSet.presets()) {
                if (preset.section().contains(inversePath)) {
                    inverse = section.getBoolean(inversePath);
                    break;
                }
            }
        }

        if(DebugHandler.isDebugCategoryEnabled(DebugCategory.CONDITIONS)) {
            Commons.core().logger().debug(DebugCategory.CONDITIONS, String.format(
                    "Parsed FromPlugins condition with plugins: %s, inverse: %s",
                    contents,
                    inverse
            ));
        }

        return Optional.of(new FromPluginsCondition(contents, inverse));
    }

    public record FromPluginsCondition(
            @NotNull List<String> plugins,
            boolean inverse
    ) implements Condition {

        @Override
        public boolean appliesTo(@NotNull UniversalPlayer player, @NotNull String[] args) {
            final String pluginName = Commons.core().pluginThatRegisteredCommand(args[0].substring(1));
            if(DebugHandler.isDebugCategoryEnabled(DebugCategory.CONDITIONS)) {
                Commons.core().logger().debug(DebugCategory.CONDITIONS, String.format(
                        "FromPlugins: command %s is owned by %s",
                        args[0],
                        pluginName == null ? "(Unknown Plugin)" : pluginName
                ));
            }
            if(pluginName == null) { return false; }
            return CoreUtils.containsIgnoreCase(plugins, pluginName) != inverse();
        }

    }

}
