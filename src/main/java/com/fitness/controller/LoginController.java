package com.fitness.controller;

import com.fitness.dto.EncryptAES;
import com.fitness.dto.EncryptRSA;
import com.fitness.utility.UtilityAlert;
import com.fitness.utility.UtilitySecurity;
import com.mysql.cj.util.Util;
import java.util.Base64;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class LoginController {

    @FXML
    private AnchorPane loginPane;
    @FXML
    private AnchorPane signUpPane;
    @FXML
    private AnchorPane forgotPasswordPane;

    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    
    @FXML
    private TextField signupUsernameField;
    @FXML
    private TextField emailField;
    @FXML
    private PasswordField signupPasswordField;
    @FXML
    private PasswordField confirmPasswordField;
    
    @FXML
    private TextField forgotEmailField;
    @FXML
    private Button loginButton;

    @FXML
    public void initialize() {
        showLoginForm();
    }

    private void showLoginForm() {
        loginPane.setVisible(true);
        signUpPane.setVisible(false);
        forgotPasswordPane.setVisible(false);
    }

    @FXML
    private void handleShowSignUp() {
        signUpPane.setVisible(true);
        loginPane.setVisible(false);
        forgotPasswordPane.setVisible(false);
    }

    @FXML
    private void handleShowForgotPassword() {
        forgotPasswordPane.setVisible(true);
        loginPane.setVisible(false);
        signUpPane.setVisible(false);
    }

    private double x, y;
    @FXML
    private void handleLogin() throws Exception {
        String username = usernameField.getText();
        String password = passwordField.getText();

        try {
            // code test here 
            String key = UtilitySecurity.genKey(32);
            EncryptAES encryptAES = new EncryptAES(key);

            String encryptedData = encryptAES.encryptData(username + "$" + password);
            String encryptedAESKey = EncryptRSA.encryptAESKey(Base64.getEncoder().encodeToString(key.getBytes("UTF-8")));

            EncryptRSA.sendData(encryptedAESKey,encryptedData);

        } catch (Exception e) {
            e.printStackTrace();
        }
        

        // Check account on SQL (placeholder for actual logic)
        if (username.equals("") && password.equals("")) {
            UtilityAlert.showInfo("Login Success", "Welcome back!");
            
            // Handle login main mode
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/Main.fxml"));
                Parent root = fxmlLoader.load();
                Stage stage = (Stage) loginButton.getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                // stage.setTitle("Admin");
                stage.show();

                // Center the window on the screen
                stage.setX((Screen.getPrimary().getVisualBounds().getWidth() - stage.getWidth()) / 2);
                stage.setY((Screen.getPrimary().getVisualBounds().getHeight() - stage.getHeight()) / 2);

                // Drag functionality
                root.setOnMousePressed(event -> {
                    x = event.getSceneX();
                    y = event.getSceneY();
                });
                root.setOnMouseDragged(event -> {
                    stage.setX(event.getScreenX() - x);
                    stage.setY(event.getScreenY() - y);
                });

                
            } catch (Exception e) {
                System.out.println("Error loading Main.fxml: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            UtilityAlert.showError(Alert.AlertType.ERROR, "Login Failed", "Invalid username or password!");
        }
    }


    @FXML
    private void handleSignUp() {
        String username = signupUsernameField.getText();
        String email = emailField.getText();
        String password = signupPasswordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        if (username.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            UtilityAlert.showError(Alert.AlertType.ERROR, "Lỗi đăng ký", "Vui lòng điền đầy đủ thông tin!");
        } else if (!password.equals(confirmPassword)) {
            UtilityAlert.showError(Alert.AlertType.ERROR, "Lỗi đăng ký", "Mật khẩu không khớp!");
        } else {
            UtilityAlert.showInfo("Đăng ký thành công", "Chào mừng " + username + "!");
        }
    }

    @FXML
    private void handleForgotPassword() {
        String email = forgotEmailField.getText();

        if (email.isEmpty()) 
            UtilityAlert.showError(Alert.AlertType.ERROR, "Lỗi", "Vui lòng nhập email!");
        else 
            UtilityAlert.showError(Alert.AlertType.INFORMATION, "Yêu cầu đặt lại mật khẩu", "Liên kết khôi phục mật khẩu đã được gửi tới " + email);
        
    }

}
