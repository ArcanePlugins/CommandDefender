package me.lokka30.commanddefender.core.filter.set.option.type;

import me.lokka30.commanddefender.core.filter.set.option.Option;
import org.jetbrains.annotations.NotNull;

public record BypassPermissionOption(
        @NotNull String bypassPermission
) implements Option {}