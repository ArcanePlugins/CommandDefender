package me.lokka30.commanddefender.core.filter.set.option.postprocess.type;

import de.leonhard.storage.sections.FlatFileSection;
import me.lokka30.commanddefender.core.filter.set.condition.Condition;
import me.lokka30.commanddefender.core.filter.set.option.OptionHandler;
import me.lokka30.commanddefender.core.filter.set.option.postprocess.PostProcessOption;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class StopProcessing implements OptionHandler {

    @Override
    public @NotNull String getIdentifier() {
        return "stop-processing";
    }

    @Override
    public @NotNull Optional<Condition> parse(@NotNull FlatFileSection section) {
        //TODO
        return Optional.empty();
    }

    public record StopProcessingOption(
            boolean stopProcessing
    ) implements PostProcessOption {}
}