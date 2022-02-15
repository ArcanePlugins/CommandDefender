package me.lokka30.commanddefender.corebukkit.listener;

import com.destroystokyo.paper.event.server.AsyncTabCompleteEvent;
import java.util.LinkedList;
import java.util.List;
import me.lokka30.commanddefender.core.Commons;
import me.lokka30.commanddefender.core.filter.FilterContextType;
import me.lokka30.commanddefender.core.util.CoreUtils;
import me.lokka30.commanddefender.corebukkit.listener.misc.ListenerExt;
import me.lokka30.commanddefender.corebukkit.util.universal.BukkitPlatformHandler;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;

public class AsyncTabCompleteListener implements ListenerExt {

    @Override
    public boolean compatibleWithServer() {
        return CoreUtils.classExists("com.destroystokyo.paper.event.server.AsyncTabCompleteEvent");
    }

    @EventHandler
    public void onTabComplete(final AsyncTabCompleteEvent event) {
        if(!Commons.core().fileHandler().advancedSettings().data()
            .get("listeners.async-tab-complete.enabled", true)) {
            return;
        }

        if(!event.isAsynchronous()) {
            if(Commons.core().fileHandler().advancedSettings().data()
                .get("listeners.async-tab-complete.ignore-sync", true)) {
                return;
            }
        }

        if(!(event.getSender() instanceof final Player player)) {
            return;
        }

        if(event.getCompletions().size() == 0) return;

        final LinkedList<String> toRemove = new LinkedList<>();

        for(String completion : event.getCompletions()) {
            // we want to combine all the buffer and the completion but remove the last arg of the buffer
            // e.g. if buffer is `/hello th` and the completion is `there`, then -> `/hello there`.
            final LinkedList<String> toCheck = new LinkedList<>();
            final String[] splitBuffer = event.getBuffer().substring(1).split(" ");
            for(int i = 0; i < splitBuffer.length; i++) {
                if(i != 0 && i == (splitBuffer.length - 1)) break;
                toCheck.add(splitBuffer[i]);
            }
            toCheck.add(completion);

            if(!Commons.core().commandFilter().canAccess(
                FilterContextType.COMMAND_SUGGESTION,
                BukkitPlatformHandler.bukkitPlayerToUniversal(player),
                toCheck.toArray(new String[0])
            )) {
                toRemove.add(completion);
            }
        }

        final List<String> completions = new LinkedList<>(List.copyOf(event.getCompletions()));
        completions.removeAll(toRemove);
        event.setCompletions(completions);
    }
}
