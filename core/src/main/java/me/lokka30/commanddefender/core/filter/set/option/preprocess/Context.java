package me.lokka30.commanddefender.core.filter.set.option.preprocess;

import de.leonhard.storage.sections.FlatFileSection;
import me.lokka30.commanddefender.core.Commons;
import me.lokka30.commanddefender.core.debug.DebugCategory;
import me.lokka30.commanddefender.core.debug.DebugHandler;
import me.lokka30.commanddefender.core.filter.FilterContextType;
import me.lokka30.commanddefender.core.filter.set.CommandSet;
import me.lokka30.commanddefender.core.filter.set.CommandSetPreset;
import me.lokka30.commanddefender.core.filter.set.option.Option;
import me.lokka30.commanddefender.core.filter.set.option.OptionHandler;
import me.lokka30.commanddefender.core.filter.set.option.ProcessingStage;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Optional;

public class Context implements OptionHandler {

    @Override
    public @NotNull String identifier() {
        return "context";
    }

    @Override
    public @NotNull Optional<Option> parse(@NotNull CommandSet parentSet, @NotNull FlatFileSection section) {

        final String path = "options." + identifier();

        final HashMap<FilterContextType, Boolean> typeMap = new HashMap<>();

        if(section.contains(path + ".command-execution")) {
            typeMap.put(FilterContextType.COMMAND_EXECUTION, section.getBoolean(path + ".command-execution"));
        } else {
            for(CommandSetPreset preset : parentSet.presets()) {
                if(preset.section().contains(path + ".command-execution")) {
                    typeMap.put(FilterContextType.COMMAND_EXECUTION, preset.section().getBoolean(path + ".command-execution"));
                    break;
                }
            }
        }

        if(section.contains(path + ".command-suggestion")) {
            typeMap.put(FilterContextType.COMMAND_SUGGESTION, section.getBoolean(path + ".command-suggestion"));
        } else {
            for(CommandSetPreset preset : parentSet.presets()) {
                if(preset.section().contains(path + ".command-suggestion")) {
                    typeMap.put(FilterContextType.COMMAND_SUGGESTION, preset.section().getBoolean(path + ".command-suggestion"));
                    break;
                }
            }
        }

       if(typeMap.isEmpty()) {
           return Optional.empty();
       } else {
           final HashSet<FilterContextType> types = new HashSet<>();
           for(FilterContextType type : typeMap.keySet()) {
               if(typeMap.get(type)) {
                   types.add(type);
               }
           }

           if(DebugHandler.isDebugCategoryEnabled(DebugCategory.OPTIONS)) {
               Commons.core().logger().debug(DebugCategory.OPTIONS, String.format(
                       "Parsed context option in command set %s with types %s",
                       parentSet.identifier(),
                       types
               ));
           }

           return Optional.of(new ContextOption(types.toArray(new FilterContextType[0])));
       }
    }

    public record ContextOption(
            @NotNull FilterContextType[] contextTypes
    ) implements Option {
        @Override
        public @NotNull ProcessingStage processingStage() {
            return ProcessingStage.PRE_PROCESS;
        }
    }

}
