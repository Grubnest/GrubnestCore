package com.grubnest.game.core.databasehandler;

import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Objects;

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
        File configFile;
        String jarPath = Paths.get(MySQLData.class.getProtectionDomain().getCodeSource().getLocation().getPath().toString()).getParent().getFileName() + "/GrubnestCore/config.yml";

        // Creates config.yml file if it doesn't exist
        if (!Files.exists(Paths.get(jarPath))) {
            configFile = new File(jarPath.toString());
            configFile.getParentFile().mkdirs();

            try {
                // Copy default config into new file
                Files.copy(Objects.requireNonNull(MySQLData.class.getClassLoader().getResourceAsStream("config.yml")), Paths.get(jarPath));
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException("An exception occurred while creating a new config.yml file");
            }
        }

        InputStream stream;
        try {
            stream = new FileInputStream(new File(jarPath));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException("config.yml file does not exist!");
        }

        Map<String, Object> config = (Map<String, Object>) ((Map<String, Object>) new Yaml().load(stream)).get("Database");

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
