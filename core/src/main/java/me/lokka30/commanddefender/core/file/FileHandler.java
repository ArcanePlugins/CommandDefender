package me.lokka30.commanddefender.core.file;

import me.lokka30.commanddefender.core.Core;
import me.lokka30.commanddefender.core.file.external.AdvancedSettings;
import me.lokka30.commanddefender.core.file.external.Messages;
import me.lokka30.commanddefender.core.file.external.Settings;
import me.lokka30.commanddefender.core.file.external.misc.License;
import me.lokka30.commanddefender.core.file.external.type.ExternalFile;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public final class FileHandler {
    
    private final Core core;
    public FileHandler(final @NotNull Core core) {
        this.core = core;

        this.license = new License(core);
        this.advancedSettings = new AdvancedSettings(core);
        this.messages = new Messages(core);
        this.settings = new Settings(core);

        this.allExternalFiles = Set.of(
                // misc
                license,

                // root
                advancedSettings,
                messages,
                settings
        );
    }

    /* External Files */

    // ... misc ...

    @NotNull
    public License license() { return license; }
    private final License license;

    // ... root ...

    @NotNull
    public AdvancedSettings advancedSettings() { return advancedSettings; }
    private final AdvancedSettings advancedSettings;

    @NotNull
    public Messages messages() { return messages; }
    private final Messages messages;

    @NotNull
    public Settings settings() { return settings; }
    private final Settings settings;

    private final Set<ExternalFile> allExternalFiles;

    public void load(final boolean fromReload) {
        core.logger().info("Loading external files...");
        allExternalFiles.forEach(file -> file.load(fromReload));
        core.logger().info("Loaded external files.");
    }
}
