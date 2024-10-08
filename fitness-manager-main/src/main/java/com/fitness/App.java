package com.fitness;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application
{
    @Override
    public void start(Stage stage) throws Exception
    {
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/fxml/Login.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setTitle("Login");
            stage.setScene(scene);
            stage.show();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
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


    public static void main( String[] args )
    {
        // testDBConnection();
        launch(args);
    }
}
