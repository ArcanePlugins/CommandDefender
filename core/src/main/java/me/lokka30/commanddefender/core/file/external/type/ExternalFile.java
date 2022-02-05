package me.lokka30.commanddefender.core.file.external.type;

import me.lokka30.commanddefender.core.Commons;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public interface ExternalFile {

    /* NAME */

    @NotNull String nameWithoutExtension();

    @NotNull String nameWithExtension();

    /* PATH */

    @NotNull String resourcePath();

    @NotNull default String fullPath() {
        return Commons.core().dataFolder() + File.separator + resourcePath();
    }

    /* LOADING */

    void load(final boolean fromReload);

    /* FILE MGMT */

    default boolean exists() {
        return new File(fullPath()).exists();
    }

    default void replaceWithDefault() {
        Commons.core().replaceFileWithDefault(this);
    }

    default void backup() {
        if(!exists()) return;

        final File backupsDirectory = new File(Commons.core().dataFolder() + File.separator + "backups");
        backupsDirectory.mkdir(); // doesn't matter if it already exists, it'll just return false

        final String dateAndTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy_HH-mm-ss"));

        try {
            Files.copy(
                    Path.of(fullPath()),
                    Path.of(backupsDirectory.getPath() + File.separator + "backup-" + dateAndTime + "&7-" + nameWithExtension())
            );
        } catch(IOException ex) {
            Commons.core().logger().error("Unable to backup file '&b" + nameWithExtension() + "&7': &7" + ex.getMessage());
        }
    }
}
