package me.lokka30.commanddefender.core.filter.set;

import me.lokka30.commanddefender.core.filter.CommandAccessStatus;
import me.lokka30.commanddefender.core.filter.set.action.Action;
import me.lokka30.commanddefender.core.filter.set.condition.Condition;
import me.lokka30.commanddefender.core.filter.set.option.Option;
import me.lokka30.commanddefender.core.player.UniversalPlayer;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;

public class CommandSet {

    // Update your Java so I can use records!!

    public CommandSet(
            final @NotNull String identifier,
            final @NotNull CommandAccessStatus type,
            final @NotNull HashSet<Condition> conditions,
            final @NotNull HashSet<Action> actions,
            final @NotNull HashSet<Option> options,
            final double conditionsPercentageRequired
    ) {
        this.identifier = identifier;
        this.type = type;
        this.conditions = conditions;
        this.actions = actions;
        this.options = options;
        this.conditionsPercentageRequired = conditionsPercentageRequired;
    }

    public String getIdentifier() { return identifier; }
    private final String identifier;

    public CommandAccessStatus getType() { return type; }
    private final CommandAccessStatus type;

    public HashSet<Condition> getConditions() { return conditions; }
    private final HashSet<Condition> conditions;

    public HashSet<Action> getActions() { return actions; }
    private final HashSet<Action> actions;

    public HashSet<Option> getOptions() { return options; }
    private final HashSet<Option> options;

    public double getConditionsPercentageRequired() { return conditionsPercentageRequired; }
    private final double conditionsPercentageRequired;

    // get if a command set wants to allow/deny a command, or if it doesn't care about the command.
    public CommandAccessStatus getAccessStatus(final UniversalPlayer player, final String[] args) {
        int conditionsMet = 0;
        int totalConditions = getConditions().size();

        // loop thru conditions
        for(Condition condition : getConditions()) {
            if(condition.appliesTo(player, args)) {
                // 0.0 = only one condition is required.
                if(getConditionsPercentageRequired() == 0.0) { return getType(); }

                // increment conditions met
                conditionsMet++;
            }

            // check if % of conditions met / total conditions matches requirement
            if(((double) conditionsMet / (double) totalConditions) >= getConditionsPercentageRequired()) {
                return getType();
            }
        }

        // command set doesn't want to do anything with this command
        return CommandAccessStatus.UNKNOWN;
    }
}
