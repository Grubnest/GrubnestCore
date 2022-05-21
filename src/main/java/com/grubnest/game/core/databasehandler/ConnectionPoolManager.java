package com.grubnest.game.core.databasehandler;

import com.grubnest.game.core.databasehandler.utils.Deactivated;
import com.grubnest.game.core.databasehandler.utils.Disabler;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Handles connection from mysql and uses HikariCP
 *
 * @author tamilpp25
 * @version 1.0 at 15-5-2022
 */
public class ConnectionPoolManager implements Deactivated {

    private final MySQLData data;
    private HikariDataSource dataSource;

    /**
     * Construct mysql HikariCP
     *
     * @param data MysqlDATA
     */
    public ConnectionPoolManager(MySQLData data) {
        this.data = data;
        setupPool();
        Disabler.getInstance().registerDeactivated(this);
    }

    /**
     * Setup mysql connection Pool with HikariCP to get multiple connections.
     */
    private void setupPool() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(
                "jdbc:mysql://" +
                        data.HOST +
                        ":" +
                        data.PORT +
                        "/" +
                        data.DATABASE
        );
        config.setDriverClassName("com.mysql.jdbc.Driver");
        config.setUsername(data.USERNAME);
        config.setPassword(data.PASSWORD);
        config.setMinimumIdle(data.minimumConnections);
        config.setMaximumPoolSize(data.maximumConnections);
        config.setConnectionTimeout(data.connectionTimeout);
        config.setConnectionTestQuery("SELECT 1");
        dataSource = new HikariDataSource(config);
    }

    /**
     * Get MySQL Pool connection
     *
     * @return Sql Connection
     * @throws SQLException if some error..
     */
    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    /**
     * Temp close method can close using try-with statement too
     * Recommending using try-with statement to close automatically!
     *
     * @param conn Connection
     * @param ps   PreparedStatement
     * @param res  ResultSet
     */
    public void close(Connection conn, PreparedStatement ps, ResultSet res) {
        if (conn != null) try {
            conn.close();
        } catch (SQLException ignored) {
        }
        if (ps != null) try {
            ps.close();
        } catch (SQLException ignored) {
        }
        if (res != null) try {
            res.close();
        } catch (SQLException ignored) {
        }
    }

    /**
     * Close pool when everything done...
     */
    public void closePool() {
        if (dataSource != null && !dataSource.isClosed()) {
            dataSource.close();
        }
    }

    /**
     * Auto disable classes on disable if multiple instance are there too
     */
    @Override
    public void onDisable() {
        closePool();
    }
}
