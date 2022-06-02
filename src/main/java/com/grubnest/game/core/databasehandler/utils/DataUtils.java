package com.grubnest.game.core.databasehandler.utils;

import com.grubnest.game.core.databasehandler.MySQL;

import java.util.UUID;

/**
 * Static accessors for MySQL queries through the
 * MySQL class
 *
 * @author Theeef
 * @version 1.0 at 6/2/2022
 */
public abstract class DataUtils {

    /**
     * Updates a players username stored in the database
     *
     * @param mysql    the mysql instance
     * @param id       the player's uuid
     * @param username the player's username
     */
    public static void updatePlayerUsername(MySQL mysql, UUID id, String username) {
        mysql.updatePlayerUsername(id, username);
    }

    /**
     * Gets a player's UUID from their last stored username
     *
     * @param mysql    the mysql instance
     * @param username the player's username
     * @return the player's uuid
     */
    public static UUID getIDFromUsername(MySQL mysql, String username) {
        return mysql.getIdFromUsername(username);
    }

    /**
     * Get a players last stored username from their uuid
     *
     * @param mysql the mysql instance
     * @param id    the player's uuid
     * @return the player's username
     */
    public static String getUsernameFromID(MySQL mysql, UUID id) {
        return mysql.getUsernameFromID(id);
    }
}
