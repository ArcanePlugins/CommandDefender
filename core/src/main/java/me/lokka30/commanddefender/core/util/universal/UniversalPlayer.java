package me.lokka30.commanddefender.core.util.universal;

import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public interface UniversalPlayer {

    @NotNull
    UUID uuid();

    @NotNull
    String name();

    void sendChatMessage(final @NotNull String msg);

    default void playSound(final @NotNull UniversalSound sound) {
        sound.play(this);
    }

    boolean hasPermission(final @NotNull String permission);

}
