package me.lokka30.commanddefender;

import me.lokka30.commanddefender.commands.CommandDefenderCommand;
import me.lokka30.commanddefender.listeners.CommandListeners;
import me.lokka30.commanddefender.managers.CommandManager;
import me.lokka30.commanddefender.utils.Utils;
import me.lokka30.microlib.files.YamlConfigFile;
import me.lokka30.microlib.maths.QuickTimer;
import me.lokka30.microlib.other.UpdateChecker;
import org.bstats.bukkit.Metrics;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class CommandDefender extends JavaPlugin {

    public YamlConfigFile settingsFile, messagesFile;
    public final CommandManager commandManager = new CommandManager(this);

    @Override
    public void onEnable() {
        Utils.logger.info("&f~ Initiating start-up procedure ~");
        final QuickTimer timer = new QuickTimer();

        loadFiles();
        registerListeners();
        registerCommands();

        Utils.logger.info("&fStart-up: &7Running misc procedures...");
        startMetrics();
        checkForUpdates();

        Utils.logger.info("&f~ Start-up complete, took &b" + timer.getTimer() + "ms&f ~");
    }

    public void loadFiles() {
        Utils.logger.info("&fFile Loader: &7Loading files...");

        try {
            settingsFile = new YamlConfigFile(this, new File(getDataFolder(), "settings.yml"));
            settingsFile.load();
            checkFileVersion(settingsFile.getConfig(), "settings.yml", 2);
            commandManager.load();

            messagesFile = new YamlConfigFile(this, new File(getDataFolder(), "messages.yml"));
            messagesFile.load();
            checkFileVersion(messagesFile.getConfig(), "messages.yml", 2);

            createIfNotExists(new File(getDataFolder(), "license.txt"));
        } catch (IOException ex) {
            Utils.logger.error("&fFile Loader: &7An error occured whilst attempting to load files. Stack trace:");
            ex.printStackTrace();
        }
    }

    private void createIfNotExists(File file) {
        if (!file.exists()) {
            saveResource(file.getName(), false);
        }
    }

    private void checkFileVersion(YamlConfiguration cfg, String cfgName, @SuppressWarnings("SameParameterValue") int recommendedVersion) {
        if (cfg.getInt("file-version") != recommendedVersion) {
            Utils.logger.error("Configuration file '&b" + cfgName + "&7' does not have the correct file version. Reset or merge your current changes with the latest file or errors are highly likely to occur!");
        }
    }

    private void registerListeners() {
        Utils.logger.info("&fStart-up: &7Registering listeners...");

        new CommandListeners(this).registerListeners();
    }

    private void registerCommands() {
        Utils.logger.info("&fStart-up: &7Registering commands...");
        Objects.requireNonNull(getCommand("commanddefender")).setExecutor(new CommandDefenderCommand(this));
    }

    private void startMetrics() {
        new Metrics(this, 8936);
    }

    private void checkForUpdates() {
        if (!settingsFile.getConfig().getBoolean("check-for-updates", true)) { return; }

        final UpdateChecker updateChecker = new UpdateChecker(this, 84167);
        final String currentVersion = updateChecker.getCurrentVersion().split(" ")[0];
        updateChecker.getLatestVersion(latestVersion -> {
            if (!latestVersion.equals(currentVersion)) {
                Utils.logger.warning("&fUpdate Checker: &7A new update is available on SpigotMC! &8(&7You are running &bv" + currentVersion + "&7, but the latest version is &bv" + latestVersion + "&8)&7.");
            }
        });
    }

    public String getPrefix() {
        return messagesFile.getConfig().getString("prefix", "&b&lCommandDefender:&7 ");
    }
}
