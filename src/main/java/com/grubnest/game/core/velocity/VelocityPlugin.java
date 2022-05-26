package com.grubnest.game.core.velocity;

import com.google.inject.Inject;
import com.grubnest.game.core.databasehandler.MySQL;
import com.grubnest.game.core.databasehandler.MySQLData;
import com.grubnest.game.core.velocity.events.CoreEventListener;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.ProxyServer;
import net.kyori.adventure.text.Component;
import org.slf4j.Logger;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.Map;

/**
 * The VelocityPlugin class is an implementation of the Velocity API.
 * It provides communication to and from the Velocity Proxy server
 * running on the Grubnest network
 *
 * @author Theeef
 * @version 1.1 at 5/23/2022
 */
@Plugin(id = "grubnestcore", name = "Grubnest Core Plugin", version = "0.1.0-SNAPSHOT",
        url = "htts://grubnest.com", description = "Grubnest Core running on Velocity", authors = {"Theeef"})
public class VelocityPlugin {

    private final ProxyServer server;
    private final MySQL sql;
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

    /**
     * Runs when the Proxy server initializes
     *
     * @param event event used in callback
     */
    @Subscribe
    public void onInitialize(ProxyInitializeEvent event) {
        this.server.getEventManager().register(this, new CoreEventListener());

        getMySQL().createTables();
    }

    /**
     * Initialize data from config.yml
     *
     * @return MySQLData
     */
    private MySQLData dataInitializer() {
        Yaml yaml = new Yaml();
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("config.yml");
        Map<String, Object> config;
        config = (Map<String, Object>) ((Map<String, Object>) yaml.load(inputStream)).get("Database");

        String host = (String) config.get("hostname");
        int port = (int) config.get("port");
        String database = (String) config.get("database");
        String username = (String) config.get("username");
        String password = (String) config.get("password");

        int minimumConnections = (int) config.get("minimumConnections");
        int maximumConnections = (int) config.get("maximumConnections");
        long connectionTimeout = (long) (int) config.get("connectionTimeout");

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
