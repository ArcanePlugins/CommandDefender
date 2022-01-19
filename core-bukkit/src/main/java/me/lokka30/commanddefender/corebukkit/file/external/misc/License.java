package me.lokka30.commanddefender.corebukkit.file.external.misc;

import me.lokka30.commanddefender.corebukkit.file.external.type.TxtExternalFile;
import org.jetbrains.annotations.NotNull;

import java.io.File;

public class License implements TxtExternalFile {

    @Override
    public @NotNull String nameWithoutExtension() {
        return "license";
    }

    @Override
    public @NotNull String resourcePath() {
        return "misc" + File.separator + nameWithExtension();
    }

    @Override
    public void load(boolean fromReload) {
        replaceWithDefault();
    }
}
