package me.lokka30.commanddefender.core.filter.set;

import de.leonhard.storage.Yaml;
import me.lokka30.commanddefender.core.Commons;
import me.lokka30.commanddefender.core.debug.DebugCategory;
import me.lokka30.commanddefender.core.debug.DebugHandler;
import me.lokka30.commanddefender.core.filter.CommandAccessStatus;
import me.lokka30.commanddefender.core.filter.set.action.Action;
import me.lokka30.commanddefender.core.filter.set.condition.Condition;
import me.lokka30.commanddefender.core.filter.set.option.Option;
import me.lokka30.commanddefender.core.util.universal.UniversalPlayer;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.HashSet;

public record CommandSet(
        /* General */
        @NotNull String identifier,
        @NotNull CommandAccessStatus type,
        @NotNull HashSet<CommandSetPreset> presets,

        /* Conditions */
        @NotNull HashSet<Condition> conditions,
        double conditionsPercentageRequired,

        /* Actions */
        @NotNull HashSet<Action> actions,

        /* Options */
        @NotNull HashSet<Option> preProcessOptions,
        @NotNull HashSet<Option> postProcessOptions
) {

    // get if a command set wants to allow/deny a command, or if it doesn't care about the command.
    public CommandAccessStatus getAccessStatus(final UniversalPlayer player, final String[] args) {
        boolean debugLog = DebugHandler.isDebugCategoryEnabled(DebugCategory.COMMAND_FILTER_ACCESSIBILITY);

        if(debugLog) {
            Commons.core().logger().debug(DebugCategory.COMMAND_FILTER_ACCESSIBILITY, String.format(
                    "Checking access status for command set: %s, player: %s, args: %s",
                    identifier(),
                    player.name(),
                    Arrays.toString(args)
            ));
        }

        final Yaml advancedSettingsData = Commons.core().fileHandler().advancedSettings().data();

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
            if(
                    conditionsPercentageRequired() != 0.0 &&
                            ((double) conditionsMet / (double) totalConditions) >= conditionsPercentageRequired()
            ) {
                // if enough conditions are met, then return the type, e.g. `"`DENY`.
                if(debugLog) {
                    Commons.core().logger().debug(DebugCategory.COMMAND_FILTER_ACCESSIBILITY, String.format(
                            "%s of %s conditions were met (req %s); returning %s",
                            conditionsMet,
                            totalConditions,
                            conditionsPercentageRequired(),
                            type()
                    ));
                }
                return type();
            }
        }

        if(debugLog) {
            Commons.core().logger().debug(DebugCategory.COMMAND_FILTER_ACCESSIBILITY, String.format(
                    "Not enough conditions were met: requires &b%s&7 percent of &btrue&7" +
                            " conditions &8(&7total: &b%s&8)&7; but only &b%s&7 of &b%s&7 conditions are &btrue&7.",
                    conditionsPercentageRequired() * 100,
                    (conditionsPercentageRequired() * totalConditions),
                    conditionsMet,
                    totalConditions
            ));
        }

        // command set doesn't want to do anything with this command,
        // so return `UNKNOWN`.
        return CommandAccessStatus.UNKNOWN;
    }

}
