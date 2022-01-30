package me.lokka30.commanddefender.core.filter.set.condition;

import me.lokka30.commanddefender.core.util.universal.UniversalPlayer;
import org.jetbrains.annotations.NotNull;

public interface Condition {

    boolean appliesTo(final @NotNull UniversalPlayer player, final @NotNull String[] args);

}
