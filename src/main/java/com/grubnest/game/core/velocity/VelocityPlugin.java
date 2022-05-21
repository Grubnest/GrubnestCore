package com.grubnest.game.core.velocity;

import com.google.inject.Inject;
import com.grubnest.game.core.GrubnestCorePlugin;
import com.grubnest.game.core.databasehandler.MySQL;
import com.grubnest.game.core.databasehandler.MySQLData;
import com.grubnest.game.core.velocity.events.CoreEventListener;
import com.velocitypowered.api.event.PostOrder;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.player.ServerConnectedEvent;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.ProxyServer;
import net.kyori.adventure.text.Component;
import org.bukkit.configuration.file.YamlConfiguration;
import org.slf4j.Logger;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;

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
    private MySQL sql;
    private static VelocityPlugin instance;


    /**
     * Creates an instance of the Velocity Plugin and injects it
     *
     * @param server The velocity proxy server
     * @param logger The proxy server's logger
     */
    @Inject
    public VelocityPlugin(ProxyServer server, Logger logger) {
        this.server = server;

        this.server.sendMessage(Component.text("GrubnestCore is enabled on Velocity!"));
        this.sql = new MySQL(dataInitializer());
        instance = this;
    }

    @Subscribe
    public void onInitialize(ProxyInitializeEvent event) {
        this.server.getEventManager().register(this, new CoreEventListener());
    }

    /**
     * Initialize data from config.yml
     *
     * @return MySQLData
     */
    private MySQLData dataInitializer() {
        YamlConfiguration config = YamlConfiguration.loadConfiguration(new File(getClass().getResource("config.yml").getPath()));

        String host = config.getString("Database.hostname");
        String port = config.getString("Database.port");
        String database = config.getString("Database.database");
        String username = config.getString("Database.username");
        String password = config.getString("Database.password");

        int minimumConnections = config.getInt("Database.minimumConnections");
        int maximumConnections = config.getInt("Database.maximumConnections");
        long connectionTimeout = config.getLong("Database.connectionTimeout");

        return new MySQLData(host, username, password, port, database, minimumConnections, maximumConnections, connectionTimeout);
    }

    /**
     * Get SQL Object
     *
     * @return SQL object
     */
    public MySQL getMySQL() {
        return this.sql;
    }

    /**
     * Get the ProxyServer object
     *
     * @return ProxyServer object
     */
    public ProxyServer getServer() {
        return this.server;
    }

    /**
     * Get Plugin Instance
     *
     * @return Plugin Instance
     */
    public static VelocityPlugin getInstance() {
        return instance;
    }
}
