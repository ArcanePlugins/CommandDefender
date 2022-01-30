package me.lokka30.commanddefender.core.filter.set.condition.type;

import de.leonhard.storage.sections.FlatFileSection;
import me.lokka30.commanddefender.core.filter.set.CommandSet;
import me.lokka30.commanddefender.core.filter.set.condition.Condition;
import me.lokka30.commanddefender.core.filter.set.condition.ConditionHandler;
import me.lokka30.commanddefender.core.util.universal.UniversalPlayer;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class HasColonInFirstArg implements ConditionHandler {

    CommandSet parentSet;
    @Override
    public @NotNull CommandSet parentSet() {
        return parentSet;
    }

    @Override
    public @NotNull String identifier() {
        return "has-colon-in-first-arg";
    }

    @Override
    public @NotNull Optional<Condition> parse(final @NotNull CommandSet parentSet, final @NotNull FlatFileSection section) {
        this.parentSet = parentSet;
        //TODO
        return Optional.empty();
    }

    public record HasColonInFirstArgCondition(
            boolean inverse
    ) implements Condition {
        @Override
        public boolean appliesTo(@NotNull UniversalPlayer player, @NotNull String[] args) {
            return args[0].contains(":") == !inverse();
        }
    }


}
