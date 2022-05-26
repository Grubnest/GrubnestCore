package com.grubnest.game.core.velocity.events;

import com.grubnest.game.core.velocity.VelocityPlugin;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.player.ServerConnectedEvent;
import net.kyori.adventure.text.Component;

import java.sql.Connection;
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
        try {
            Connection connection = VelocityPlugin.getInstance().getMySQL().getConnection();
            PreparedStatement statement = connection.prepareStatement("""
                    INSERT INTO player
                    	(uuid, username)
                    VALUES
                    	(?, ?)
                    ON DUPLICATE KEY UPDATE
                    	username = ?;
                    	""");
            statement.setString(1, event.getPlayer().getUniqueId().toString());
            statement.setString(2, event.getPlayer().getUsername());
            statement.setString(3, event.getPlayer().getUsername());
            statement.executeUpdate();
            statement.close();
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


    }

}
