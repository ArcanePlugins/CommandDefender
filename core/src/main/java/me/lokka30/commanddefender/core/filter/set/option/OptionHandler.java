package me.lokka30.commanddefender.core.filter.set.option;

import de.leonhard.storage.sections.FlatFileSection;
import me.lokka30.commanddefender.core.filter.set.condition.Condition;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public interface OptionHandler {

    @NotNull
    String getIdentifier();

    @NotNull
    Optional<Condition> parse(final @NotNull FlatFileSection section);

}
