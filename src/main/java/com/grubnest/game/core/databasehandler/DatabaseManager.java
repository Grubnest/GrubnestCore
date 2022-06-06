package com.grubnest.game.core.databasehandler;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Objects;

public class DatabaseManager {

    private static DatabaseManager instance;
    private final MySQL mysql;

    private DatabaseManager() {
        this.mysql = new MySQL(MySQLData.dataInitializer());
    }

    public static DatabaseManager getInstance() {
        return Objects.requireNonNullElseGet(instance, () -> instance = new DatabaseManager());
    }

    public static Connection getConnection() throws SQLException {
        return getInstance().mysql.getConnection();
    }

    public MySQL getMySQL() {
        return this.mysql;
    }

}
