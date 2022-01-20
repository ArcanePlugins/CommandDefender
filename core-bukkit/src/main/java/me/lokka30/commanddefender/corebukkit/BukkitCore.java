package me.lokka30.commanddefender.corebukkit;

import me.lokka30.commanddefender.core.Core;
import me.lokka30.commanddefender.core.command.UniversalCommand;
import me.lokka30.commanddefender.core.command.commanddefender.CommandDefenderCommand;
import me.lokka30.commanddefender.core.log.Logger;
import me.lokka30.commanddefender.corebukkit.conversion.CommandConverter;
import me.lokka30.commanddefender.corebukkit.file.FileHandler;
import me.lokka30.commanddefender.corebukkit.listener.ListenerInfo;
import me.lokka30.commanddefender.corebukkit.listener.PlayerCommandPreprocessListener;
import me.lokka30.commanddefender.corebukkit.listener.PlayerCommandSendListener;
import me.lokka30.commanddefender.corebukkit.log.BukkitLogger;
import me.lokka30.commanddefender.corebukkit.util.BukkitColorizer;
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
        fileHandler().load(false);

        // register listeners
        registerListeners();

        // register commands
        registerCommands();

        // print total time taken
        final long duration = TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis() - startTime);
        logger().info("Plugin enabled successfully &8(&7took &b" + duration + " seconds&8)&7.");
    }

    @Override
    public void onDisable() {}

    private final HashSet<Listener> allListeners = new HashSet<>(Arrays.asList(
            new PlayerCommandPreprocessListener(),
            new PlayerCommandSendListener()
    ));
    void registerListeners() {
        logger().info("Registering listeners...");
        allListeners.forEach(listener -> {
            logger().info("Registering listener '&b" + listener.getClass().getSimpleName() + "&7'...");
            if(listener instanceof ListenerInfo) {
                if(((ListenerInfo) listener).compatibleWithServer()) {
                    getServer().getPluginManager().registerEvents(listener, this);
                    logger().info("Registed listener.");
                } else {
                    logger().info("Listener was not registered - incompatible server. This can be safely ignored.");
                }
            } else {
                getServer().getPluginManager().registerEvents(listener, this);
                logger().info("Registered listener.");
            }
        });
        logger().info("Registered listeners.");
    }

    private final HashSet<UniversalCommand> allCommands = new HashSet<>(Collections.singletonList(
            new CommandDefenderCommand()
    ));
    void registerCommands() {
        logger().info("Registering commands...");
        allCommands.forEach(command -> {
            final PluginCommand pluginCommand = getCommand(command.labels()[0]);
            if(pluginCommand == null) {
                logger().error("Unable to register the command '&b/" + command.labels()[0] + "&7'! " +
                        "Please inform CommandDefender developers.");
            } else {
                pluginCommand.setExecutor(CommandConverter.toBukkitCommand(command));
            }
        });
        logger().info("Registered commands.");
    }

    @Override @NotNull
    public Logger logger() { return logger; }
    private final Logger logger = new BukkitLogger();

    @Override @NotNull
    public String colorize(@NotNull String msg) {
        return colorizer.colorize(msg);
    }
    private final BukkitColorizer colorizer = new BukkitColorizer();

    @NotNull
    public FileHandler fileHandler() { return fileHandler; }
    private final FileHandler fileHandler = new FileHandler();

}