package me.lokka30.commanddefender.corebukkit;

import me.lokka30.commanddefender.core.Core;
import me.lokka30.commanddefender.core.command.UniversalCommand;
import me.lokka30.commanddefender.core.command.commanddefender.CommandDefenderCommand;
import me.lokka30.commanddefender.core.log.Logger;
import me.lokka30.commanddefender.corebukkit.converter.BukkitConverter;
import me.lokka30.commanddefender.corebukkit.file.FileHandler;
import me.lokka30.commanddefender.corebukkit.listener.ListenerInfo;
import me.lokka30.commanddefender.corebukkit.listener.PlayerCommandPreprocessListener;
import me.lokka30.commanddefender.corebukkit.listener.PlayerCommandSendListener;
import me.lokka30.commanddefender.corebukkit.log.BukkitLogger;
import me.lokka30.commanddefender.corebukkit.util.BukkitColorizer;
import me.lokka30.commanddefender.corebukkit.util.BukkitUtils;
import org.bukkit.command.PluginCommand;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.concurrent.TimeUnit;

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
        final long startTime = System.currentTimeMillis();

        // llad files
        getFileHandler().load(false);

        // register listeners
        registerListeners();

        // register commands
        registerCommands();

        // print total time taken
        final long duration = TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis() - startTime);
        this.getLogger().info("Plugin enabled successfully &8(&7took &b" + duration + " seconds&8)&7.");
    }

    @Override
    public void onDisable() {}

    private final HashSet<Listener> allListeners = new HashSet<>(Arrays.asList(
            new PlayerCommandPreprocessListener(),
            new PlayerCommandSendListener()
    ));

    void registerListeners() {
        this.getLogger().info("Registering listeners...");
        allListeners.forEach(listener -> {
            this.getLogger().info("Registering listener '&b" + listener.getClass().getSimpleName() + "&7'...");
            if(listener instanceof ListenerInfo) {
                if(((ListenerInfo) listener).compatibleWithServer()) {
                    getServer().getPluginManager().registerEvents(listener, this);
                    this.getLogger().info("Registed listener.");
                } else {
                    this.getLogger().info("Listener was not registered - incompatible server. This can be safely ignored.");
                }
            } else {
                getServer().getPluginManager().registerEvents(listener, this);
                this.getLogger().info("Registered listener.");
            }
        });
        this.getLogger().info("Registered listeners.");
    }

    private final HashSet<UniversalCommand> allCommands = new HashSet<>(Collections.singletonList(
            new CommandDefenderCommand()
    ));
    void registerCommands() {
        this.getLogger().info("Registering commands...");
        allCommands.forEach(command -> {
            final PluginCommand pluginCommand = getCommand(command.labels()[0]);
            if(pluginCommand == null) {
                this.getCoreLogger().error("Unable to register the command '&b/" + command.labels()[0] + "&7'! " +
                        "Please inform CommandDefender developers.");
            } else {
                pluginCommand.setExecutor(BukkitConverter.universalCommandToBukkit(command));
            }
        });
        this.getLogger().info("Registered commands.");
    }

    @Override @NotNull
    public Logger getCoreLogger() { return logger; }
    private final Logger logger = new BukkitLogger();

    @Override @NotNull
    public String colorize(@NotNull String msg) {
        return BukkitUtils.colorize(msg);
    }

    @Override
    public @NotNull HashSet<String> getRegisteredConditions() {
        return null;
    }

    @Override
    public @NotNull HashSet<String> getRegisteredActions() {
        return null;
    }

    @Override
    public @NotNull HashSet<String> getRegisteredOptions() {
        return null;
    }

    private final BukkitColorizer colorizer = new BukkitColorizer();

    @NotNull
    public FileHandler getFileHandler() { return fileHandler; }
    private final FileHandler fileHandler = new FileHandler();

}