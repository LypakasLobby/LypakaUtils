package com.lypaka.lypakautils.SQL;

import com.lypaka.lypakautils.LypakaUtils;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class ConnectionManager {
    private static HikariDataSource dataSource;

    public static synchronized void configureDataSource(String jdbcUrl, String username, String password, int maximumPoolSize) {
        if (dataSource != null) {
            throw new IllegalStateException("DataSource has already been configured.");
        }

        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(jdbcUrl);
        config.setUsername(username);
        config.setPassword(password);
        config.setMaximumPoolSize(maximumPoolSize);
        // Other configuration settings...

        dataSource = new HikariDataSource(config);
    }

    public static synchronized void configureDataSource(HikariConfig config) {
        if (dataSource != null) {
            throw new IllegalStateException("DataSource has already been configured.");
        }

        try {
            dataSource = new HikariDataSource(config);
            LypakaUtils.logger.info("DataSource configured successfully.");
        } catch (Exception e) {
            LypakaUtils.logger.fatal("Failed to configure DataSource: " + e.getMessage());
            throw new RuntimeException("Failed to configure DataSource", e);
        }
    }

    public static HikariDataSource getDataSource() {
        if (dataSource == null) {
            throw new IllegalStateException("DataSource has not been configured. Call configureDataSource first.");
        }
        return dataSource;
    }

    public static synchronized void updateDataSourceConfiguration(HikariConfig config) {
        if (dataSource == null) {
            throw new IllegalStateException("DataSource has not been configured.");
        }

        try {
            dataSource.setDataSourceProperties(config.getDataSourceProperties());
            LypakaUtils.logger.info("DataSource configuration updated successfully.");
        } catch (Exception e) {
            LypakaUtils.logger.fatal("Failed to update DataSource configuration: " + e.getMessage());
            throw new RuntimeException("Failed to update DataSource configuration", e);
        }
    }

    public static synchronized void closeDataSource() {
        if (dataSource != null) {
            dataSource.close();
            dataSource = null; // Reset the dataSource reference
            LypakaUtils.logger.info("DataSource closed successfully.");
        }
    }

    // Register a shutdown hook to close the DataSource automatically
    static {
        Runtime.getRuntime().addShutdownHook(new Thread(ConnectionManager::closeDataSource));
    }
}
