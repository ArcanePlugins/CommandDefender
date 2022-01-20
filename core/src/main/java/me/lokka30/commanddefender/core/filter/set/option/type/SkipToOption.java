package me.lokka30.commanddefender.core.filter.set.option.type;

import me.lokka30.commanddefender.core.filter.set.option.Option;
import org.jetbrains.annotations.NotNull;

public class SkipToOption implements Option {

    public SkipToOption(
            final @NotNull String setToSkipTo
    ) {
        this.setToSkipTo = setToSkipTo;
    }

    private final String setToSkipTo;
    public String setToSkipTo() { return setToSkipTo; }
}
