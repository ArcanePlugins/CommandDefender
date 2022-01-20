package me.lokka30.commanddefender.core.filter.set.condition.type;

import me.lokka30.commanddefender.core.filter.set.condition.Condition;
import me.lokka30.commanddefender.core.player.UniversalPlayer;
import org.jetbrains.annotations.NotNull;

public class ListCondition implements Condition {

    public ListCondition(
            final @NotNull MatchingMode matchingMode,
            final boolean ignoreCase,
            final String[] contents,
            final boolean inverse
    ) {
        this.matchingMode = matchingMode;
        this.ignoreCase = ignoreCase;
        this.contents = contents;
        this.inverse = inverse;
    }

    private final MatchingMode matchingMode;
    private final boolean ignoreCase;
    private final String[] contents;
    private final boolean inverse;

    @Override
    public boolean appliesTo(@NotNull UniversalPlayer player, @NotNull String[] args) {
        switch(matchingMode) {
            case EQUALS:
                return false != inverse; //todo
            case CONTAINS:
                return false != inverse; //todo
            case STARTS_WITH:
                return false != inverse; //todo
            default:
                throw new IllegalStateException("Unexpected value: " + matchingMode);
        }
    }

    enum MatchingMode {
        EQUALS,
        STARTS_WITH,
        CONTAINS
    }
}
