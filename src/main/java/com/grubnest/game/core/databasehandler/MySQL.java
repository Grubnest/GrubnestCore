package com.grubnest.game.core.databasehandler;

import com.grubnest.game.core.GrubnestCorePlugin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Main class for handling mysql data
 * make your methods for getting / setting here in async!
 * new MySQL class can be initialized outside main class too!
 *
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
	 * This is an example table query for creating queries make a similar method like this!
	 * MAKE SURE that all queries are run async so that it doesn't freeze the main thread
	 * you don't have to worry about closing a connection since its auto closed by
	 */
	public void testTableQuery() {
		GrubnestCorePlugin.getInstance().getServer().getScheduler().runTaskAsynchronously(GrubnestCorePlugin.getInstance(),()->{
			try (Connection conn = getConnection()){
				PreparedStatement statement = conn.prepareStatement(
						"CREATE TABLE IF NOT EXISTS `Test` " +
								"(" +
								"UUID varchar(30)" +
								")"
				);
				statement.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		});
	}

	//You make method to fetch / add data in this class

	/**
	 * Close pool on plugin Disable
	 */
	public void onDisable(){
		closePool();
	}

}
