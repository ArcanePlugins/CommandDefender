package me.lokka30.commanddefender.core.filter.set;

import me.lokka30.commanddefender.core.filter.CommandAccessStatus;
import me.lokka30.commanddefender.core.filter.set.action.Action;
import me.lokka30.commanddefender.core.filter.set.condition.Condition;
import me.lokka30.commanddefender.core.filter.set.option.Option;
import me.lokka30.commanddefender.core.player.UniversalPlayer;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;

public class CommandSet {

    public CommandSet(
            final @NotNull CommandAccessStatus type,
            final @NotNull HashSet<Condition> conditions,
            final @NotNull HashSet<Action> actions,
            final @NotNull HashSet<Option> options,
            final double conditionsPercentageRequired
    ) {
        this.type = type;
        this.conditions = conditions;
        this.actions = actions;
        this.options = options;
        this.conditionsPercentageRequired = conditionsPercentageRequired;
    }

    private final CommandAccessStatus type;
    private final HashSet<Condition> conditions;
    private final HashSet<Action> actions;
    private final HashSet<Option> options;
    private final double conditionsPercentageRequired;

    public CommandAccessStatus accessStatus(final UniversalPlayer player, final String[] args) {
        //TODO
        return CommandAccessStatus.UNKNOWN;
    }
}
