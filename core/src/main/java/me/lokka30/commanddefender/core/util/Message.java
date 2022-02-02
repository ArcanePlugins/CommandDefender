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
    private List<String> finalMessages;
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
    public List<String> getMessages() { return messages; }

    @NotNull
    public Placeholder[] getPlaceholders() { return placeholders; }

    @NotNull
    public List<String> getFinalMessages() { return finalMessages; }

    private void buildFinalMessages() {
        final List<String> newFinalMessages = new ArrayList<>();

        for(String msg : finalMessages) {
            for(Placeholder placeholder : placeholders) {
                newFinalMessages.add(
                        Commons.core.colorize(
                                msg.replace(placeholder.id(), placeholder.replacement())
                        )
                );
            }
        }

        finalMessages = newFinalMessages;
    }

    public void send(@NotNull final UniversalCommandSender recipient) {
        finalMessages.forEach(recipient::sendChatMessage);
    }

    public void send(@NotNull final UniversalPlayer recipient) {
        finalMessages.forEach(recipient::sendChatMessage);
    }

}
