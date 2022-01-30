package me.lokka30.commanddefender.corebukkit.util.universal;

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
    public void play(final @NotNull UniversalPlayer player) {
        final BukkitPlayer bukkitPlayer = BukkitConverter.universalPlayerToBukkit(player);
        bukkitPlayer.player().playSound(
                bukkitPlayer.player().getLocation(),
                Sound.valueOf(identifier()),
                (float) volume(),
                (float) pitch()
        );
    }

}
