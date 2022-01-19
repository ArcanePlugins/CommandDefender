package me.lokka30.commanddefender.corebukkit.file;

import me.lokka30.commanddefender.corebukkit.file.external.SettingsFile;

import java.util.Arrays;

public class FileHandler {

    public void load(final boolean fromReload) {
        Arrays.asList(
                new SettingsFile()
        ).forEach(file -> file.load(fromReload));
    }
}
