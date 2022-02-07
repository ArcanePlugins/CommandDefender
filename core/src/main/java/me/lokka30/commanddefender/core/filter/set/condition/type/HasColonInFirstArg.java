package me.lokka30.commanddefender.core.filter.set.condition.type;

import de.leonhard.storage.sections.FlatFileSection;
import java.util.Arrays;
import java.util.Optional;
import me.lokka30.commanddefender.core.Commons;
import me.lokka30.commanddefender.core.debug.DebugCategory;
import me.lokka30.commanddefender.core.debug.DebugHandler;
import me.lokka30.commanddefender.core.filter.set.CommandSet;
import me.lokka30.commanddefender.core.filter.set.CommandSetPreset;
import me.lokka30.commanddefender.core.filter.set.condition.Condition;
import me.lokka30.commanddefender.core.filter.set.condition.ConditionHandler;
import me.lokka30.commanddefender.core.util.universal.UniversalPlayer;
import org.jetbrains.annotations.NotNull;

public class HasColonInFirstArg implements ConditionHandler {

    @Override
    public @NotNull String identifier() {
        return "has-colon-in-first-arg";
    }

    @Override
    public @NotNull Optional<Condition> parse(final @NotNull CommandSet parentSet,
        final @NotNull FlatFileSection section) {
        final String path = "conditions." + identifier();

        if (section.get(path, false)) {

            // START DEBUG LOG
            if (DebugHandler.isDebugCategoryEnabled(DebugCategory.CONDITIONS)) {
                Commons.core().logger().debug(DebugCategory.CONDITIONS, String.format(
                    "Parsing '%s' in command set '%s': true",
                    identifier(),
                    parentSet.identifier()
                ));
            }
            // END DEBUG LOG

            return Optional.of(new HasColonInFirstArgCondition());
        } else {
            for (CommandSetPreset preset : parentSet.presets()) {
                if (preset.section().get(path, false)) {

                    // START DEBUG LOG
                    if (DebugHandler.isDebugCategoryEnabled(DebugCategory.CONDITIONS)) {
                        Commons.core().logger().debug(DebugCategory.CONDITIONS, String.format(
                            "Parsing '%s' in command set '%s': true, in preset '%s': true",
                            identifier(),
                            parentSet.identifier(),
                            preset.identifier()
                        ));
                    }
                    // END DEBUG LOG

                    return Optional.of(new HasColonInFirstArgCondition());
                }
            }
        }

        // START DEBUG LOG
        if (DebugHandler.isDebugCategoryEnabled(DebugCategory.CONDITIONS)) {
            Commons.core().logger().debug(DebugCategory.CONDITIONS, String.format(
                "Parsing '%s' in command set '%s': false",
                identifier(),
                parentSet.identifier()
            ));
        }
        // END DEBUG LOG

        return Optional.empty();
    }

    public record HasColonInFirstArgCondition() implements Condition {

        @Override
        public boolean appliesTo(@NotNull UniversalPlayer player, @NotNull String[] args) {

            final boolean result = args[0].contains(":");

            // START DEBUG LOG
            if (DebugHandler.isDebugCategoryEnabled(DebugCategory.CONDITIONS)) {
                Commons.core().logger().debug(DebugCategory.CONDITIONS, String.format(
                    "Checking appliesTo for '%s' for player '%s' with args '%s' -> result: '%s'.",
                    this.getClass().getSimpleName(),
                    player.name(),
                    Arrays.toString(args),
                    result
                ));
            }
            // END DEBUG LOG

            return result;
        }

    }

}
