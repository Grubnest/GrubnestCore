package com.grubnest.game.core.velocity.events;

import com.grubnest.game.core.velocity.VelocityPlugin;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.player.ServerConnectedEvent;

import java.sql.SQLException;

/**
 * Listens for core events, like Server Connection for Velocity
 * <p>
 *
 * @author Theeef
 * @version 1.1 at 5/29/2022
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
        VelocityPlugin.getInstance().getMySQL().updatePlayerUsername(event.getPlayer().getUniqueId(), event.getPlayer().getUsername());
    }

}
