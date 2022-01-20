package me.lokka30.commanddefender.core.filter.set.action;

import me.lokka30.commanddefender.core.player.UniversalPlayer;
import org.jetbrains.annotations.NotNull;

public interface Action {

    void run(final @NotNull UniversalPlayer player);

}
