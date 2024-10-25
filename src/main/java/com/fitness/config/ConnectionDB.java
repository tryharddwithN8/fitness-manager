package com.fitness.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import com.fitness.App;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * ConnectionDB
 */
public class ConnectionDB {

    public static void testDBConnection() {
        Properties properties = new Properties();
        try (InputStream input = App.class.getResourceAsStream("/application.properties")) {
            properties.load(input);

            String driver = properties.getProperty("db.driver");
            String url = properties.getProperty("db.url");
            String user = properties.getProperty("db.user");
            String password = properties.getProperty("db.password");

            Class.forName(driver);

            try (Connection connection = DriverManager.getConnection(url, user, password)) {
                if (connection != null) 
                    System.out.println("Connected to the database!");
                else 
                    System.out.println("Failed to make connection!");
                
            } catch (SQLException e) {
                System.out.println("SQL error: " + e.getMessage());
            }

        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading properties or driver: " + e.getMessage());
        }
    }

    private static HikariDataSource dataSource;
    private static int maxPool = 20;

    static {
        Properties properties = new Properties();
        try (InputStream input = App.class.getResourceAsStream("/application.properties")) {
            properties.load(input);
            String driver = properties.getProperty("db.driver");
            String url = properties.getProperty("db.url");
            String user = properties.getProperty("db.user");
            String password = properties.getProperty("db.password");

            HikariConfig config = new HikariConfig();
            config.setDriverClassName(driver);
            config.setJdbcUrl(url);
            config.setUsername(user);
            config.setPassword(password);
            config.setMaximumPoolSize(maxPool); 
            config.setIdleTimeout(60000);

            dataSource = new HikariDataSource(config);
        } catch (IOException e) {
            System.out.println("Error loading properties: " + e.getMessage());
        }
    }

    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    public static void close() {
        if (dataSource != null) 
            dataSource.close();
    }
    
    
    
}