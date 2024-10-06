package com.fitness.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

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

    @FXML
    private void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (username.equals("admin") && password.equals("password")) {
            showAlert(Alert.AlertType.INFORMATION, "Đăng nhập thành công", "Chào mừng " + username + "!");
        } else {
            showAlert(Alert.AlertType.ERROR, "Đăng nhập thất bại", "Sai tên người dùng hoặc mật khẩu!");
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
