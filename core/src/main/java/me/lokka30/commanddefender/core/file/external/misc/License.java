package me.lokka30.commanddefender.core.file.external.misc;

import me.lokka30.commanddefender.core.Core;
import me.lokka30.commanddefender.core.file.external.type.TxtExternalFile;
import org.jetbrains.annotations.NotNull;

import java.io.File;

public class License implements TxtExternalFile {

    private final Core core;
    public License(final @NotNull Core core) { this.core = core; }

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
        core.logger().info("Loading file '&b" + nameWithExtension() + "&7'...");
        replaceWithDefault();
        core.logger().info("Loaded file.");
    }
}
