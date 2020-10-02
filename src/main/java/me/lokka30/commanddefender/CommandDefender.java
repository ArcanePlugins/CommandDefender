package me.lokka30.commanddefender;

import me.lokka30.microlib.MicroLogger;
import me.lokka30.microlib.UpdateChecker;
import org.bstats.bukkit.Metrics;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.Objects;

public class CommandDefender extends JavaPlugin {

    public final MicroLogger logger = new MicroLogger("&b&lCommandDefender: &7");
    public final File settingsFile = new File(getDataFolder(), "settings.yml");
    public final File messagesFile = new File(getDataFolder(), "messages.yml");
    public YamlConfiguration settingsCfg, messagesCfg;

    @Override
    public void onEnable() {
        long startTime = System.currentTimeMillis();

        logger.info("Loading files");
        loadFiles();

        logger.info("Registering events");
        registerEvents();

        logger.info("Registering commands");
        registerCommands();

        logger.info("Starting bStats metrics");
        startMetrics();

        long duration = System.currentTimeMillis() - startTime;
        logger.info("&fLoading complete! &8(&7Took &b" + duration + "ms&8)");

        checkForUpdates();
    }

    public void loadFiles() {
        createIfNotExists(settingsFile, "settings.yml");
        settingsCfg = YamlConfiguration.loadConfiguration(settingsFile);
        checkFileVersion(settingsCfg, "settings.yml", 1);

        createIfNotExists(messagesFile, "messages.yml");
        messagesCfg = YamlConfiguration.loadConfiguration(messagesFile);
        checkFileVersion(messagesCfg, "messages.yml", 1);

        createIfNotExists(new File(getDataFolder(), "license.txt"), "license.txt");
    }

    private void createIfNotExists(File file, String fileName) {
        if (!file.exists()) {
            logger.info("File '&b" + fileName + "&7' didn't exist, creating it...");
            saveResource(fileName, false);
        }
    }

    private void checkFileVersion(YamlConfiguration cfg, String cfgName, @SuppressWarnings("SameParameterValue") int recommendedVersion) {
        if (cfg.getInt("advanced.file-version") != recommendedVersion) {
            logger.warning("Configuration file '&b" + cfgName + "&7' does not have the correct file version. Reset or merge your current changes with the latest file or errors are likely to happen!");
        }
    }

    private void registerEvents() {
        getServer().getPluginManager().registerEvents(new CommandListener(this), this);
    }

    private void registerCommands() {
        Objects.requireNonNull(getCommand("commanddefender")).setExecutor(new CDCommand(this));
    }

    private void startMetrics() {
        new Metrics(this, 8936);
    }

    private void checkForUpdates() {
        if(settingsCfg.getBoolean("check-for-updates")) {
            final UpdateChecker updateChecker = new UpdateChecker(this, 84167);
            if (!updateChecker.getCurrentVersion().equals(updateChecker.getLatestVersion())) {
                logger.warning("&b(NEW UPDATE) &fA new update is available on SpigotMC!");
            }
        }
    }
}
