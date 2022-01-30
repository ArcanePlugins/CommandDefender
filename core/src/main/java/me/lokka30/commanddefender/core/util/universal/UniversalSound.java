package me.lokka30.commanddefender.core.util.universal;

import org.jetbrains.annotations.NotNull;

public interface UniversalSound {

    @NotNull String identifier();

    double volume();

    double pitch();

    void play(final @NotNull UniversalPlayer player);

}
