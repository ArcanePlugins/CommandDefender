package me.lokka30.commanddefender.core.filter.set.option.preprocess.type;

import de.leonhard.storage.sections.FlatFileSection;
import me.lokka30.commanddefender.core.Commons;
import me.lokka30.commanddefender.core.debug.DebugCategory;
import me.lokka30.commanddefender.core.debug.DebugHandler;
import me.lokka30.commanddefender.core.filter.set.CommandSet;
import me.lokka30.commanddefender.core.filter.set.CommandSetPreset;
import me.lokka30.commanddefender.core.filter.set.option.Option;
import me.lokka30.commanddefender.core.filter.set.option.OptionHandler;
import me.lokka30.commanddefender.core.filter.set.option.preprocess.PreProcessOption;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class BypassPermission implements OptionHandler {

    @Override
    public @NotNull String identifier() {
        return "bypass-permission";
    }

    @Override
    public @NotNull Optional<Option> parse(final @NotNull CommandSet parentSet, final @NotNull FlatFileSection section) {
        String bypassPermission = null;

        final String path = "options." + identifier();
        if(section.contains(path)) {
            bypassPermission = section.getString(path);
        } else {
            for(CommandSetPreset preset : parentSet.presets()) {
                if(preset.section().contains(path)) {
                    bypassPermission = preset.section().getString(path);
                    break;
                }
            }
        }

        if(bypassPermission == null) {
            return Optional.empty();
        } else {
            // START DEBUG LOG
            if(DebugHandler.isDebugCategoryEnabled(DebugCategory.CONDITIONS)) {
                Commons.core().logger().debug(DebugCategory.CONDITIONS, String.format(
                        "Parsed BypassPermission option in command set %s with perm = %s",
                        parentSet.identifier(),
                        bypassPermission
                ));
            }
            // END DEBUG LOG
            return Optional.of(new BypassPermissionOption(bypassPermission));
        }
    }

    public record BypassPermissionOption(
            @NotNull String bypassPermission
    ) implements PreProcessOption {}

}
