package com.grubnest.game.core.databasehandler;

import com.velocitypowered.api.proxy.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

/**
 * Main class for handling mysql data
 * make your methods for getting / setting here in async!
 * new MySQL class can be initialized outside main class too!
 * <p>
 * Recommend using getMySQL method from main class as it properly does the work and closes connection on disable!
 * make a new instance of this if you really know what you are doing!
 *
 * @author tamilpp25
 * @version 1.0 at 15-5-2022
 */
public class MySQL extends ConnectionPoolManager {
    public MySQL(MySQLData data) {
        super(data);
    }

    /**
     * Close pool on plugin Disable
     */
    public void onDisable() {
        closePool();
    }

    /**
     * Creates any tables required for the database. Runs at proxy server initialization
     */
    public void createTables() {
        try (Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement("""
                    CREATE TABLE IF NOT EXISTS `player` (
                        uuid varchar(36) PRIMARY KEY,
                        username varchar(16)
                    )
                    """);
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Updates a players username stored in the database
     *
     * @param id       the player's uuid
     * @param username the player's username
     */
    public void updatePlayerUsername(UUID id, String username) {
        try (Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement("""
                    INSERT INTO player
                    	(uuid, username)
                    VALUES
                    	(?, ?)
                    ON DUPLICATE KEY UPDATE
                    	username = ?;
                    	""");
            statement.setString(1, id.toString());
            statement.setString(2, username);
            statement.setString(3, username);
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets a player's UUID from their last stored username
     *
     * @param username the player's username
     * @return the player's uuid
     */
    public UUID getIdFromUsername(String username) {
        try (Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement("""
                    SELECT uuid
                    FROM player
                    WHERE username = ?
                    """);
            statement.setString(1, username);
            ResultSet queryResults = statement.executeQuery();
            statement.close();

            return UUID.fromString(queryResults.getString(1));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        throw new NullPointerException("Could not retrive a UUID for a user with the name \"" + username + "\"");
    }
}
