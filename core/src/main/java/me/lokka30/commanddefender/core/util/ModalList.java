package me.lokka30.commanddefender.core.util;

import org.jetbrains.annotations.NotNull;

import java.util.List;

//TODO Marked for removal.

public record ModalList<T>(
        @NotNull ModalListType modalListType,
        @NotNull List<T> contents
) {

    public enum ModalListType {

        INCLUSIVE,

        EXCLUSIVE

    }

    public boolean includes(final T object) {
        // Included | Inclusive | Result //
        // ---------|-----------|------- //
        // Yes      | Yes       | Yes    //
        // No       | Yes       | No     //
        // Yes      | No        | No     //
        // No       | No        | Yes    //

        // Who in their right mind spends this amount of time
        // formatting a brainstorming table in the source code?
        // (I did.)
        return contents.contains(object) && modalListType == ModalListType.INCLUSIVE;
    }

}
