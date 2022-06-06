package com.grubnest.game.core.databasehandler;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Objects;

/**
 * Handles connection access for all other Grubnest Plugins
 *
 * @author Theeef
 * @version 1.0 at 6/6/2022
 */
public class DatabaseManager {

    private static DatabaseManager instance;
    private final MySQL mysql;

    private DatabaseManager() {
        this.mysql = new MySQL(MySQLData.dataInitializer());
    }

    /**
     * Gets the Singleton instance of the DatabaseManager
     *
     * @return the database manager
     */
    public static DatabaseManager getInstance() {
        return Objects.requireNonNullElseGet(instance, () -> instance = new DatabaseManager());
    }

    /**
     * Gets a connection from the ConnectionPool
     *
     * @return a connection
     * @throws SQLException
     */
    public Connection getConnection() throws SQLException {
        return this.mysql.getConnection();
    }

    /**
     * Gets the MySQL connection
     *
     * @return mysql object
     */
    public MySQL getMySQL() {
        return this.mysql;
    }

}
