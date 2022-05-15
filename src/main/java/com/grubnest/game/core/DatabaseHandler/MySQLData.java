package com.grubnest.game.core.DatabaseHandler;

/** MysqlData object to store credentials etc
 * @author tamilpp25
 * @version 1.0 at 15-5-2022
 */
public class MySQLData {
	public final String HOST;
	public final String USERNAME;
	public final String PASSWORD;
	public final String PORT;
	public final String DATABASE;

	public final int minimumConnections;
	public final int maximumConnections;
	public final long connectionTimeout;

	public MySQLData(String host, String username, String password, String port, String database, int minimumConnections, int maximumConnections, long connectionTimeout) {
		HOST = host;
		USERNAME = username;
		PASSWORD = password;
		PORT = port;
		DATABASE = database;
		this.minimumConnections = minimumConnections;
		this.maximumConnections = maximumConnections;
		this.connectionTimeout = connectionTimeout;
	}
}
