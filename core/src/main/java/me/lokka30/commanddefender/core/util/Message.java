package me.lokka30.commanddefender.core.util;

import me.lokka30.commanddefender.core.Core;
import me.lokka30.commanddefender.core.unused.command.UniversalCommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Message {

    public static class Placeholder {
        @NotNull private final String id;
        @NotNull private final String replacement;

        public Placeholder(@NotNull final String id, @NotNull final String replacement) {
            this.id = id; this.replacement = replacement;
        }

        @NotNull
        public String getId() { return id; }

        @NotNull
        public String getReplacement() { return replacement; }
    }

    private final Core core;
    private final List<String> messages;
    private final Set<Placeholder> placeholders;
    private List<String> finalMessages;
    public Message(
            @NotNull final Core core,
            @NotNull final List<String> messages,
            @NotNull final Set<Placeholder> placeholders
    ) {
        this.core = core;
        this.messages = messages;
        this.placeholders = placeholders;
        this.finalMessages = this.messages;

        buildFinalMessages();
    }

    @NotNull
    public List<String> getMessages() { return messages; }

    @NotNull
    public Set<Placeholder> getPlaceholders() { return placeholders; }

    @NotNull
    public List<String> getFinalMessages() { return finalMessages; }

    private void buildFinalMessages() {
        final List<String> newFinalMessages = new ArrayList<>();

        for(String msg : finalMessages) {
            for(Placeholder placeholder : placeholders) {
                newFinalMessages.add(
                        core.colorize(
                                msg.replace(placeholder.getId(), placeholder.getReplacement())
                        )
                );
            }
        }


        finalMessages = newFinalMessages;
    }

    public void send(@NotNull final UniversalCommandSender recipient) {
        finalMessages.forEach(recipient::sendChatMessage);
    }
}
