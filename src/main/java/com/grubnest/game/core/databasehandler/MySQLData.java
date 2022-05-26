package com.grubnest.game.core.databasehandler;

import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.Map;

/**
 * MysqlData object to store credentials etc
 *
 * @author tamilpp25, Theeef
 * @version 1.0 at 5/26/2022
 */
public class MySQLData {
    public final String HOST;
    public final String USERNAME;
    public final String PASSWORD;
    public final int PORT;
    public final String DATABASE;

    public final int minimumConnections;
    public final int maximumConnections;
    public final long connectionTimeout;

    public MySQLData(String host, String username, String password, int port, String database, int minimumConnections, int maximumConnections, long connectionTimeout) {
        HOST = host;
        USERNAME = username;
        PASSWORD = password;
        PORT = port;
        DATABASE = database;
        this.minimumConnections = minimumConnections;
        this.maximumConnections = maximumConnections;
        this.connectionTimeout = connectionTimeout;
    }

    /**
     * Initialize data from config.yml
     *
     * @return MySQLData
     */
    public static MySQLData dataInitializer() {
        Yaml yaml = new Yaml();
        InputStream inputStream = MySQLData.class.getClassLoader().getResourceAsStream("config.yml");
        Map<String, Object> config;
        config = (Map<String, Object>) ((Map<String, Object>) yaml.load(inputStream)).get("Database");

        String host = (String) config.get("hostname");
        int port = (int) config.get("port");
        String database = (String) config.get("database");
        String username = (String) config.get("username");
        String password = (String) config.get("password");

        int minimumConnections = (int) config.get("minimumConnections");
        int maximumConnections = (int) config.get("maximumConnections");
        long connectionTimeout = (long) (int) config.get("connectionTimeout");

        return new MySQLData(host, username, password, port, database, minimumConnections, maximumConnections, connectionTimeout);
    }
}
