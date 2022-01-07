package me.lokka30.commanddefender.core.command;

import org.jetbrains.annotations.NotNull;

public interface UniversalCommandSender {

    void sendChatMessage(@NotNull final String message);

    boolean hasPermission(@NotNull final String permission);

}
