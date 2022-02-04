package me.lokka30.commanddefender.core.file;

import me.lokka30.commanddefender.core.Commons;
import me.lokka30.commanddefender.core.file.external.AdvancedSettings;
import me.lokka30.commanddefender.core.file.external.Messages;
import me.lokka30.commanddefender.core.file.external.Settings;
import me.lokka30.commanddefender.core.file.external.misc.License;
import me.lokka30.commanddefender.core.file.external.type.ExternalFile;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public final class FileHandler {

    /* External Files */

    // ... misc ...

    @NotNull
    public License license() { return license; }
    private final License license = new License();

    // ... root ...

    @NotNull
    public AdvancedSettings advancedSettings() { return advancedSettings; }
    private final AdvancedSettings advancedSettings = new AdvancedSettings();

    @NotNull
    public Messages messages() { return messages; }
    private final Messages messages = new Messages();

    @NotNull
    public Settings settings() { return settings; }
    private final Settings settings = new Settings();

    private final Set<ExternalFile> allExternalFiles = Set.of(
        // misc
        license,

        // root
        advancedSettings,
        messages,
        settings
    );

    public void load(final boolean fromReload) {
        Commons.getCore().logger().info("Loading external files...");
        allExternalFiles.forEach(file -> file.load(fromReload));
        Commons.getCore().logger().info("Loaded external files.");
    }
}
