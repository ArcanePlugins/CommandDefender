package me.lokka30.commanddefender.core.debug;

import me.lokka30.commanddefender.core.Core;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;

public final class DebugHandler {

    private final Core core;
    public DebugHandler(final @NotNull Core core) { this.core = core; }

    private final EnumSet<DebugCategory> enabledDebugCategories = EnumSet.noneOf(DebugCategory.class);
    public @NotNull EnumSet<DebugCategory> getEnabledDebugCategories() { return enabledDebugCategories; }

    public void load() {
        final ArrayList<String> enabledDebugCategoriesStr = (ArrayList<String>) Arrays.asList("TO", "DO"); //TODO

        for(String str : enabledDebugCategoriesStr) {
            final DebugCategory category;
            try {
                category = DebugCategory.valueOf(str);
            } catch(IllegalArgumentException ex) {
                core.getLogger().error("Invalid debug category '&b" + str + "&7'!");
                continue;
            }

            if(enabledDebugCategories.contains(category)) {
                core.getLogger().error("Debug category '&b" + str + "&7' is already enabled! Please remove any duplicates.");
                continue;
            }

            enabledDebugCategories.add(category);
        }
    }
}
