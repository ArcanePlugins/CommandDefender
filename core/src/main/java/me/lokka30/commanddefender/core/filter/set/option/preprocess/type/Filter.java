package me.lokka30.commanddefender.core.filter.set.option.preprocess.type;

import de.leonhard.storage.sections.FlatFileSection;
import me.lokka30.commanddefender.core.filter.set.CommandSet;
import me.lokka30.commanddefender.core.filter.set.option.Option;
import me.lokka30.commanddefender.core.filter.set.option.OptionHandler;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class Filter implements OptionHandler {

    private CommandSet parentSet;
    @Override
    public @NotNull CommandSet parentSet() {
        return parentSet;
    }

    @Override
    public @NotNull String identifier() {
        return "filter";
    }

    @Override
    public @NotNull Optional<Option> parse(@NotNull CommandSet parentSet, @NotNull FlatFileSection section) {
        this.parentSet = parentSet;
        //TODO
        return Optional.empty();
    }
}
