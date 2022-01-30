package me.lokka30.commanddefender.core.filter.set.option;

import de.leonhard.storage.sections.FlatFileSection;
import me.lokka30.commanddefender.core.filter.set.CommandSet;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public interface OptionHandler {

    @NotNull CommandSet parentSet();

    @NotNull String identifier();

    @NotNull
    Optional<Option> parse(final @NotNull CommandSet parentSet, final @NotNull FlatFileSection section);

}
