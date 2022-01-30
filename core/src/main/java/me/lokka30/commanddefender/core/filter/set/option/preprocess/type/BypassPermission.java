package me.lokka30.commanddefender.core.filter.set.option.preprocess.type;

import de.leonhard.storage.sections.FlatFileSection;
import me.lokka30.commanddefender.core.filter.set.CommandSet;
import me.lokka30.commanddefender.core.filter.set.option.Option;
import me.lokka30.commanddefender.core.filter.set.option.OptionHandler;
import me.lokka30.commanddefender.core.filter.set.option.preprocess.PreProcessOption;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class BypassPermission implements OptionHandler {

    CommandSet parentSet;
    @Override
    public @NotNull CommandSet parentSet() {
        return parentSet;
    }

    @Override
    public @NotNull String identifier() {
        return "bypass-permission";
    }

    @Override
    public @NotNull Optional<Option> parse(final @NotNull CommandSet parentSet, final @NotNull FlatFileSection section) {
        this.parentSet = parentSet;
        //TODO
        return Optional.empty();
    }

    public record BypassPermissionOption(
            @NotNull String bypassPermission
    ) implements PreProcessOption {}
}
