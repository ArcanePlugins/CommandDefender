package me.lokka30.commanddefender.corebukkit.file.external;

import de.leonhard.storage.Yaml;
import me.lokka30.commanddefender.corebukkit.BukkitCore;
import org.jetbrains.annotations.NotNull;

import java.io.File;

public interface YamlExternalFile {

    /* SIMPLIX YAML DATA */

    // Don't add @NotNull to data() - must be null before the file starts
    // loading, as instantiating Yaml will cause it to load - we want it
    // to load only when we tell it to.
    Yaml data();

    /* NAME */

    @NotNull String nameWithoutExtension();

    @NotNull default String nameWithExtension() {
        return nameWithoutExtension() + ".yml";
    }

    /* PATH */

    @NotNull String resourcePath();

    @NotNull default String fullPath() {
        return BukkitCore.getInstance().getDataFolder() + File.separator + resourcePath();
    }

    /* LOADING */

    void load(final boolean fromReload);

    /* MIGRATION */

    void migrate();

    /* VERSIONING */

    int getCurrentVersion();

    default int getInstalledVersion() {
        return data().get("file-metadata.version", -1);
    }

    /* FILE MGMT */

    default void replaceWithDefault() {
        //TODO
    }

    default void backup() {
        //TODO
    }
}
