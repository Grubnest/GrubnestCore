package com.grubnest.game.core.velocity;

import com.google.inject.Inject;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.ProxyServer;
import org.slf4j.Logger;

/**
 * The VelocityPlugin class is an implementation of the Velocity API.
 * It provides communication to and from the Velocity Proxy server
 * running on the Grubnest network
 * <p>
 * Date: 5/20/2022
 * Authors: Theeef
 */
@Plugin(id = "myfirstplugin", name = "My First Plugin", version = "0.1.0-SNAPSHOT",
        url = "https://example.org", description = "I did it!", authors = {"Me"})
public class VelocityPlugin {

    private final ProxyServer server;
    private final Logger logger;


    /**
     * Creates an instance of the Velocity Plugin and injects it
     *
     * @param server The velocity proxy server
     * @param logger The proxy server's logger
     */
    @Inject
    public VelocityPlugin(ProxyServer server, Logger logger) {
        this.server = server;
        this.logger = logger;
    }

}
