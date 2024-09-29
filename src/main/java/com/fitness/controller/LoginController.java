
package com.fitness.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginButton;

    @FXML
    private void handleLogin(ActionEvent event) {
        try {

            // if(tr)
            // {
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Login Successful");
                alert.setHeaderText(null);
                alert.setContentText("Welcome! Login successful.");
                alert.showAndWait();
               
            // } else {
            //     System.out.println("Invalid login!");
            //     Alert alert = new Alert(AlertType.ERROR);
            //     alert.setTitle("Login Failed");
            //     alert.setHeaderText(null);
            //     alert.setContentText("Invalid username or password. Please try again.");
            //     alert.showAndWait();
            // }
             
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }

}
