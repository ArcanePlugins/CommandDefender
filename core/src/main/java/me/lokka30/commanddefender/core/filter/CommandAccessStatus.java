package me.lokka30.commanddefender.core.filter;

public enum CommandAccessStatus {

    // a command set has decided to block the command
    DENY,

    // a command set does not care about the command
    // due to not matching enough of its configured
    // conditions.
    UNKNOWN,

    // a command set has decided to allow the command.
    ALLOW

}
