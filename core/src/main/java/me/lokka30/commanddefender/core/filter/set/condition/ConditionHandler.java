package me.lokka30.commanddefender.core.filter.set.condition;

import de.leonhard.storage.sections.FlatFileSection;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public interface ConditionHandler {

    @NotNull
    String getIdentifier();

    @NotNull
    Optional<Condition> parse(final FlatFileSection section);
}
