package me.lokka30.commanddefender.core.filter.set.option.postprocess.type;

import de.leonhard.storage.sections.FlatFileSection;
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
        if(!section.contains(identifier())) {
            return Optional.empty();
        }

        return Optional.of(new ActionPredicateOverrideOption(
                parentSet,
                section.get(identifier() + ".ignore-filtering-context", false),
                section.get(identifier() + ".ignore-command-set-type", false)
        ));
    }

    public record ActionPredicateOverrideOption(
            @NotNull CommandSet parentSet,
            boolean ignoreFilteringContext,
            boolean ignoreCommandAccessStatus
    ) implements PostProcessOption {}
}
