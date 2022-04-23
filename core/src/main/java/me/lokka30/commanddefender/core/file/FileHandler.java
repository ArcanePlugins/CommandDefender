package me.lokka30.commanddefender.core.file;

import java.util.Set;
import me.lokka30.commanddefender.core.Commons;
import me.lokka30.commanddefender.core.file.external.AdvancedSettings;
import me.lokka30.commanddefender.core.file.external.Messages;
import me.lokka30.commanddefender.core.file.external.Settings;
import me.lokka30.commanddefender.core.file.external.type.ExternalFile;
import org.jetbrains.annotations.NotNull;

public final class FileHandler {

    /* External Files */

    @NotNull
    public AdvancedSettings advancedSettings() {
        return advancedSettings;
    }

    private final AdvancedSettings advancedSettings = new AdvancedSettings();

    @NotNull
    public Messages messages() {
        return messages;
    }

    private final Messages messages = new Messages();

    @NotNull
    public Settings settings() {
        return settings;
    }

    private final Settings settings = new Settings();

    private final Set<ExternalFile> allExternalFiles = Set.of(
        advancedSettings,
        messages,
        settings
    );

    public void load(final boolean fromReload) {
        Commons.core().logger().info("Loading external files...");
        allExternalFiles.forEach(file -> file.load(fromReload));
    }
}
