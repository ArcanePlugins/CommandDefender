package me.lokka30.commanddefender.core.filter.set.condition.type;

import de.leonhard.storage.sections.FlatFileSection;
import me.lokka30.commanddefender.core.filter.set.condition.Condition;
import me.lokka30.commanddefender.core.filter.set.condition.ConditionHandler;
import me.lokka30.commanddefender.core.player.UniversalPlayer;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class RegexList implements ConditionHandler {

    @Override
    public @NotNull String getIdentifier() {
        return "regex-list";
    }

    @Override
    public @NotNull Optional<Condition> parse(FlatFileSection section) {
        //TODO
        return Optional.empty();
    }

    public record RegexListCondition(
            @NotNull String[] contents,
            boolean inverse
    ) implements Condition {

        @Override
        public boolean appliesTo(@NotNull UniversalPlayer player, @NotNull String[] args) {
            //TODO
            return false != inverse();
        }

    }
}