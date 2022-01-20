package me.lokka30.commanddefender.core.command;

import org.jetbrains.annotations.NotNull;

public interface UniversalCommandSender {

    @NotNull
    String name();

    void sendChatMessage(final @NotNull String msg);

    boolean hasPermission(final @NotNull String permission);

}
