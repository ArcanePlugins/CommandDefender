package me.lokka30.commanddefender.corevelocity;

import com.google.inject.Inject;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import com.velocitypowered.api.proxy.ProxyServer;

import java.nio.file.Path;

@Plugin(
        id = "commanddefender",
        name = "CommandDefender",
        version = "3.0.0-SNAPSHOT",
        url = "https://github.com/lokka30/CommandDefender",
        description = "The ultimate command blocking solution for Minecraft servers, built to be robust and with multi-platform support.",
        authors = {"lokka30"}
)
public class VelocityCore {
    private final ProxyServer server;
    private final org.slf4j.Logger inbuiltLogger;
    private final Path dataDirectory;

    @Inject
    public VelocityCore(ProxyServer server, org.slf4j.Logger inbuiltLogger, @DataDirectory Path dataDirectory) {
        this.server = server;
        this.inbuiltLogger = inbuiltLogger;
        this.dataDirectory = dataDirectory;

        inbuiltLogger.info("It loads!");
    }
}
