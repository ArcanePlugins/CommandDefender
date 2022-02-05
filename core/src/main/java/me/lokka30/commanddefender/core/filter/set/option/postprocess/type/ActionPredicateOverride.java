package me.lokka30.commanddefender.core.filter.set.option.postprocess.type;

import de.leonhard.storage.sections.FlatFileSection;
import me.lokka30.commanddefender.core.Commons;
import me.lokka30.commanddefender.core.debug.DebugCategory;
import me.lokka30.commanddefender.core.debug.DebugHandler;
import me.lokka30.commanddefender.core.filter.set.CommandSet;
import me.lokka30.commanddefender.core.filter.set.option.Option;
import me.lokka30.commanddefender.core.filter.set.option.OptionHandler;
import me.lokka30.commanddefender.core.filter.set.option.postprocess.PostProcessOption;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class ActionPredicateOverride implements OptionHandler {
    
    @Override
    public @NotNull String identifier() {
        return "action-predicate-override";
    }

    @Override
    public @NotNull Optional<Option> parse(@NotNull CommandSet parentSet, @NotNull FlatFileSection section) {
        final String path = "options." + identifier();

        if(!section.contains(path)) {
            return Optional.empty();
        }

        final boolean ignoreFilteringContext = section.get(path + ".ignore-filtering-context", false);
        final boolean ignoreCommandSetType = section.get(path + ".ignore-command-set-type", false);

        if(DebugHandler.isDebugCategoryEnabled(DebugCategory.OPTIONS)) {
            Commons.core().logger().debug(DebugCategory.OPTIONS, String.format(
                    "Parsing ActionPredicateOverride option in set '%s' with ignoreFilteringContext=%s," +
                            " ignoreCommandSetType=%s",
                    parentSet.identifier(),
                    ignoreFilteringContext,
                    ignoreCommandSetType
            ));
        }

        return Optional.of(new ActionPredicateOverrideOption(
                parentSet,
                ignoreFilteringContext,
                ignoreCommandSetType
        ));
    }

    public record ActionPredicateOverrideOption(
            @NotNull CommandSet parentSet,
            boolean ignoreFilteringContext,
            boolean ignoreCommandAccessStatus
    ) implements PostProcessOption {}

}
