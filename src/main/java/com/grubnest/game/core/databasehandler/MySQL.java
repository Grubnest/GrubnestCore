package com.grubnest.game.core.databasehandler;

import java.sql.PreparedStatement;
import java.sql.SQLException;

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

    //You make method to fetch / add data in this class

    /**
     * Close pool on plugin Disable
     */
    public void onDisable() {
        closePool();
    }

    public void createTables() {
        try {
            PreparedStatement statement = getConnection().prepareStatement("""
                    CREATE TABLE `player` (
                        uuid varchar(30) PRIMARY KEY,
                        username varchar(16)
                    )
                    """);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
