package me.lokka30.commanddefender.core.filter.set.condition;

import de.leonhard.storage.sections.FlatFileSection;
import java.util.Optional;
import me.lokka30.commanddefender.core.filter.set.CommandSet;
import org.jetbrains.annotations.NotNull;

public interface ConditionHandler {

    @NotNull
    String identifier();

    @NotNull
    Optional<Condition> parse(final @NotNull CommandSet parentSet,
        final @NotNull FlatFileSection section);
}
