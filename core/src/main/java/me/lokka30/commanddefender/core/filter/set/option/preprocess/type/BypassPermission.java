package me.lokka30.commanddefender.core.filter.set.option.preprocess.type;

import de.leonhard.storage.sections.FlatFileSection;
import me.lokka30.commanddefender.core.filter.set.condition.Condition;
import me.lokka30.commanddefender.core.filter.set.option.OptionHandler;
import me.lokka30.commanddefender.core.filter.set.option.preprocess.PreProcessOption;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class BypassPermission implements OptionHandler {

    @Override
    public @NotNull String getIdentifier() {
        return "bypass-permission";
    }

    @Override
    public @NotNull Optional<Condition> parse(@NotNull FlatFileSection section) {
        //TODO
        return Optional.empty();
    }

    public record BypassPermissionOption(
            @NotNull String bypassPermission
    ) implements PreProcessOption {}
}
