package me.lokka30.commanddefender.core.util;

import me.lokka30.commanddefender.core.Commons;
import me.lokka30.commanddefender.core.util.universal.UniversalCommandSender;
import me.lokka30.commanddefender.core.util.universal.UniversalPlayer;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class Message {

    public record Placeholder(
            @NotNull String id,
            @NotNull String replacement
    ) {}

    private final List<String> messages;
    private final Placeholder[] placeholders;
    private final List<String> finalMessages;
    public Message(
            @NotNull final List<String> messages,
            @NotNull final Placeholder... placeholders
    ) {
        this.messages = messages;
        this.placeholders = placeholders;
        this.finalMessages = this.messages;

        buildFinalMessages();
    }

    @NotNull
    public List<String> messages() { return messages; }

    @NotNull
    public Placeholder[] placeholders() { return placeholders; }

    @NotNull
    public List<String> finalMessages() { return finalMessages; }

    private void buildFinalMessages() {
        final List<String> newFinalMessages = new ArrayList<>();

        for(String msg : finalMessages) {
            String newFinalMessage = msg;
            for(Placeholder placeholder : placeholders) {
                newFinalMessage = newFinalMessage.replace(placeholder.id(), placeholder.replacement());
            }
            newFinalMessages.add(Commons.core().colorize(newFinalMessage));
        }

        finalMessages.clear();
        finalMessages.addAll(newFinalMessages);
    }

    public void send(@NotNull final UniversalCommandSender recipient) {
        finalMessages.forEach(recipient::sendChatMessage);
    }

    public void send(@NotNull final UniversalPlayer recipient) {
        finalMessages.forEach(recipient::sendChatMessage);
    }

}
