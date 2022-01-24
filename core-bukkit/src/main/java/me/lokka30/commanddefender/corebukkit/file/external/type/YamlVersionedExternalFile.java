package me.lokka30.commanddefender.corebukkit.file.external.type;

import de.leonhard.storage.Yaml;
import org.jetbrains.annotations.NotNull;

public interface YamlVersionedExternalFile extends VersionedExternalFile {

    // Don't add @NotNull to data() - must be null before the file starts
    // loading, as instantiating Yaml will cause it to load - we want it
    // to load only when we tell it to.
    Yaml getData();

    @NotNull @Override
    default String nameWithExtension() {
        return nameWithoutExtension() + ".yml";
    }

    @Override
    default int getInstalledVersion() {
        return getData().get("file-metadata.version", -1);
    }
}
