package com.grubnest.game.core.paper;

import com.grubnest.game.core.PluginMessage;
import com.grubnest.game.core.databasehandler.MySQL;
import com.grubnest.game.core.databasehandler.MySQLData;
import com.grubnest.game.core.databasehandler.utils.Disabler;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

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

        getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "GrubnestCore is Enabled");

        loadConfig();
        this.sql = new MySQL(MySQLData.dataInitializer());
    }

    /**
     * Loads the config and enables copying defaults
     */
    public void loadConfig() {
        getConfig().options().copyDefaults(true);
        saveConfig();
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
