package com.grubnest.game.core.velocity;

import com.google.inject.Inject;
import com.grubnest.game.core.databasehandler.MySQL;
import com.grubnest.game.core.databasehandler.MySQLData;
import com.grubnest.game.core.databasehandler.utils.DataUtils;
import com.grubnest.game.core.velocity.events.CoreEventListener;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.PluginContainer;
import com.velocitypowered.api.proxy.ProxyServer;
import net.kyori.adventure.text.Component;
import org.slf4j.Logger;

import java.util.Optional;

/**
 * The VelocityPlugin class is an implementation of the Velocity API.
 * It provides communication to and from the Velocity Proxy server
 * running on the Grubnest network
 *
 * @author Theeef
 * @version 1.2 at 5/31/2022
 */
@Plugin(id = "grubnestcore", name = "Grubnest Core Plugin", version = "0.1.0-SNAPSHOT",
        url = "htts://grubnest.com", description = "Grubnest Core running on Velocity", authors = {"Theeef"})
public class VelocityPlugin {

    private static ProxyServer server;
    private static MySQL sql;
    private static VelocityPlugin instance;

    /**
     * Creates an instance of the Velocity Plugin and injects it
     *
     * @param server The velocity proxy server
     * @param logger The proxy server's logger
     */
    @Inject
    public VelocityPlugin(ProxyServer server, Logger logger) {
        VelocityPlugin.server = server;

        VelocityPlugin.server.sendMessage(Component.text("GrubnestCore is enabled on Velocity!"));
        VelocityPlugin.sql = new MySQL(MySQLData.dataInitializer());

        instance = this;
    }

    /**
     * Runs when the Proxy server initializes
     *
     * @param event event used in callback
     */
    @Subscribe
    public void onInitialize(ProxyInitializeEvent event) {
        VelocityPlugin.server.getEventManager().register(this, new CoreEventListener());

        getMySQL().createTables();
    }

    /**
     * Get SQL Object
     *
     * @return SQL object
     */
    public MySQL getMySQL() {
        return VelocityPlugin.sql;
    }

    /**
     * Get ProxyServer Object
     *
     * @return ProxyServer object
     */
    public static ProxyServer getProxyServer() {
        return VelocityPlugin.server;
    }

    /**
     * Get Plugin Instance
     *
     * @return Plugin Instance
     */
    public static VelocityPlugin getInstance() {
        if (instance != null)
            return instance;
        else {
            Optional<PluginContainer> pluginContainer = VelocityPlugin.getProxyServer().getPluginManager().getPlugin("grubnestcore");
            Optional<?> plugin = pluginContainer.isPresent() ? pluginContainer.get().getInstance() : Optional.empty();

            if (plugin.isPresent())
                return (VelocityPlugin) plugin.get();
            else
                throw new RuntimeException("GrubnestCore's velocity plugin instance was null!");
        }
    }
}
