package me.lokka30.commanddefender.core.filter.set.option.preprocess.type;

import de.leonhard.storage.sections.FlatFileSection;
import me.lokka30.commanddefender.core.filter.FilterContextType;
import me.lokka30.commanddefender.core.filter.set.CommandSet;
import me.lokka30.commanddefender.core.filter.set.option.Option;
import me.lokka30.commanddefender.core.filter.set.option.OptionHandler;
import me.lokka30.commanddefender.core.filter.set.option.preprocess.PreProcessOption;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class FilterContext implements OptionHandler {

    @Override public @NotNull String identifier() {
        return "filter";
    }

    @Override
    public @NotNull Optional<Option> parse(@NotNull CommandSet parentSet, @NotNull FlatFileSection section) {
        //TODO
        return Optional.empty();
    }

    public record FilterContextOption(
            @NotNull FilterContextType[] contextTypes
    ) implements PreProcessOption {}
}
