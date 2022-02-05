package me.lokka30.commanddefender.core.filter.set.action.type;

import de.leonhard.storage.sections.FlatFileSection;
import me.lokka30.commanddefender.core.Commons;
import me.lokka30.commanddefender.core.debug.DebugCategory;
import me.lokka30.commanddefender.core.debug.DebugHandler;
import me.lokka30.commanddefender.core.filter.set.CommandSet;
import me.lokka30.commanddefender.core.filter.set.action.Action;
import me.lokka30.commanddefender.core.filter.set.action.ActionHandler;
import me.lokka30.commanddefender.core.util.Message;
import me.lokka30.commanddefender.core.util.universal.UniversalPlayer;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;

public class SendMessage implements ActionHandler {

    @Override
    public @NotNull String identifier() {
        return "send-message";
    }

    @Override
    public @NotNull Optional<Action> parse(final @NotNull CommandSet parentSet, final @NotNull FlatFileSection section) {
        final String path = "actions." + identifier();

        if(!section.contains(path)) {
            return Optional.empty();
        }

        return Optional.of(new SendMessageAction(section.getStringList(path)));
    }

    public record SendMessageAction(
            @NotNull List<String> messages
    ) implements Action {

        @Override
        public void run(@NotNull UniversalPlayer player, @NotNull String[] args) {
            final Message message = new Message(messages(),
                    new Message.Placeholder("%prefix%", Commons.core().fileHandler().messages().data().get("common.prefix", "&b&lCommandDefender: &7")),
                    new Message.Placeholder("%player-name%", player.name()),
                    new Message.Placeholder("%command%", String.join(" ", args))
            );

            message.send(player);

            if(DebugHandler.isDebugCategoryEnabled(DebugCategory.ACTIONS)) {
                Commons.core().logger().debug(DebugCategory.ACTIONS, String.format(
                        "Sent messages '%s' to player '%s'.",
                        message.finalMessages(),
                        player.name()
                ));
            }
        }

    }

}