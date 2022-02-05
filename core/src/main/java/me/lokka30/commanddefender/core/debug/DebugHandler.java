package me.lokka30.commanddefender.core.debug;

import me.lokka30.commanddefender.core.Commons;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Locale;

public class DebugHandler {

    private DebugCategory[] enabledDebugCategories = null;
    public DebugCategory[] getEnabledDebugCategories() { return enabledDebugCategories; }

    public static boolean isDebugCategoryEnabled(final @NotNull DebugCategory debugCategory) {
        for(DebugCategory enabledCategory : Commons.core().debugHandler().getEnabledDebugCategories()) {
            if(enabledCategory.equals(debugCategory)) {
                return true;
            }
        }
        return false;
    }

    public void load() {
        final HashSet<DebugCategory> debugCategoriesToEnable = new HashSet<>();

        Commons.core().fileHandler().advancedSettings().data().getStringList("debug").forEach(categoryStr -> {
            try {
                debugCategoriesToEnable.add(DebugCategory.valueOf(categoryStr.toUpperCase(Locale.ROOT)));
            } catch(IllegalArgumentException ex) {
                Commons.core().logger().error("Invalid debug category '&b" + categoryStr + "&7'!");
            }
        });

        enabledDebugCategories = debugCategoriesToEnable.toArray(new DebugCategory[0]);

        if(debugCategoriesToEnable.size() != 0) {
            Commons.core().logger().warning("You are spying on &b" + debugCategoriesToEnable.size() + "&7 debug categories. This is configured at '&bdebug&7' in &badvanced-settings.yml&7.");
        }
    }
}
