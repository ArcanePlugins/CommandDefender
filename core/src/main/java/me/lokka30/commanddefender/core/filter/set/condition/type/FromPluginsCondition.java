package me.lokka30.commanddefender.core.filter.set.condition.type;

import me.lokka30.commanddefender.core.filter.set.condition.Condition;
import me.lokka30.commanddefender.core.player.UniversalPlayer;
import org.jetbrains.annotations.NotNull;

public class FromPluginsCondition implements Condition {

    public FromPluginsCondition(
            final boolean inverse
    ) {
        this.inverse = inverse;
    }

    private final boolean inverse;

    @Override
    public boolean appliesTo(@NotNull UniversalPlayer player, @NotNull String[] args) {
        return false != inverse; //TODO need to figure out how to implement this.
    }

}
