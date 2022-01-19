package me.lokka30.commanddefender.corebukkit;

import me.lokka30.commanddefender.core.Core;
import me.lokka30.commanddefender.core.log.Logger;
import me.lokka30.commanddefender.corebukkit.log.BukkitLogger;
import me.lokka30.commanddefender.corebukkit.util.BukkitColorizer;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public class BukkitCore extends JavaPlugin implements Core {

    public static BukkitCore getInstance() {
        return instance;
    }
    private static BukkitCore instance;

    @Override
    public void onLoad() {
        instance = this;
    }

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
    public Logger logger() { return logger; }
    private final Logger logger = new BukkitLogger();

    private final BukkitColorizer colorizer = new BukkitColorizer();
    @Override
    public @NotNull String colorize(@NotNull String msg) {
        return colorizer.colorize(msg);
    }
}