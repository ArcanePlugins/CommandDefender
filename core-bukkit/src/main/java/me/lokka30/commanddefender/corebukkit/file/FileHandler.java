package me.lokka30.commanddefender.corebukkit.file;

import me.lokka30.commanddefender.corebukkit.file.external.Settings;

import java.util.Arrays;

public class FileHandler {

    public void load(final boolean fromReload) {
        Arrays.asList(
                new Settings()
        ).forEach(file -> file.load(fromReload));
    }
}
