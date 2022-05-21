package com.grubnest.game.core.velocity;

import com.google.inject.Inject;
import com.grubnest.game.core.GrubnestCorePlugin;
import com.velocitypowered.api.event.PostOrder;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.player.ServerConnectedEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.ProxyServer;
import net.kyori.adventure.text.Component;
import org.slf4j.Logger;

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
        this.server.sendMessage(Component.text("SERVER CONNECTION EVENT FIRED FOR: " + event.getPlayer().getUsername()));


        GrubnestCorePlugin.getInstance().getServer().getScheduler().runTaskAsynchronously(GrubnestCorePlugin.getInstance(), () -> {
            try (Connection connection = GrubnestCorePlugin.getInstance().getMySQL().getConnection()) {
                PreparedStatement statement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS `Players` (" +
                        "UUID varchar(30), " +
                        "name varchar(20)" +
                        ");" +
                        "IF NOT EXISTS ( SELECT 1 FROM `Player` WHERE UUID = " + event.getPlayer().getUniqueId().toString() + " )" +
                        "BEGIN" +
                        "INSERT INTO `Players` (UUID, username) VALUES ('" + event.getPlayer().getUniqueId().toString() + "', '" + event.getPlayer().getUsername() + "')" +
                        "END");
                statement.executeUpdate();
            } catch (java.sql.SQLException e) {
                e.printStackTrace();
            }
        });
    }
}
