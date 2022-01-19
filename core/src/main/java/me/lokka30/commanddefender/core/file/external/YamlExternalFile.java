package me.lokka30.commanddefender.core.file.external;

import de.leonhard.storage.Yaml;
import org.jetbrains.annotations.NotNull;

public interface YamlExternalFile {

    /* FILE NAME, PATH */

    @NotNull String nameWithoutExtension();

    @NotNull default String nameWithExtension() {
        return nameWithoutExtension() + ".yml";
    }

    @NotNull String fullPath();

    @NotNull String relativePath();

    /* SIMPLIX YAML DATA */

    @NotNull Yaml data();

    /* LOADING */

    void load(final boolean fromReload);

    /* MIGRATION */

    void migrate();

    /* FILE MGMT */

    void replaceWithDefaultFile();

    void backup();

    /* FILE VERSIONS */

    default int installedFileVersion() {
        return data().get("file-metadata.version", -1);
    }

    int latestFileVersion();
}
