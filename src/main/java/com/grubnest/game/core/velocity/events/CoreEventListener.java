package com.grubnest.game.core.velocity.events;

import com.grubnest.game.core.velocity.VelocityPlugin;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.player.ServerConnectedEvent;
import net.kyori.adventure.text.Component;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Listens for core events, like Server Connection for Velocity
 * <p>
 *
 * @author Theeef
 * @version 1.0 at 5/23/2022
 */
public class CoreEventListener {

    /**
     * Logs basic user data to the server database when a user connects
     * to a server on our network
     *
     * @param event The connection event that occurs
     */
    @Subscribe
    public void onServerConnect(ServerConnectedEvent event) {
        VelocityPlugin.getInstance().getServer().sendMessage(Component.text("SERVER CONNECTION EVENT FIRED FOR: " + event.getPlayer().getUsername()));
        String query = """
                INSERT INTO player
                	(uuid, username)
                VALUES
                	("%uuid%", "%username%")
                ON DUPLICATE KEY UPDATE
                	username = "%username%";
                	""";
        query = query.replaceAll("%username%", event.getPlayer().getUsername()).replaceAll("%uuid%", event.getPlayer().getUniqueId().toString());

        try {
            PreparedStatement statement = VelocityPlugin.getInstance().getMySQL().getConnection().prepareStatement(query);
            statement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


    }

}
