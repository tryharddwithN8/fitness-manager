package com.fitness.controller;

import com.fitness.dto.Encrypt;
import com.fitness.model.person.User;
import com.fitness.services.UserServiceImpl;
import com.fitness.utility.UtilityAlert;
import com.fitness.utility.UtilitySecurity;
import com.mysql.cj.exceptions.ExceptionInterceptorChain;

import java.sql.SQLException;

import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
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
    private AnchorPane keyPanel;
    @FXML
    private AnchorPane updatePassPanel;

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
    private Text waitting_status_login;
    @FXML
    private Text waitting_status_signup;
    @FXML
    private Text waitting_status_forgot;
    @FXML
    private Text waitting_status_forgot_update_pass;

    @FXML
    private TextField key_user;
    
    @FXML
    private TextField passwd;
    @FXML
    private TextField confirm_passwd;

    private String key = null;

    @FXML
    public void initialize() {
        showLoginForm();
    }

    private UserServiceImpl userSerImpl = new UserServiceImpl();

    private void showLoginForm() {
        loginPane.setVisible(true);
        signUpPane.setVisible(false);
        forgotPasswordPane.setVisible(false);
        keyPanel.setVisible(false);
        updatePassPanel.setVisible(false);
    }

    @FXML
    private void handleShowSignUp() {
        signUpPane.setVisible(true);
        loginPane.setVisible(false);
        forgotPasswordPane.setVisible(false);
        keyPanel.setVisible(false);
        updatePassPanel.setVisible(false);
    }

    @FXML
    private void handleShowForgotPassword() {
        forgotPasswordPane.setVisible(true);
        loginPane.setVisible(false);
        signUpPane.setVisible(false);
        keyPanel.setVisible(false);
        updatePassPanel.setVisible(false);
    }

    @FXML
    private void handleKeyForgotPass(){
        forgotPasswordPane.setVisible(false);
        loginPane.setVisible(false);
        signUpPane.setVisible(false);
        keyPanel.setVisible(true);
        updatePassPanel.setVisible(false);
    }

    @FXML
    private void showUpdatePasswdForm(){
        forgotPasswordPane.setVisible(false);
        loginPane.setVisible(false);
        signUpPane.setVisible(false);
        keyPanel.setVisible(false);
        updatePassPanel.setVisible(true);
    }

    

    private double x, y;
    @FXML
    private void handleLogin() throws Exception {
        String username = usernameField.getText();
        String password = Encrypt.hash(passwordField.getText());

        
        /*
         * Stop feautuer
         *   try {
         *       EncryptRSA.sendData(username,password);
         *   
         *  } catch (Exception e) {
         *       e.printStackTrace();
         *   }
         */
        if (username.isEmpty() || password == null || password.trim().isEmpty()){
            UtilityAlert.showError(Alert.AlertType.ERROR, "Login Error", "Please fill out all the fields!");
            return;
        }

        waitting_status_login.setVisible(true);
        Task<Integer> task = new Task<Integer>() {
            @Override
            protected Integer call() throws Exception{
                return userSerImpl.loginAuth(username, password);
            }
        };
        
        task.setOnSucceeded(event -> {
            waitting_status_login.setVisible(false);
            int check = task.getValue();
            if(check==1){
                UtilityAlert.showInfo("Login Success", "Welcome " + username + "!");
                loginHandler();
            } else{
                UtilityAlert.showError(Alert.AlertType.ERROR, "Login failed", "Username of password incorrect");
                usernameField.setText("");
                passwordField.setText("");
            }
        });
        task.setOnFailed(event -> {
            waitting_status_login.setVisible(false);
            UtilityAlert.showError(Alert.AlertType.ERROR, "Login Error", "An error occurred during login (501)");
        });

        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
    }


    @FXML
    private void handleSignUp() throws SQLException{
        String username = signupUsernameField.getText();
        String email = emailField.getText();
        String password = Encrypt.hash(signupPasswordField.getText().trim());
        String confirmPassword = Encrypt.hash(confirmPasswordField.getText().trim());
        
        System.out.printf("User: %s\nemail: %s\npassword: %s\n\n", username, email, password);
        if (username.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) 
            UtilityAlert.showError(Alert.AlertType.ERROR, "Registration Error", "Please fill out all the fields!");
        else if (!password.equals(confirmPassword)) 
            UtilityAlert.showError(Alert.AlertType.ERROR, "Registration Error", "Passwords do not match!");
    
        
        else 
        {
            waitting_status_signup.setVisible(true);
            Task<Integer> task = new Task<Integer>() {
                @Override
                protected Integer call() throws Exception {
                    User newUser = new User("1", username, password, email, "user", "Uknow");
                    return userSerImpl.add(newUser);
                }
            };

            task.setOnSucceeded(event -> {
                waitting_status_signup.setVisible(false);
                int check = task.getValue();
                if (check == 1) {
                    UtilityAlert.showInfo("Registration Successful", "Welcome " + username + "!");
                    loginHandler();
                } else if (check == 0) {
                    UtilityAlert.showError(Alert.AlertType.ERROR, "SignUp Failed", "Account already exists");
                    signupPasswordField.setText("");
                    confirmPasswordField.setText("");
                } else {
                    UtilityAlert.showError(Alert.AlertType.ERROR, "Registration Error", "Registration failed");
                    signupPasswordField.setText("");
                    confirmPasswordField.setText("");
                }
            });

            task.setOnFailed(event -> {
                waitting_status_signup.setVisible(false);
                UtilityAlert.showError(Alert.AlertType.ERROR, "Registration Error", "An error occurred during registration");
            });

            Thread thread = new Thread(task);
            thread.setDaemon(true);
            thread.start();
        }
    }

    @FXML
    private void handleForgotPassword() {
        String email = forgotEmailField.getText();

        if (email.isEmpty())
        {
            UtilityAlert.showError(Alert.AlertType.ERROR, "Errorr", "Please try agains!");
            forgotEmailField.setText("");
            return;
        }

        waitting_status_forgot.setVisible(true);
        Task<Integer> task = new Task<Integer>() {
            @Override
            protected Integer call() throws Exception{
                return userSerImpl.getEmailAccount(email);
            }
        };
        task.setOnSucceeded(event -> {
            waitting_status_forgot.setVisible(false);
            int check = task.getValue();
            if(check != 1){
                UtilityAlert.showError(Alert.AlertType.ERROR, "Forgot Password Failed", "Email not found\nPlease try agains!");
                forgotEmailField.setText("");    
                return;
            }

            UtilityAlert.showError(Alert.AlertType.INFORMATION, "Yêu cầu đặt lại mật khẩu", "Liên kết khôi phục mật khẩu đã được gửi tới " + email);
            try {
                this.key = UtilitySecurity.getKeyFromServer(email);
            } catch (Exception e) {    
                e.printStackTrace();
            }
            handleKeyForgotPass();

        });

        task.setOnFailed(event -> {
            waitting_status_login.setVisible(false);
            UtilityAlert.showError(Alert.AlertType.ERROR, "Errorr", "Please try agains!");
        });
        
        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
    }

    @FXML
    public void handleVerifyKey(){
        String key_client = key_user.getText();
        if(key_client.equals(key))
        {
            showUpdatePasswdForm();
        }
        else
            UtilityAlert.showError(Alert.AlertType.ERROR, "Errorr", "Key incorrect!");

    }
    @FXML
    public void handleUpdatePasswd(){
        String email = forgotEmailField.getText();
        String pass = Encrypt.hash(passwd.getText().trim());
        String conf_pass = Encrypt.hash(confirm_passwd.getText().trim());

        if (pass == null || pass.isEmpty() || conf_pass == null || conf_pass.isEmpty())
        {
            UtilityAlert.showError(Alert.AlertType.ERROR, "Errorr", "Điền cho ra trò!");
            passwd.setText("");
            confirm_passwd.setText("");
            return;
        }
        if(!pass.equals(conf_pass)){
            UtilityAlert.showError(Alert.AlertType.ERROR, "Errorr", "Đíu giống nhau!\n Nhập lại đi");
            passwd.setText("");
            confirm_passwd.setText("");
            return;
        }

        waitting_status_forgot_update_pass.setVisible(true);
        Task<Integer> task = new Task<Integer>() {
            @Override
            protected Integer call() throws Exception{
                return userSerImpl.updatePasswd(email, pass);
            }
        };
        task.setOnSucceeded(event -> {
            waitting_status_forgot_update_pass.setVisible(false);
            int check = task.getValue();
            if(check != 1){
                UtilityAlert.showError(Alert.AlertType.ERROR, "Update Failed", "Failed rồi\nKhông biết lỗi gì :((");
                passwd.setText("");
                confirm_passwd.setText(""); 
                return;
            }
            loginHandler();

        });

        task.setOnFailed(event -> {
            waitting_status_forgot_update_pass.setVisible(false);
            UtilityAlert.showError(Alert.AlertType.ERROR, "Errorr", "Please try agains!");
        });
        
        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
    }

    private void loginHandler(){
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
    }
    

}
