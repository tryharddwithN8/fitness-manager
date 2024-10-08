package com.fitness.controller;

import com.fitness.utility.UtilityAlert;

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
    private void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

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
                stage.setTitle("Admin");

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

                stage.show();
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
            showAlert(Alert.AlertType.ERROR, "Lỗi đăng ký", "Vui lòng điền đầy đủ thông tin!");
        } else if (!password.equals(confirmPassword)) {
            showAlert(Alert.AlertType.ERROR, "Lỗi đăng ký", "Mật khẩu không khớp!");
        } else {
            showAlert(Alert.AlertType.INFORMATION, "Đăng ký thành công", "Chào mừng " + username + "!");
        }
    }

    @FXML
    private void handleForgotPassword() {
        String email = forgotEmailField.getText();

        if (email.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Lỗi", "Vui lòng nhập email!");
        } else {
            showAlert(Alert.AlertType.INFORMATION, "Yêu cầu đặt lại mật khẩu", "Liên kết khôi phục mật khẩu đã được gửi tới " + email);
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
