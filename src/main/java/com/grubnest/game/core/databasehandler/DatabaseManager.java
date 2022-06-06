package com.grubnest.game.core.databasehandler;

import java.sql.Connection;
import java.sql.SQLException;

public abstract class DatabaseManager {

    private static MySQL mysql;

    private void init() {
        if (mysql == null)
            mysql = new MySQL(MySQLData.dataInitializer());
    }

    public static Connection getConnection() throws SQLException {
        return mysql.getConnection();
    }

}
