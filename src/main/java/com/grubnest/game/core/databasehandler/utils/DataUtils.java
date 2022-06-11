package com.grubnest.game.core.databasehandler.utils;

import com.grubnest.game.core.databasehandler.DatabaseManager;
import com.grubnest.game.core.databasehandler.MySQL;

import java.sql.SQLException;
import java.util.Optional;
import java.util.UUID;

/**
 * Static accessors for MySQL queries through the
 * MySQL class
 *
 * @author Theeef
 * @version 1.0.1 at 6/11/2022
 */
public abstract class DataUtils {

    /**
     * Updates a players username stored in the database
     *
     * @param id       the player's uuid
     * @param username the player's username
     * @throws SQLException Thrown if database cannot be accessed
     */
    public static void updatePlayerUsername(UUID id, String username) throws SQLException {
        DatabaseManager.getInstance().getMySQL().updatePlayerUsername(id, username);
    }

    /**
     * Gets a player's UUID from their last stored username
     *
     * @param username the player's username
     * @return an optional containing the player's UUID, or nothing if an error occurred
     */
    public static Optional<UUID> getIDFromUsername(String username) {
        return DatabaseManager.getInstance().getMySQL().getIdFromUsername(username);
    }

    /**
     * Get a players last stored username from their uuid
     *
     * @param id the player's uuid
     * @return the player's username
     */
    public static Optional<String> getUsernameFromID(UUID id) {
        return DatabaseManager.getInstance().getMySQL().getUsernameFromID(id);
    }
}
