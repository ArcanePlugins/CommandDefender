package me.lokka30.commanddefender.core;

import me.lokka30.commanddefender.core.log.Logger;
import org.jetbrains.annotations.NotNull;

public interface Core {

    @NotNull Logger logger();

    @NotNull String colorize(final @NotNull String msg);

}