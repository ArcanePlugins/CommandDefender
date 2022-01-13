package me.lokka30.commanddefender.corebukkit;

import me.lokka30.commanddefender.core.Core;
import me.lokka30.commanddefender.core.debug.DebugHandler;
import me.lokka30.commanddefender.core.file.FileHandler;
import me.lokka30.commanddefender.core.log.Logger;
import me.lokka30.commanddefender.corebukkit.log.BukkitLogger;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public class BukkitCore extends JavaPlugin implements Core {

    @Override
    public void onEnable() {
        //
    }

    @Override
    public void onDisable() {
        //
    }

    void registerListeners() {
        //
    }

    void registerCommands() {
        //
    }

    @Override @NotNull
    public FileHandler getFileHandler() { return fileHandler; }
    private final FileHandler fileHandler = new FileHandler(this);

    @Override @NotNull
    public Logger getCoreLogger() { return logger; }
    public final Logger logger = new BukkitLogger();

    @Override @NotNull
    public DebugHandler getDebugHandler() { return debugHandler; }
    private final DebugHandler debugHandler = new DebugHandler(this);
}