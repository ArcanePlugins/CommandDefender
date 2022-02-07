package me.lokka30.commanddefender.core.filter.set;

import de.leonhard.storage.sections.FlatFileSection;
import org.jetbrains.annotations.NotNull;

public record CommandSetPreset(
    @NotNull String identifier,
    @NotNull FlatFileSection section
) {

}