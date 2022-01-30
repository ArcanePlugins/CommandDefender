package me.lokka30.commanddefender.core.filter.set.condition;

import de.leonhard.storage.sections.FlatFileSection;
import me.lokka30.commanddefender.core.filter.set.CommandSet;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public interface ConditionHandler {

    @NotNull
    CommandSet parentSet();

    @NotNull
    String identifier();

    @NotNull
    Optional<Condition> parse(final @NotNull CommandSet parentSet, final @NotNull FlatFileSection section);
}
