package me.lokka30.commanddefender.corebukkit.file;

import me.lokka30.commanddefender.corebukkit.BukkitCore;
import me.lokka30.commanddefender.corebukkit.file.external.AdvancedSettings;
import me.lokka30.commanddefender.corebukkit.file.external.Messages;
import me.lokka30.commanddefender.corebukkit.file.external.Settings;
import me.lokka30.commanddefender.corebukkit.file.external.type.ExternalFile;

import java.util.Arrays;
import java.util.HashSet;

public final class FileHandler {

    /* External Files */

    public AdvancedSettings getAdvancedSettings() { return advancedSettings; }
    private final AdvancedSettings advancedSettings = new AdvancedSettings();

    public Messages getMessages() { return messages; }
    private final Messages messages = new Messages();

    public Settings getSettings() { return settings; }
    private final Settings settings = new Settings();

    private final HashSet<ExternalFile> allExternalFiles = new HashSet<>(Arrays.asList(
            advancedSettings, messages, settings
    ));

    public void load(final boolean fromReload) {
        BukkitCore.getInstance().logger().info("Loading external files...");
        allExternalFiles.forEach(file -> file.load(fromReload));
        BukkitCore.getInstance().logger().info("Loaded external files.");
    }
}
