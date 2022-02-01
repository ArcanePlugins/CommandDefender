package me.lokka30.commanddefender.core.filter.set.condition.type;

import de.leonhard.storage.sections.FlatFileSection;
import me.lokka30.commanddefender.core.filter.set.CommandSet;
import me.lokka30.commanddefender.core.filter.set.condition.Condition;
import me.lokka30.commanddefender.core.filter.set.condition.ConditionHandler;
import me.lokka30.commanddefender.core.util.universal.UniversalPlayer;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Locale;
import java.util.Optional;

public class List implements ConditionHandler {

    @Override
    public @NotNull String identifier() {
        return "list";
    }

    @Override
    public @NotNull Optional<Condition> parse(final @NotNull CommandSet parentSet, final @NotNull FlatFileSection section) {
        //TODO parse from command set and presets
        return Optional.empty();
    }

    public record ListCondition(
            @NotNull MatchingMode matchingMode,
            boolean ignoreCase,
            @NotNull String[] contents,
            boolean includeAliases, //TODO implement
            boolean inverse
    ) implements Condition {

        // this is cached to improve performance.
        private static String[] adaptedContents = null;

        @Override
        public boolean appliesTo(@NotNull UniversalPlayer player, @NotNull String[] args) {

            // we don't want to modify the existing args or contents arrays
            // so we generate a copy of them.

            final String[] adaptedArgs = Arrays.copyOf(args, args.length);

            if(adaptedContents == null) {
                adaptedContents = Arrays.copyOf(contents(), contents().length);

                // adapt array for ignoreCase
                if(ignoreCase()) {
                    for(int i = 0; i < adaptedContents.length; i++) {
                        adaptedContents[i] = adaptedContents[i].toLowerCase(Locale.ROOT);
                    }
                }
            }

            // adapt array for ignoreCase
            if(ignoreCase()) {
                for(int i = 0; i < args.length; i++) {
                    adaptedArgs[i] = adaptedArgs[i].toLowerCase(Locale.ROOT);
                }
            }

            // adapt array for "*" -> "\*"
            for(int i = 0; i < adaptedArgs.length; i++) {
                if(adaptedArgs[i].equals("*")) {
                    adaptedArgs[i] = "\\*";
                }
            }

            contentsLoop:
            for(String content : adaptedContents) {
                final String[] cSplit = content.split(" ");
                final int maxIteration = Math.min(adaptedArgs.length, cSplit.length);

                for (int i = 0; i < maxIteration; i++) {
                    if (cSplit[i].equals("*")) {
                        return !inverse();
                    }

                    final boolean matchingModeCheckSuccess = switch(matchingMode()) {
                        case EQUALS -> cSplit[i].equals(adaptedArgs[i]);
                        case CONTAINS -> cSplit[i].contains(adaptedArgs[i]);
                        case STARTS_WITH -> cSplit[i].startsWith(adaptedArgs[i]);
                    };

                    if (matchingModeCheckSuccess) {
                        if ((i + 1) == maxIteration) {
                            return !inverse();
                        }
                    } else {
                        // don't waste processing power. skip to the next content
                        continue contentsLoop;
                    }
                }
            }
            return inverse();
        }

        public enum MatchingMode {
            EQUALS,
            STARTS_WITH,
            CONTAINS
        }
    }
}