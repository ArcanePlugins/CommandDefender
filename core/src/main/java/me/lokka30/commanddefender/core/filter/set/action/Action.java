package me.lokka30.commanddefender.core.filter.set.action;

import me.lokka30.commanddefender.core.util.universal.UniversalPlayer;
import org.jetbrains.annotations.NotNull;

public interface Action {

    void run(final @NotNull UniversalPlayer player);

}
