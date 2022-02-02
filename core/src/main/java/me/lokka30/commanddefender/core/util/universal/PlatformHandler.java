package me.lokka30.commanddefender.core.util.universal;

import org.jetbrains.annotations.NotNull;

public interface PlatformHandler {

    @NotNull UniversalSound buildPlatformSpecificSound(
            final @NotNull String identifier,
            final double volume,
            final double pitch
    );

}
