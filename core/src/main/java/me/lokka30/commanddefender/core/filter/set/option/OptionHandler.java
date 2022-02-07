package me.lokka30.commanddefender.core.filter.set.option;

import de.leonhard.storage.sections.FlatFileSection;
import java.util.Optional;
import me.lokka30.commanddefender.core.filter.set.CommandSet;
import org.jetbrains.annotations.NotNull;

public interface OptionHandler {

    @NotNull
    String identifier();

    @NotNull
    Optional<Option> parse(final @NotNull CommandSet parentSet,
        final @NotNull FlatFileSection section);

}
