package com.grubnest.game.core;

import com.grubnest.game.core.DatabaseHandler.MySQL;
import com.grubnest.game.core.DatabaseHandler.MySQLData;
import com.grubnest.game.core.DatabaseHandler.Utils.Disabler;
import com.velocitypowered.api.proxy.ProxyServer;
import org.bukkit.plugin.java.JavaPlugin;
import org.slf4j.Logger;

public class GrubnestCorePlugin extends JavaPlugin {

    private MySQL sql;
    private static GrubnestCorePlugin instance;

    /**
     * Runs when plugin is enabled
     */
    @Override
    public void onEnable() {
        instance = this;

        //Register Plugin messaging channels on enable
        this.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        this.getServer().getMessenger().registerIncomingPluginChannel(this, "BungeeCord", new PluginMessage());

        getConfig().options().copyDefaults(true);
        saveConfig();
        sql = new MySQL(dataInitializer());
    }

    /**
     * Runs when plugin is disabled
     */
    @Override
    public void onDisable() {
        Disabler.getInstance().disableAll();
        //Unregister channels on disable
        this.getServer().getMessenger().unregisterOutgoingPluginChannel(this);
        this.getServer().getMessenger().unregisterIncomingPluginChannel(this);
    }

    /**
     * Initialize data from config.yml
     *
     * @return MySQLData
     */
    private MySQLData dataInitializer() {
        String host = getConfig().getString("Database.hostname");
        String port = getConfig().getString("Database.port");
        String database = getConfig().getString("Database.database");
        String username = getConfig().getString("Database.username");
        String password = getConfig().getString("Database.password");

        int minimumConnections = getConfig().getInt("Database.minimumConnections");
        int maximumConnections = getConfig().getInt("Database.maximumConnections");
        long connectionTimeout = getConfig().getLong("Database.connectionTimeout");
        return new MySQLData(host, username, password, port, database, minimumConnections, maximumConnections, connectionTimeout);
    }

    /**
     * Get SQL Object
     *
     * @return SQL object
     */
    public MySQL getMySQL() {
        return sql;
    }

    /**
     * Get Plugin Instance
     *
     * @return Plugin Instance
     */
    public static GrubnestCorePlugin getInstance() {
        return instance;
    }
}
