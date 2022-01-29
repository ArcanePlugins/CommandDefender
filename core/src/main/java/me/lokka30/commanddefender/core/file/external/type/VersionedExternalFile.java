package me.lokka30.commanddefender.core.file.external.type;

public interface VersionedExternalFile extends ExternalFile {

    void migrate();

    int currentVersion();

    int installedVersion();
}
