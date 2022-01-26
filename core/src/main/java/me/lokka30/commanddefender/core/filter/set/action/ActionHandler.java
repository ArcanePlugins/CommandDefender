package me.lokka30.commanddefender.core.filter.set.action;

import de.leonhard.storage.sections.FlatFileSection;
import me.lokka30.commanddefender.core.filter.set.condition.Condition;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public interface ActionHandler {

    @NotNull
    String getIdentifier();

    @NotNull
    Optional<Condition> parse(final @NotNull FlatFileSection section);

}
