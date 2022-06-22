package com.grubnest.game.core.databasehandler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.UUID;

/**
 * Main class for handling mysql data
 * make your methods for getting / setting here in async!
 * new MySQL class can be initialized outside main class too!
 * <p>
 * Recommend using getMySQL method from main class as it properly does the work and closes connection on disable!
 * make a new instance of this if you really know what you are doing!
 *
 * @author tamilpp25, Theeef
 * @version 1.1 at 5/31/2022
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
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement("""
                     CREATE TABLE IF NOT EXISTS `player` (
                         uuid varchar(36) PRIMARY KEY,
                         username varchar(16)
                     )
                     """)) {
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Couldn't create player table");
        }
    }

    /**
     * Updates the username mapped to the specified ID
     *
     * @param id       the uuid
     * @param username the username
     * @return whether or not the name was successfully updated
     */
    public boolean updatePlayerUsername(UUID id, String username) {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement("""
                     INSERT INTO player
                     	(uuid, username)
                     VALUES
                     	(?, ?)
                     ON DUPLICATE KEY UPDATE
                     	username = ?;
                     	""")) {
            statement.setString(1, id.toString());
            statement.setString(2, username);
            statement.setString(3, username);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    /**
     * Gets a player's UUID from their last stored username
     *
     * @param username the player's username
     * @return the player's uuid
     */
    public Optional<UUID> getIdFromUsername(String username) {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement("""
                     SELECT uuid
                     FROM player
                     WHERE username = ?
                     """)) {
            statement.setString(1, username);
            ResultSet queryResults = statement.executeQuery();

            if (queryResults.next()) {
                return Optional.of(UUID.fromString(queryResults.getString(1)));
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            e.printStackTrace();

            return Optional.empty();
        }
    }

    /**
     * Get a players last stored username from their uuid
     *
     * @param id the player's uuid
     * @return the player's username
     */
    public Optional<String> getUsernameFromID(UUID id) {
        try (Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement("""
                    SELECT username
                    FROM player
                    WHERE uuid = ?
                    """);
            statement.setString(1, id.toString());
            ResultSet queryResults = statement.executeQuery();

            if (queryResults.next()) {
                return Optional.of(queryResults.getString(1));
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            e.printStackTrace();

            return Optional.empty();
        }
    }
}
