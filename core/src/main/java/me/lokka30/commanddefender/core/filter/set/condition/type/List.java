package me.lokka30.commanddefender.core.filter.set.condition.type;

import de.leonhard.storage.Yaml;
import de.leonhard.storage.sections.FlatFileSection;
import me.lokka30.commanddefender.core.Commons;
import me.lokka30.commanddefender.core.debug.DebugCategory;
import me.lokka30.commanddefender.core.debug.DebugHandler;
import me.lokka30.commanddefender.core.filter.set.CommandSet;
import me.lokka30.commanddefender.core.filter.set.CommandSetPreset;
import me.lokka30.commanddefender.core.filter.set.condition.Condition;
import me.lokka30.commanddefender.core.filter.set.condition.ConditionHandler;
import me.lokka30.commanddefender.core.util.universal.UniversalPlayer;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class List implements ConditionHandler {

    @Override
    public @NotNull String identifier() {
        return "list";
    }

    @Override
    public @NotNull Optional<Condition> parse(final @NotNull CommandSet parentSet, final @NotNull FlatFileSection section) {

        java.util.List<String> contents = null;
        final String contentsPath = "conditions." + identifier() + ".contents";
        if(section.contains(contentsPath)) {
            contents = section.getStringList(contentsPath);
        } else {
            for(CommandSetPreset preset : parentSet.presets()) {
                if(preset.section().contains(contentsPath)) {
                    contents = preset.section().getStringList(contentsPath);
                    break;
                }
            }
        }
        if(contents == null) { return Optional.empty(); }

        ListCondition.MatchingMode matchingMode = ListCondition.MatchingMode.EQUALS;
        final String matchingModePath = "conditions." + identifier() + ".matching-mode";
        if(section.contains(matchingModePath)) {
            matchingMode = ListCondition.MatchingMode.valueOf(section.getString(matchingModePath));
        } else {
            for(CommandSetPreset preset : parentSet.presets()) {
                if(preset.section().contains(matchingModePath)) {
                    matchingMode = ListCondition.MatchingMode.valueOf(preset.section().getString(matchingModePath));
                    break;
                }
            }
        }

        boolean inverse = false;
        final String inversePath = "conditions." + identifier() + ".inverse";
        if(section.contains(inversePath)) {
            inverse = section.getBoolean(inversePath);
        } else {
            for(CommandSetPreset preset : parentSet.presets()) {
                if(preset.section().contains(inversePath)) {
                    inverse = preset.section().getBoolean(inversePath);
                    break;
                }
            }
        }

        boolean ignoreCase = true;
        final String ignoreCasePath = "conditions." + identifier() + ".ignore-case";
        if(section.contains(ignoreCasePath)) {
            ignoreCase = section.getBoolean(ignoreCasePath);
        } else {
            for(CommandSetPreset preset : parentSet.presets()) {
                if(preset.section().contains(ignoreCasePath)) {
                    ignoreCase = preset.section().getBoolean(ignoreCasePath);
                    break;
                }
            }
        }

        boolean includeAliases = false;
        final String includeAliasesPath = "conditions." + identifier() + ".include-aliases";
        if(section.contains(includeAliasesPath)) {
            includeAliases = section.getBoolean(includeAliasesPath);
        } else {
            for(CommandSetPreset preset : parentSet.presets()) {
                if(preset.section().contains(includeAliasesPath)) {
                    includeAliases = preset.section().getBoolean(includeAliasesPath);
                    break;
                }
            }
        }

        if(DebugHandler.isDebugCategoryEnabled(DebugCategory.CONDITIONS)) {
            Commons.core().logger().debug(DebugCategory.CONDITIONS, String.format(
                    "Parsed list condition in command set %s with matchingMode=%s, " +
                            "ignoreCase=%s, contents=%s, includeAliases=%s, inverse=%s",
                    parentSet.identifier(),
                    matchingMode,
                    ignoreCase,
                    contents,
                    includeAliases,
                    inverse
            ));
        }

        return Optional.of(new ListCondition(
                matchingMode,
                ignoreCase,
                contents.toArray(new String[0]),
                includeAliases,
                inverse
        ));
    }

    public record ListCondition(
            @NotNull MatchingMode matchingMode,
            boolean ignoreCase,
            @NotNull String[] contents,
            boolean includeAliases,
            boolean inverse
    ) implements Condition {

        // this is cached to improve performance.
        private static final HashMap<ListCondition, String[]> adaptedContentsMap = new HashMap<>();

        @Override
        public boolean appliesTo(@NotNull UniversalPlayer player, @NotNull String[] args) {

            final boolean debugLog = DebugHandler.isDebugCategoryEnabled(DebugCategory.CONDITIONS);

            // we don't want to modify the existing args or contents arrays
            // so we generate a copy of them.

            String[] adaptedContents = adaptedContentsMap.get(this);
            final String[] adaptedArgs = Arrays.copyOf(args, args.length);

            if(adaptedContents == null) {
                adaptedContents = Arrays.copyOf(contents(), contents().length);

                // adapt array for ignoreCase
                if(ignoreCase()) {
                    for(int i = 0; i < adaptedContents.length; i++) {
                        adaptedContents[i] = adaptedContents[i].toLowerCase(Locale.ROOT);
                    }
                }

                adaptedContentsMap.put(this, adaptedContents);
            }
            if(debugLog) {
                Commons.core().logger().debug(DebugCategory.CONDITIONS, String.format(
                        "List contents after adaption is: &b%s&7.",
                        String.join("&7, &b", adaptedContents)
                ));
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

            // adapt array for 'use starting slash'
            final Yaml advancedSettingsData = Commons.core().fileHandler().advancedSettings().data();
            if(!advancedSettingsData.get("commands-configured-with-starting-slash", true)) {
                assert (adaptedArgs[0].startsWith("/"));
                adaptedArgs[0] = adaptedArgs[0].substring(1);
            }

            if(debugLog) {
                Commons.core().logger().debug(DebugCategory.CONDITIONS, String.format(
                        "List args array after adaption is: &b%s&7.",
                        String.join("&7, &b", adaptedArgs)
                ));
            }

            final Set<String> aliases = Commons.core().aliasesOfCommand(adaptedArgs[0].substring(1));
            if(debugLog) {
                Commons.core().logger().debug(DebugCategory.CONDITIONS, String.format(
                        "Aliases for command &b%s&7: &b/%s&7.",
                        adaptedArgs[0],
                        String.join("&7, &b/", aliases)
                ));
            }

            contentsLoop:
            for(String content : adaptedContents) {
                final String[] cSplit = content.split(" ");
                final int maxIteration = Math.min(adaptedArgs.length, cSplit.length);

                for (int i = 0; i < maxIteration; i++) {
                    if(
                            // if includeAliases, then check if the alias is also listed
                            // only run this on the first index (i.e. base label)
                            // substring(1) is used to remove the starting slash
                            (i == 0 && includeAliases() && aliases.contains(adaptedArgs[0].substring(1))) ||

                            // if /* is used in the list then it means to detect all commands
                            (i == 0 && cSplit[i].equals("/*")) ||

                            cSplit[i].equals("*") ||

                            switch(matchingMode()) {
                                case EQUALS -> cSplit[i].equals(adaptedArgs[i]);
                                case CONTAINS -> cSplit[i].contains(adaptedArgs[i]);
                                case STARTS_WITH -> cSplit[i].startsWith(adaptedArgs[i]);
                            }
                    ){
                        if ((i + 1) == cSplit.length) {
                            return !inverse();
                        }
                        // last arg not reached,
                    } else {
                        // don't waste processing power by checking other args. skip to the next content.
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