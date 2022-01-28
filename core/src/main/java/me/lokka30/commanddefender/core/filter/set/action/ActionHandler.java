package me.lokka30.commanddefender.core.filter.set.action;

import de.leonhard.storage.sections.FlatFileSection;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public interface ActionHandler {

    @NotNull
    String getIdentifier();

    @NotNull
    Optional<Action> parse(final @NotNull FlatFileSection section);

}
