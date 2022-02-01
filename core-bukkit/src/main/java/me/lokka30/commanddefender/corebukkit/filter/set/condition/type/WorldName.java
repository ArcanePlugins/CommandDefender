package me.lokka30.commanddefender.corebukkit.filter.set.condition.type;

import de.leonhard.storage.sections.FlatFileSection;
import me.lokka30.commanddefender.core.filter.set.CommandSet;
import me.lokka30.commanddefender.core.filter.set.condition.Condition;
import me.lokka30.commanddefender.core.filter.set.condition.ConditionHandler;
import me.lokka30.commanddefender.core.util.ModalList;
import me.lokka30.commanddefender.core.util.universal.UniversalPlayer;
import me.lokka30.commanddefender.corebukkit.util.universal.BukkitPlatformHandler;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class WorldName implements ConditionHandler {
    @Override
    public @NotNull String identifier() {
        return "world-name";
    }

    @Override
    public @NotNull Optional<Condition> parse(@NotNull CommandSet parentSet, @NotNull FlatFileSection section) {
        //TODO program the parsing, and also register the condition

        /*
        format:

        conditions:
            world-name:
                inclusive-list: ['world']
                ...OR...
                exclusive-list: ['world']
            ...OR...
            world-name-incl: ['world']
            ...OR...
            world-name-excl: ['world']
         */
        return Optional.empty();
    }

    public record WorldNameCondition(
            @NotNull CommandSet parentSet,
            @NotNull ModalList<String> modalList
    ) implements Condition {


        @Override
        public boolean appliesTo(@NotNull UniversalPlayer player, @NotNull String[] args) {
            return modalList.includes(BukkitPlatformHandler.universalPlayerToBukkit(player).player().getWorld().getName());
        }
    }
}