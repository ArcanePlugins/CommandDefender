package me.lokka30.commanddefender;

import me.lokka30.commanddefender.commands.CommandDefenderCommand;
import me.lokka30.commanddefender.listeners.CommandListeners;
import me.lokka30.commanddefender.managers.CommandManager;
import me.lokka30.microlib.files.YamlConfigFile;
import me.lokka30.microlib.other.UpdateChecker;
import org.bstats.bukkit.Metrics;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class CommandDefender extends JavaPlugin {

    public final CommandManager commandManager = new CommandManager(this);
    public YamlConfigFile settingsFile, messagesFile;

    @Override
    public void onEnable() {
        loadFiles();
        registerListeners();
        registerCommands();
        startMetrics();
        checkForUpdates();
    }

    public void loadFiles() {
        try {
            settingsFile = new YamlConfigFile(this, new File(getDataFolder(), "settings.yml"));
            settingsFile.load();
            checkFileVersion(settingsFile.getConfig(), "settings.yml", 2);
            commandManager.load();

            messagesFile = new YamlConfigFile(this, new File(getDataFolder(), "messages.yml"));
            messagesFile.load();
            checkFileVersion(messagesFile.getConfig(), "messages.yml", 2);
        } catch (IOException ex) {
            getLogger().severe("Unable to load files. This is usually caused when you mistakenly create syntax errors in your .yml files! Stack trace:");
            ex.printStackTrace();
            setEnabled(false);
        }
    }

    private void registerListeners() {
        new CommandListeners(this).registerListeners();
    }

    private void registerCommands() {
        Objects.requireNonNull(getCommand("commanddefender"))
                .setExecutor(new CommandDefenderCommand(this));
    }

    private void startMetrics() {
        new Metrics(this, 8936);
    }

    private void checkForUpdates() {
        if (!settingsFile.getConfig().getBoolean("check-for-updates", true)) {
            return;
        }

        final UpdateChecker updateChecker = new UpdateChecker(this, 84167);
        final String currentVersion = updateChecker.getCurrentVersion();
        updateChecker.getLatestVersion(latestVersion -> {
            if (latestVersion.equals(currentVersion)) {
                return;
            }

            getLogger().warning("A new update for CommandDefender is available on SpigotMC.");
            getLogger().warning("You are running v" + currentVersion + ", but the latest version is v" + latestVersion + ").");
            getLogger().warning("If you've just downloaded the latest version, sometimes Spigot takes a while to update; ignore this in that case.");
            getLogger().warning("< https://www.spigotmc.org/resources/commanddefender.84167/ >");
        });
    }

    private void checkFileVersion(
            YamlConfiguration cfg,
            String cfgName,
            @SuppressWarnings("SameParameterValue") int recommendedVersion
    ) {
        if (cfg.getInt("file-version") != recommendedVersion) {
            getLogger().severe("Configuration file '" + cfgName + "' does not have the correct file version.");
            getLogger().severe("Reset or merge your current changes with the latest file, otherwise you may encounter issues!");
        }
    }

    public String getPrefix() {
        return messagesFile.getConfig().getString("prefix", "&b&lCommandDefender:&7 ");
    }
}