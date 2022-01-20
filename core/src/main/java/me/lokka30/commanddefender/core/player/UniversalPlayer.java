package me.lokka30.commanddefender.core.player;

import org.jetbrains.annotations.NotNull;

public interface UniversalPlayer {

    void sendChatMessage(final @NotNull String msg);

    boolean hasPermission(final @NotNull String permission);

}
