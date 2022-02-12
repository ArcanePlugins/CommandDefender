package me.lokka30.commanddefender.corebukkit;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;
import me.lokka30.commanddefender.core.Commons;
import me.lokka30.commanddefender.core.Core;
import me.lokka30.commanddefender.core.command.commanddefender.CommandDefenderCommand;
import me.lokka30.commanddefender.core.debug.DebugHandler;
import me.lokka30.commanddefender.core.file.FileHandler;
import me.lokka30.commanddefender.core.file.external.type.ExternalFile;
import me.lokka30.commanddefender.core.filter.CommandFilter;
import me.lokka30.commanddefender.core.util.CoreUtils;
import me.lokka30.commanddefender.core.util.universal.PlatformHandler;
import me.lokka30.commanddefender.core.util.universal.UniversalCommand;
import me.lokka30.commanddefender.core.util.universal.UniversalLogger;
import me.lokka30.commanddefender.corebukkit.listener.AsyncTabCompleteListener;
import me.lokka30.commanddefender.corebukkit.listener.PlayerCommandPreprocessListener;
import me.lokka30.commanddefender.corebukkit.listener.PlayerCommandSendListener;
import me.lokka30.commanddefender.corebukkit.listener.TabCompleteListener;
import me.lokka30.commanddefender.corebukkit.listener.misc.ListenerExt;
import me.lokka30.commanddefender.corebukkit.util.BukkitUtils;
import me.lokka30.commanddefender.corebukkit.util.universal.BukkitLogger;
import me.lokka30.commanddefender.corebukkit.util.universal.BukkitPlatformHandler;
import org.bukkit.Bukkit;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BukkitCore extends JavaPlugin implements Core {

    @Override
    public void onLoad() {
        Commons.setCore(this);
    }

    @Override
    public void onEnable() {
        final long startTime = System.currentTimeMillis();

        registerBukkitConditions();
        fileHandler().load(false);
        debugHandler.load();
        commandFilter().load();
        registerListeners();
        registerCommands();
        checkForUpdates();

        // print total time taken
        logger().info(
            "Plugin enabled successfully &8(&7took &b" + (System.currentTimeMillis() - startTime)
                + "ms&8)&7.");
    }

    void registerBukkitConditions() {
        //Commons.conditionHandlers.add(new WorldName());
    }

    void registerListeners() {
        logger().info("Registering listeners...");

        final HashSet<ListenerExt> toRegister = new HashSet<>();

        final AsyncTabCompleteListener asyncTabCompleteListener = new AsyncTabCompleteListener();
        final PlayerCommandPreprocessListener playerCommandPreprocessListener = new PlayerCommandPreprocessListener();
        final PlayerCommandSendListener playerCommandSendListener = new PlayerCommandSendListener();
        final TabCompleteListener tabCompleteListener = new TabCompleteListener();

        if(asyncTabCompleteListener.compatibleWithServer()) {
            toRegister.add(asyncTabCompleteListener);
        } else {
            toRegister.add(tabCompleteListener);
        }

        if(playerCommandSendListener.compatibleWithServer()) {
            toRegister.add(playerCommandSendListener);
        }

        toRegister.addAll(Set.of(playerCommandPreprocessListener));

        universalLogger.info("Registered &b" + toRegister.size() + "&7 listeners &8[&b" +
            String.join("&7, &b", CoreUtils.collectionToClassList(toRegister)) +
            "&8]&7.");
    }

    void registerCommands() {
        logger().info("Registering commands...");

        final Set<UniversalCommand> allCommands = Set.of(
            new CommandDefenderCommand()
        );

        allCommands.forEach(command -> {
            final PluginCommand pluginCommand = getCommand(command.labels()[0]);
            if (pluginCommand == null) {
                this.logger()
                    .error("Unable to register the command '&b/" + command.labels()[0] + "&7'! " +
                        "Please inform CommandDefender developers.");
            } else {
                pluginCommand.setExecutor(BukkitPlatformHandler.universalCommandToBukkit(command));
            }
        });
        logger().info("Registered &b" + allCommands.size() + "&7 commands &8[&b" +
            String.join("&7, &b", CoreUtils.collectionToClassList(allCommands)) +
            "&8]&7.");
    }

    @Override
    public @NotNull UniversalLogger logger() {
        return universalLogger;
    }

    private final UniversalLogger universalLogger = new BukkitLogger();

    @Override
    public @NotNull String colorize(@NotNull String msg) {
        return BukkitUtils.colorize(msg);
    }

    @Override
    public @NotNull CommandFilter commandFilter() {
        return filter;
    }

    private final CommandFilter filter = new CommandFilter();

    @Override
    public void updateTabCompletionForAllPlayers() {
        final LinkedList<Player> players = new LinkedList<>(Bukkit.getOnlinePlayers());
        if (players.size() == 0) {
            return;
        }
        final int[] index = {0}; // this is necessary due to the inner class below

        new BukkitRunnable() {
            @Override
            public void run() {
                try {
                    final Player player = players.get(index[0]);
                    if (player.isOnline()) {
                        player.updateCommands();
                    }

                    index[0]++;
                    if (index[0] == players.size()) {
                        cancel();
                    }
                } catch (NoSuchMethodError error) {
                    cancel();
                }
            }
        }.runTaskTimer(this, 1L, 1L);
        // every tick, CD will update one online player's tab completion commands list. (20 players per secnd)
        // this is done in a timer rather than all at once to prevent crashes just in case there is a
        // gigantic amount of commands to filter through. even if there are, doing it this way will allow
        // the main thread to run still and thus not crash the server. of course, this is in a scenario
        // where a server owner has a stupidly extreme amount of commands on their server and/or equally
        // stupidly extreme amount of command sets.
    }

    @Override
    public @Nullable String pluginThatRegisteredCommand(@NotNull String command) {
        final PluginCommand pluginCommand = Bukkit.getPluginCommand(command);
        if (pluginCommand == null) {
            return null;
        }
        return pluginCommand.getPlugin().getName();
    }

    @Override
    public @NotNull Set<String> aliasesOfCommand(@NotNull String command) {
        final PluginCommand pluginCommand = Bukkit.getPluginCommand(command);
        if (pluginCommand == null) {
            return Set.of();
        }
        final Set<String> aliases = new HashSet<>();
        aliases.add(pluginCommand.getLabel());
        aliases.addAll(pluginCommand.getAliases());
        return aliases;
    }

    @Override
    public void replaceFileWithDefault(ExternalFile externalFile) {
        saveResource(externalFile.resourcePath(), true);
    }

    @Override
    public void checkForUpdates() {
        //TODO
    }

    @Override
    public @NotNull DebugHandler debugHandler() {
        return debugHandler;
    }

    private final DebugHandler debugHandler = new DebugHandler();

    @Override
    public @NotNull FileHandler fileHandler() {
        return fileHandler;
    }

    private final FileHandler fileHandler = new FileHandler();

    @Override
    public @NotNull String dataFolder() {
        return getDataFolder().getPath();
    }

    @Override
    public @NotNull PlatformHandler platformHandler() {
        return platformHandler;
    }

    private final BukkitPlatformHandler platformHandler = new BukkitPlatformHandler();

}