package me.lokka30.commanddefender;

import me.lokka30.microlib.MicroLogger;
import me.lokka30.microlib.QuickTimer;
import me.lokka30.microlib.UpdateChecker;
import me.lokka30.microlib.YamlConfigFile;
import org.bstats.bukkit.Metrics;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class CommandDefender extends JavaPlugin {

    public final MicroLogger logger = new MicroLogger("&b&lCommandDefender: &7");
    public YamlConfigFile settingsFile, messagesFile;
    public final CommandManager commandManager = new CommandManager(this);

    @Override
    public void onEnable() {
        QuickTimer timer = new QuickTimer();
        timer.start();

        logger.info("Loading files...");
        try {
            loadFiles();
        } catch (IOException exception) {
            exception.printStackTrace();
        }

        logger.info("Registering listeners...");
        new CommandListeners(this).registerListeners();

        logger.info("Registering commands...");
        registerCommands();

        logger.info("Running misc methods...");
        startMetrics();
        checkForUpdates();

        logger.info("&fLoading complete! &8(&7Took &b" + timer.getTimer() + "ms&8)");
    }

    public void loadFiles() throws IOException {
        settingsFile = new YamlConfigFile(this, new File(getDataFolder(), "settings.yml"));
        settingsFile.load();
        checkFileVersion(settingsFile.getConfig(), "settings.yml", 1);
        commandManager.load();

        messagesFile = new YamlConfigFile(this, new File(getDataFolder(), "messages.yml"));
        messagesFile.load();
        checkFileVersion(messagesFile.getConfig(), "messages.yml", 1);

        createIfNotExists(new File(getDataFolder(), "license.txt"));
    }

    private void createIfNotExists(File file) {
        if (!file.exists()) {
            saveResource(file.getName(), false);
        }
    }

    private void checkFileVersion(YamlConfiguration cfg, String cfgName, @SuppressWarnings("SameParameterValue") int recommendedVersion) {
        if (cfg.getInt("advanced.file-version") != recommendedVersion) {
            logger.warning("Configuration file '&b" + cfgName + "&7' does not have the correct file version. Reset or merge your current changes with the latest file or errors are likely to happen!");
        }
    }

    private void registerCommands() {
        Objects.requireNonNull(getCommand("commanddefender")).setExecutor(new CommandDefenderCommand(this));
    }

    private void startMetrics() {
        new Metrics(this, 8936);
    }

    private void checkForUpdates() {
        if (settingsFile.getConfig().getBoolean("check-for-updates")) {
            try {
                final UpdateChecker updateChecker = new UpdateChecker(this, 84167);
                updateChecker.getLatestVersion(version -> {
                    if (!version.equals(updateChecker.getCurrentVersion())) {
                        logger.warning("&b(NEW UPDATE) &fA new update is available on SpigotMC!");
                    }
                });
            } catch (NoClassDefFoundError error) {
                logger.warning("The update checker only works for servers running &fMinecraft 1.11.x and older&7. Please &fdisable the update checker in the configuration&7 as it seems your server is older than what the update checker supports.");
            }
        }
    }

    public boolean isOneThirteen() {
        try {
            Class.forName("org.bukkit.event.player.PlayerCommandSendEvent");
        } catch (ClassNotFoundException e) {
            return false;
        }
        return true;
    }
}
