package com.grubnest.game.core.velocity.events;

import com.grubnest.game.core.velocity.VelocityPlugin;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.player.ServerConnectedEvent;
import net.kyori.adventure.text.Component;

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
    }

}
