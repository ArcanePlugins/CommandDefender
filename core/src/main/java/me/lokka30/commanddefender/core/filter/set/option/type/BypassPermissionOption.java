package me.lokka30.commanddefender.core.filter.set.option.type;

import me.lokka30.commanddefender.core.filter.set.option.Option;
import org.jetbrains.annotations.NotNull;

public class BypassPermissionOption implements Option {

    public BypassPermissionOption(
            final @NotNull String bypassPermission
    ) {
        this.bypassPermission = bypassPermission;
    }

    private final String bypassPermission;
    public String bypassPermission() { return bypassPermission; }
}
