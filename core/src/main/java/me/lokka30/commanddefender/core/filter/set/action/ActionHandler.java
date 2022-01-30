package me.lokka30.commanddefender.core.filter.set.action;

import de.leonhard.storage.sections.FlatFileSection;
import me.lokka30.commanddefender.core.filter.set.CommandSet;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public interface ActionHandler {

    @NotNull
    CommandSet parentSet();

    @NotNull
    String identifier();

    @NotNull
    Optional<Action> parse(final @NotNull CommandSet parentSet, final @NotNull FlatFileSection section);

}
