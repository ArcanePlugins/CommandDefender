package me.lokka30.commanddefender.core.util.universal;

import java.util.UUID;
import org.jetbrains.annotations.NotNull;

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

    boolean isOp();

}
