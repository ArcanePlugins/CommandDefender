package me.lokka30.commanddefender.corebukkit.util.universal;

import me.lokka30.commanddefender.core.Commons;
import me.lokka30.commanddefender.core.util.universal.UniversalPlayer;
import me.lokka30.commanddefender.core.util.universal.UniversalSound;
import org.bukkit.Sound;
import org.jetbrains.annotations.NotNull;

public record BukkitSound(
    @NotNull String identifier,
    double volume,
    double pitch
) implements UniversalSound {

    @Override
    public void play(@NotNull UniversalPlayer universalPlayer) {
        final Sound sound;
        try {
            sound = Sound.valueOf(identifier());
        } catch(IllegalArgumentException ex) {
            Commons.core().logger().error("Unable to play sound '&b" + identifier() + "&7', "
                + "invalid ID. It is likely incompatible with your Minecraft version, or is not "
                + "spelt correctly.");
            return;
        }

        final BukkitPlayer bukkitPlayer = BukkitPlatformHandler.universalPlayerToBukkit(
            universalPlayer);
        bukkitPlayer.player().playSound(
            bukkitPlayer.player().getLocation(),
            sound,
            (float) volume(),
            (float) pitch()
        );
    }

}
