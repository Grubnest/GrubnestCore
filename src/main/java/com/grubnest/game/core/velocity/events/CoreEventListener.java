package com.grubnest.game.core.velocity.events;

import com.grubnest.game.core.util.Util;
import com.grubnest.game.core.velocity.VelocityPlugin;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.player.ServerConnectedEvent;

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
        Util.updateUsername(VelocityPlugin.getInstance().getMySQL(), event.getPlayer().getUniqueId(), event.getPlayer().getUsername());
    }

}
