package me.lokka30.commanddefender.core.filter.set.condition.type;

import de.leonhard.storage.Yaml;
import de.leonhard.storage.sections.FlatFileSection;
import me.lokka30.commanddefender.core.Commons;
import me.lokka30.commanddefender.core.filter.set.CommandSet;
import me.lokka30.commanddefender.core.filter.set.CommandSetPreset;
import me.lokka30.commanddefender.core.filter.set.condition.Condition;
import me.lokka30.commanddefender.core.filter.set.condition.ConditionHandler;
import me.lokka30.commanddefender.core.util.universal.UniversalPlayer;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.*;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class RegexList implements ConditionHandler {

    @Override
    public @NotNull String identifier() {
        return "regex-list";
    }

    @Override
    public @NotNull Optional<Condition> parse(final @NotNull CommandSet parentSet, final @NotNull FlatFileSection section) {
        final String path = "conditions." + identifier();

        boolean inverse = false;
        List<String> contents = Collections.emptyList();
        String patternFlagStr = null;

        final String contentsPath = path + ".contents";
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
        if(contents.isEmpty()) { return Optional.empty(); }

        final String inversePath = path + ".inverse";
        if(section.contains(inversePath)) {
            inverse = section.get(inversePath, false);
        } else {
            for(CommandSetPreset preset : parentSet.presets()) {
                if(preset.section().contains(inversePath)) {
                    inverse = preset.section().get(inversePath, false);
                    break;
                }
            }
        }

        final String patternFlagPath = path + ".pattern-flag";
        if(section.contains(patternFlagPath)) {
            patternFlagStr = section.getString(patternFlagPath);
        } else {
            for(CommandSetPreset preset : parentSet.presets()) {
                if(preset.section().contains(patternFlagPath)) {
                    patternFlagStr = preset.section().getString(patternFlagPath);
                    break;
                }
            }
        }
        int patternFlag = -1;
        if(patternFlagStr != null && !patternFlagStr.isEmpty()) {
            patternFlag = switch(patternFlagStr.toUpperCase(Locale.ROOT)) {
                case "UNIX_LINES" -> Pattern.UNIX_LINES;
                case "CASE_INSENSITIVE" -> Pattern.CASE_INSENSITIVE;
                case "COMMENTS" -> Pattern.COMMENTS;
                case "MULTILINE" -> Pattern.MULTILINE;
                case "LITERAL" -> Pattern.LITERAL;
                case "DOTALL" -> Pattern.DOTALL;
                case "UNICODE_CASE" -> Pattern.UNICODE_CASE;
                case "CANON_EQ" -> Pattern.CANON_EQ;
                case "UNICODE_CHARACTER_CLASS" -> Pattern.UNICODE_CHARACTER_CLASS;
                default -> throw new IllegalStateException("Unexpected value: " + patternFlagStr);
            };
        }

        final HashSet<Pattern> patterns = new HashSet<>();
        for(String regex : contents) {
            try {
                if(patternFlag == -1) {
                    patterns.add(Pattern.compile(regex));
                } else {
                    patterns.add(Pattern.compile(regex, patternFlag));
                }
            } catch(PatternSyntaxException ex) {
                Commons.core().logger().error("Invalid regex pattern syntax: '&b" + regex + "&7'. Fix this ASAP.");
            }
        }

        return Optional.of(new RegexListCondition(
                patterns.toArray(new Pattern[0]),
                inverse
        ));
    }

    public record RegexListCondition(
            @NotNull Pattern[] patterns,
            boolean inverse
    ) implements Condition {

        @Override
        public boolean appliesTo(@NotNull UniversalPlayer player, @NotNull String[] args) {
            String joinedArgs = String.join(" ", args);

            // adapt array for 'use starting slash'
            final Yaml advancedSettingsData = Commons.core().fileHandler().advancedSettings().data();
            if(!advancedSettingsData.get("commands-configured-with-starting-slash", true)) {
                assert joinedArgs.startsWith("/");
                joinedArgs = joinedArgs.substring(1);
            }

            for(Pattern pattern : patterns()) {
                if(pattern.matcher(joinedArgs).find()) {
                    return !inverse();
                }
            }

            return inverse();
        }

    }

}