package me.lokka30.commanddefender.corebukkit.file.external.type;

public interface VersionedExternalFile extends ExternalFile {

    void migrate();

    int getCurrentVersion();

    int getInstalledVersion();
}
