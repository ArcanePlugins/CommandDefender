package me.lokka30.commanddefender.corebukkit.util.universal;

import me.lokka30.commanddefender.core.util.universal.UniversalPlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public record BukkitPlayer(
        @NotNull UUID uuid,
        @NotNull Player player
) implements UniversalPlayer {

    @Override
    public @NotNull String name() {
        return player.getName();
    }

    @Override
    public void sendChatMessage(@NotNull String msg) {
        player().sendMessage(msg);
    }

    @Override
    public boolean hasPermission(@NotNull String permission) {
        return player().hasPermission(permission);
    }

}
