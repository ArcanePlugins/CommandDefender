package me.lokka30.commanddefender.core.filter.set.option.type;

import me.lokka30.commanddefender.core.filter.set.option.Option;
import org.jetbrains.annotations.NotNull;

public record SkipToOption(
        @NotNull String setToSkipTo
) implements Option {}