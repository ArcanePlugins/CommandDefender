package me.lokka30.commanddefender.corebukkit.file;

import me.lokka30.commanddefender.corebukkit.BukkitCore;
import me.lokka30.commanddefender.corebukkit.file.external.AdvancedSettings;
import me.lokka30.commanddefender.corebukkit.file.external.Messages;
import me.lokka30.commanddefender.corebukkit.file.external.Settings;
import me.lokka30.commanddefender.corebukkit.file.external.misc.License;
import me.lokka30.commanddefender.corebukkit.file.external.type.ExternalFile;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.HashSet;

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

    private final HashSet<ExternalFile> allExternalFiles = new HashSet<>(Arrays.asList(
            // misc
            license,

            // root
            advancedSettings,
            messages,
            settings
    ));

    public void load(final boolean fromReload) {
        BukkitCore.instance().getLogger().info("Loading external files...");
        allExternalFiles.forEach(file -> file.load(fromReload));
        BukkitCore.instance().getLogger().info("Loaded external files.");
    }
}
