package me.lokka30.commanddefender.core.file.external.misc;

import me.lokka30.commanddefender.core.Commons;
import me.lokka30.commanddefender.core.file.external.type.TxtExternalFile;
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
        Commons.getCore().logger().info("Loading file '&b" + nameWithExtension() + "&7'...");
        replaceWithDefault();
        Commons.getCore().logger().info("Loaded file.");
    }
}
