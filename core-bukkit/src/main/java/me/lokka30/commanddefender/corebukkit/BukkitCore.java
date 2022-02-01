package me.lokka30.commanddefender.corebukkit;

import me.lokka30.commanddefender.core.Commons;
import me.lokka30.commanddefender.core.Core;
import me.lokka30.commanddefender.core.command.commanddefender.CommandDefenderCommand;
import me.lokka30.commanddefender.core.file.FileHandler;
import me.lokka30.commanddefender.core.util.universal.PlatformHandler;
import me.lokka30.commanddefender.core.util.universal.UniversalCommand;
import me.lokka30.commanddefender.core.util.universal.UniversalLogger;
import me.lokka30.commanddefender.corebukkit.listener.ListenerMetadata;
import me.lokka30.commanddefender.corebukkit.listener.PlayerCommandPreprocessListener;
import me.lokka30.commanddefender.corebukkit.listener.PlayerCommandSendListener;
import me.lokka30.commanddefender.corebukkit.util.BukkitUtils;
import me.lokka30.commanddefender.corebukkit.util.universal.BukkitLogger;
import me.lokka30.commanddefender.corebukkit.util.universal.BukkitPlatformHandler;
import org.bukkit.Bukkit;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class BukkitCore extends JavaPlugin implements Core {

    @Override
    public void onLoad() {
        Commons.core = this;
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

    private final Set<ListenerMetadata> allListeners = Set.of(
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
        universalLogger.info("Registered listeners.");
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
                pluginCommand.setExecutor(BukkitPlatformHandler.universalCommandToBukkit(command));
            }
        });
        logger().info("Registered commands.");
    }

    @Override
    public @NotNull UniversalLogger logger() { return universalLogger; }
    private final UniversalLogger universalLogger = new BukkitLogger();

    @Override
    public @NotNull String colorize(@NotNull String msg) {
        return BukkitUtils.colorize(msg);
    }

    @Override
    public void updateTabCompletionForAllPlayers() {
        // We want to do this slowly as to not haul
        // the server with update requests

        final LinkedList<Player> players = new LinkedList<>(Bukkit.getOnlinePlayers());
        if(players.size() == 0) return;
        final int[] index = {0}; // this is necessary due to the inner class below

        new BukkitRunnable() {
            @Override
            public void run() {
                try {
                    players.get(index[0]).updateCommands();
                    index[0]++;
                } catch(NoSuchMethodError error) {
                    cancel();
                }
            }
        }.runTaskTimer(this, 1L, 1L);
        // every tick, CD will update one online player's tab completion commands list. (20 players per secnd)
        // this is done in a timer rather than all at once to prevent crashes just in case there is a
        // gigantic amount of commands to filter through.
    }

    private final FileHandler fileHandler = new FileHandler(this);
    @Override
    public @NotNull FileHandler fileHandler() { return fileHandler; }

    @Override
    public @NotNull String dataFolder() {
        return getDataFolder().getPath();
    }

    private final BukkitPlatformHandler platformHandler = new BukkitPlatformHandler();
    @Override
    public @NotNull PlatformHandler platformHandler() {
        return platformHandler;
    }

}