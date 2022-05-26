package com.grubnest.game.core.velocity.events;

import com.grubnest.game.core.velocity.VelocityPlugin;
import com.grubnest.game.core.velocity.entities.VPlayer;
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
        VPlayer player = new VPlayer(event.getPlayer());
        player.updateUsername();
    }

}
