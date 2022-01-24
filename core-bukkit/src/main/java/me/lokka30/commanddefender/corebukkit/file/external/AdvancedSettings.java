package me.lokka30.commanddefender.corebukkit.file.external;

import de.leonhard.storage.LightningBuilder;
import de.leonhard.storage.Yaml;
import de.leonhard.storage.internal.settings.ConfigSettings;
import de.leonhard.storage.internal.settings.DataType;
import de.leonhard.storage.internal.settings.ReloadSettings;
import me.lokka30.commanddefender.corebukkit.BukkitCore;
import me.lokka30.commanddefender.corebukkit.file.external.type.YamlVersionedExternalFile;
import org.jetbrains.annotations.NotNull;

import java.io.File;

public class AdvancedSettings implements YamlVersionedExternalFile {

    private Yaml data;
    @Override
    public Yaml getData() {
        return data;
    }

    @Override
    public @NotNull String nameWithoutExtension() {
        return "advanced-settings";
    }

    @Override
    public @NotNull String resourcePath() {
        return nameWithExtension();
    }

    @Override
    public void load(boolean fromReload) {
        BukkitCore.getInstance().getLogger().info("Loading file '&b" + nameWithExtension() + "&7'...");
        if (fromReload) {
            data.forceReload();
        } else {
            data = LightningBuilder
                    .fromFile(new File(fullPath()))
                    .setReloadSettings(ReloadSettings.MANUALLY)
                    .setConfigSettings(ConfigSettings.PRESERVE_COMMENTS)
                    .setDataType(DataType.SORTED)
                    .createYaml();
        }
        migrate();
        BukkitCore.getInstance().getLogger().info("Loaded file.");
    }

    @Override
    public void migrate() {
        if(getInstalledVersion() == getCurrentVersion()) return;

        for(int i = getInstalledVersion(); i < getCurrentVersion(); i++) {
            BukkitCore.getInstance().getLogger().info("Attempting to migrate file '&b" + nameWithExtension() + "&7' from version &b" + getInstalledVersion() + "&7 to &b" + i + "&7...");
            switch(getInstalledVersion()) {
                case 1:
                    break;
                default:
                    BukkitCore.getInstance().getLogger().error(
                            "No migration logic available for file '&b" + nameWithExtension() + "&7' @ version " +
                                    "&b" + i + "&7. Inform CommandDefender developers ASAP.");
                    return;
            }
            BukkitCore.getInstance().getLogger().info("Migrated file '&b" + nameWithExtension() + "&7' to version &b" + i + "&7 successfully.");
        }
    }

    @Override
    public int getCurrentVersion() {
        return 1;
    }
}
