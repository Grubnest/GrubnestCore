package com.grubnest.game.core.Velocity;

import com.google.inject.Inject;
import com.velocitypowered.api.event.PostOrder;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.player.ServerConnectedEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.ProxyServer;
import net.kyori.adventure.text.Component;
import org.slf4j.Logger;

/**
 * The VelocityPlugin class is an implementation of the Velocity API.
 * It provides communication to and from the Velocity Proxy server
 * running on the Grubnest network
 * <p>
 * Date: 5/20/2022
 * Authors: Theeef
 */
@Plugin(id = "grubnestcore", name = "Grubnest Core Plugin", version = "0.1.0-SNAPSHOT",
        url = "htts://grubnest.com", description = "Grubnest Core running on Velocity", authors = {"Theeef"})
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

        this.server.sendMessage(Component.text("GrubnestCore is enabled on Velocity!"));
    }

    /**
     * Logs basic user data to the server database when a user connects
     * to a server on our network
     *
     * @param event The connection event that occurs
     */
    @Subscribe(order = PostOrder.NORMAL)
    public void onServerConnect(ServerConnectedEvent event) {
        // Log data here
    }
}
