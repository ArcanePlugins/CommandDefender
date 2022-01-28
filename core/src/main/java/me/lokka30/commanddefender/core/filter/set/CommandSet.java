package me.lokka30.commanddefender.core.filter.set;

import me.lokka30.commanddefender.core.filter.CommandAccessStatus;
import me.lokka30.commanddefender.core.filter.set.action.Action;
import me.lokka30.commanddefender.core.filter.set.condition.Condition;
import me.lokka30.commanddefender.core.filter.set.option.postprocess.PostProcessOption;
import me.lokka30.commanddefender.core.filter.set.option.preprocess.PreProcessOption;
import me.lokka30.commanddefender.core.player.UniversalPlayer;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;

public record CommandSet(
        @NotNull String identifier,
        @NotNull CommandAccessStatus type,
        @NotNull HashSet<Condition> conditions,
        double conditionsPercentageRequired,
        @NotNull HashSet<Action> actions,
        @NotNull HashSet<PreProcessOption> preProcessOptions,
        @NotNull HashSet<PostProcessOption> postProcessOptions
) {

    // get if a command set wants to allow/deny a command, or if it doesn't care about the command.
    public CommandAccessStatus getAccessStatus(final UniversalPlayer player, final String[] args) {
        int conditionsMet = 0;
        int totalConditions = conditions().size();

        // loop thru conditions
        for(Condition condition : conditions()) {
            if(condition.appliesTo(player, args)) {
                // 0.0 = only one condition is required.
                if(conditionsPercentageRequired() == 0.0) { return type(); }

                // increment conditions met
                conditionsMet++;
            }

            // check if % of conditions met / total conditions matches requirement
            if(((double) conditionsMet / (double) totalConditions) >= conditionsPercentageRequired()) {
                return type();
            }
        }

        // command set doesn't want to do anything with this command
        return CommandAccessStatus.UNKNOWN;
    }
}
