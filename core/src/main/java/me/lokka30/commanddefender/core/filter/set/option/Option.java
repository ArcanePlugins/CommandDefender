package me.lokka30.commanddefender.core.filter.set.option;

import org.jetbrains.annotations.NotNull;

public interface Option {
    @NotNull
    ProcessingStage processingStage();
}