package me.lokka30.commanddefender.corebukkit.file.external.type;

import me.lokka30.commanddefender.corebukkit.BukkitCore;
import org.jetbrains.annotations.NotNull;

import java.io.File;

public interface ExternalFile {

    /* NAME */

    @NotNull String nameWithoutExtension();

    @NotNull String nameWithExtension();

    /* PATH */

    @NotNull String resourcePath();

    @NotNull default String fullPath() {
        return BukkitCore.instance().getDataFolder() + File.separator + resourcePath();
    }

    /* LOADING */

    void load(final boolean fromReload);

    /* FILE MGMT */

    default void replaceWithDefault() {
        //TODO
    }

    default void backup() {
        //TODO
    }
}
