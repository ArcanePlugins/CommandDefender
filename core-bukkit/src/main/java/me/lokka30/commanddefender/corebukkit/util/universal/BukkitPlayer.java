package me.lokka30.commanddefender.corebukkit.util.universal;

import java.util.UUID;
import me.lokka30.commanddefender.core.util.universal.UniversalPlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

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

    @Override
    public boolean isOp() {
        return player().isOp();
    }

}
