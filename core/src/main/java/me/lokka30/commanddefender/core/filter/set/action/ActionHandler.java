package me.lokka30.commanddefender.core.filter.set.action;

import de.leonhard.storage.sections.FlatFileSection;
import java.util.Optional;
import me.lokka30.commanddefender.core.filter.set.CommandSet;
import org.jetbrains.annotations.NotNull;

public interface ActionHandler {

    @NotNull
    String identifier();

    @NotNull
    Optional<Action> parse(final @NotNull CommandSet parentSet,
        final @NotNull FlatFileSection section);

}
