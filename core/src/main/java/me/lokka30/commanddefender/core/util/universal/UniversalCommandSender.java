package me.lokka30.commanddefender.core.util.universal;

import org.jetbrains.annotations.NotNull;

public interface UniversalCommandSender {

    @NotNull
    String name();

    void sendChatMessage(final @NotNull String msg);

    boolean hasPermission(final @NotNull String permission);

}
