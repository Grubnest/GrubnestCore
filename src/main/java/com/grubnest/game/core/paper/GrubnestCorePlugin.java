package com.grubnest.game.core.paper;

import com.grubnest.game.core.databasehandler.utils.Disabler;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * The GrubnestCorePlugin class is an implementation of the Spigot API.
 * It provides communication to and from the Spigot (Paper) Minecraft server
 * running on the Grubnest network
 *
 * @author Theeef
 * @version 1.2 at 5/31/2022
 */
public class GrubnestCorePlugin extends JavaPlugin {

    private static GrubnestCorePlugin instance;

    /**
     * Runs when plugin is enabled
     */
    @Override
    public void onEnable() {
        instance = this;

        getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "GrubnestCore is Enabled");

        loadConfig();
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
     * Get Plugin Instance
     *
     * @return Plugin Instance
     */
    public static GrubnestCorePlugin getInstance() {
        if (instance != null)
            return instance;
        else
            return (GrubnestCorePlugin) Bukkit.getPluginManager().getPlugin("GrubnestCore");
    }

}
