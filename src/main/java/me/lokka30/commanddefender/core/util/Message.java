package me.lokka30.commanddefender.core.util;

import me.lokka30.commanddefender.core.command.UniversalCommandSender;
import org.jetbrains.annotations.NotNull;

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

    @NotNull private final String baseMessage;
    @NotNull private final Placeholder[] placeholders;
    @NotNull private String finalMessage;
    public Message(@NotNull final String baseMessage, @NotNull final Placeholder... placeholders) {
        this.baseMessage = baseMessage;
        this.placeholders = placeholders;
        this.finalMessage = baseMessage;

        buildFinalMessage();
    }

    @NotNull
    public String getBaseMessage() { return baseMessage; }

    @NotNull
    public Placeholder[] getPlaceholders() { return placeholders; }

    @NotNull
    public String getFinalMessage() { return finalMessage; }

    private void buildFinalMessage() {
        for(Placeholder placeholder : placeholders) {
            finalMessage = finalMessage.replace(placeholder.getId(), placeholder.getReplacement());
        }

        finalMessage = MessageUtil.colorize(finalMessage);
    }

    public void send(@NotNull final UniversalCommandSender recipient) {
        recipient.sendChatMessage(finalMessage);
    }
}
