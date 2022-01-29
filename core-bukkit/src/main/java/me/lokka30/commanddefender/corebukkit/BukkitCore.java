package me.lokka30.commanddefender.corebukkit;

import me.lokka30.commanddefender.core.Core;
import me.lokka30.commanddefender.core.command.UniversalCommand;
import me.lokka30.commanddefender.core.command.commanddefender.CommandDefenderCommand;
import me.lokka30.commanddefender.core.file.FileHandler;
import me.lokka30.commanddefender.core.filter.set.action.ActionHandler;
import me.lokka30.commanddefender.core.filter.set.condition.ConditionHandler;
import me.lokka30.commanddefender.core.filter.set.option.OptionHandler;
import me.lokka30.commanddefender.core.log.Logger;
import me.lokka30.commanddefender.core.util.Constants;
import me.lokka30.commanddefender.corebukkit.converter.BukkitConverter;
import me.lokka30.commanddefender.corebukkit.listener.CDListener;
import me.lokka30.commanddefender.corebukkit.listener.PlayerCommandPreprocessListener;
import me.lokka30.commanddefender.corebukkit.listener.PlayerCommandSendListener;
import me.lokka30.commanddefender.corebukkit.log.BukkitLogger;
import me.lokka30.commanddefender.corebukkit.util.BukkitUtils;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class BukkitCore extends JavaPlugin implements Core {

    public static BukkitCore instance() {
        return instance;
    }
    private static BukkitCore instance;

    @Override
    public void onLoad() {
        instance = this;

        Constants.DATA_FOLDER = getDataFolder().getPath();
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

    private final Set<CDListener> allListeners = Set.of(
            new PlayerCommandPreprocessListener(),
            new PlayerCommandSendListener()
    );

    void registerListeners() {
        logger().info("Registering listeners...");
        allListeners.forEach(listener -> {
            logger().info("Registering listener '&b" + listener.getClass().getSimpleName() + "&7'...");
            if(listener.compatibleWithServer()) {
                getServer().getPluginManager().registerEvents(listener, this);
                logger().info("Registed listener.");
            } else {
                logger().info("Listener was not registered: incompatible server. This can be safely ignored.");
            }
        });
        logger.info("Registered listeners.");
    }

    private final Set<UniversalCommand> allCommands = Set.of(
            new CommandDefenderCommand()
    );
    void registerCommands() {
        logger().info("Registering commands...");
        allCommands.forEach(command -> {
            final PluginCommand pluginCommand = getCommand(command.labels()[0]);
            if(pluginCommand == null) {
                this.logger().error("Unable to register the command '&b/" + command.labels()[0] + "&7'! " +
                        "Please inform CommandDefender developers.");
            } else {
                pluginCommand.setExecutor(BukkitConverter.universalCommandToBukkit(command));
            }
        });
        logger().info("Registered commands.");
    }

    @Override
    public @NotNull Logger logger() { return logger; }
    private final Logger logger = new BukkitLogger();

    @Override
    public @NotNull String colorize(@NotNull String msg) {
        return BukkitUtils.colorize(msg);
    }

    @Override
    public @NotNull HashSet<ConditionHandler> conditionHandlers() {
        return conditionHandlers;
    }
    private final HashSet<ConditionHandler> conditionHandlers = new HashSet<>();

    @Override
    public @NotNull HashSet<ActionHandler> actionHandlers() {
        return actionHandlers;
    }
    private final HashSet<ActionHandler> actionHandlers = new HashSet<>();

    @Override
    public @NotNull HashSet<OptionHandler> optionHandlers() {
        return optionHandlers;
    }
    private final HashSet<OptionHandler> optionHandlers = new HashSet<>();

    @Override
    public @NotNull FileHandler fileHandler() { return fileHandler; }
    private final FileHandler fileHandler = new FileHandler(this);

}