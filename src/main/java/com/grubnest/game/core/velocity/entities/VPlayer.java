package com.grubnest.game.core.velocity.entities;

import com.grubnest.game.core.bridge.entities.GrubnestPlayer;
import com.grubnest.game.core.velocity.VelocityPlugin;
import com.velocitypowered.api.proxy.Player;

/**
 * Represents a custom player entity on the Velocity server
 *
 * @author Theeef
 * @version 1.0 at 5/26/2022
 */
public class VPlayer implements GrubnestPlayer {

    private Player player;

    /**
     * Creates a custom player instance for Velocity
     *
     * @param player the player this represents
     */
    public VPlayer(Player player) {
        this.player = player;
    }

    public void updateUsername() {
        VelocityPlugin.getInstance().getMySQL().updatePlayerUsername(this.player);
    }

}
