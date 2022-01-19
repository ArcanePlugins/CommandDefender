package me.lokka30.commanddefender.core.file;

import me.lokka30.commanddefender.core.Core;
import org.jetbrains.annotations.NotNull;

public class FileHandler {

    private final Core core;
    public FileHandler(@NotNull final Core core) { this.core = core; }

    public void loadFiles() {
        loadInternalFiles();
        loadExternalFiles();
    }

    void loadInternalFiles() {
        /*
        core.logger().info("Loading internal files...");
        core.logger().info("Loaded internal files.");
         */
    }

    void loadExternalFiles() {
        core.logger().info("Loading external files...");
        core.logger().info("Loaded external files.");
    }

}
