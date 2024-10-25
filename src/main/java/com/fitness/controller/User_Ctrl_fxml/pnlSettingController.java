package com.fitness.controller.User_Ctrl_fxml;

import com.fitness.model.person.User;
import com.fitness.services.UserServiceImpl;
import com.fitness.utility.UtilityAlert;
import com.fitness.utility.UtilityData;
import com.fitness.utility.UtilityIO;
import java.io.File;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * pnlSettingController
 */
public class pnlSettingController {

    @FXML
    private Label userIdProfile, errorName, errorDob, errorEmail, errorPhone, updateLabel;
    @FXML
    private TextField createDateUserProfile, day, month, year, fullNameUserProfile, usernameUserProfile, emailUserProfile, phoneUserProfile, addressUserProfile, roleUserProfile;

    @FXML
    private Button uploadCV;

    UserServiceImpl userService = new UserServiceImpl();
    UtilityIO validation = new UtilityIO();

    public void setUserService(UserServiceImpl userService) {
        this.userService = userService;
    }

    public void loadSettingProfile(User entity) {
        resetErrorLabel();
        userIdProfile.setText("ID: " + entity.getId());
        roleUserProfile.setText(entity.getRole());
        usernameUserProfile.setText(entity.getUsername());

        fullNameUserProfile.setText(entity.getFullName());
        if (entity.getDob() != null) {
            day.setText(String.valueOf(entity.getDob().getDayOfMonth()));
            month.setText(String.valueOf(entity.getDob().getMonthValue()));
            year.setText(String.valueOf(entity.getDob().getYear()));
        }
        emailUserProfile.setText(entity.getEmail());
        phoneUserProfile.setText(entity.getPhone());
        addressUserProfile.setText(entity.getAddress());
        createDateUserProfile.setText(entity.getCreateDate());

        // load all data
        // biến ID, username, role giữ nguyên

    }

    @FXML
    public void handleClicks() {
        resetErrorLabel();
        boolean check = true;
        String id = userIdProfile.getText();
        //Name
        String fullName = fullNameUserProfile.getText();
        if (!validation.checkRegex(fullName, "^^[A-ZÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚĂĐĨŨƠƯàáâãèéêìíòóôõùúăđĩũơưĂẰẮẲẴẶẲẤẦẨẪẬÒÓÔÕÙÚỲÝỶỸẤ][a-zàáâãèéêìíòóôõùúăđĩũơưăằắẳẵặầấẩẫậốồổỗộê]+(\\s[A-ZÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚĂĐĨŨƠƯàáâãèéêìíòóôõùúăđĩũơưĂẰẮẲẴẶẲẤẦẨẪẬÒÓÔÕÙÚỲÝỶỸẤ][a-zàáâãèéêìíòóôõùúăđĩũơưăằắẳẵặầấẩẫậốồổỗộê]+)*$")) {
            errorName.setText("Name must be capitalize the first letter ");
            check = false;
        }
        //Day of BirthS
        LocalDate dob = null;
        try {
            dob = LocalDate.parse(day.getText() + "/" + month.getText() + "/" + year.getText(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        } catch (Exception e) {
            errorDob.setText("Incorrect format: dd/MM/yyyy");
            check = false;
        }
        //Email
        String email = emailUserProfile.getText();
        if (!validation.checkRegex(email, "^[\\w._%+-]+@gmail\\.com$")) {
            errorEmail.setText("Email must be @gmail.com");
            check = false;
        }
        //Phone
        String phone = phoneUserProfile.getText();
        if (!validation.checkRegex(phone, "^0\\d{9}$")) {
            errorPhone.setText("Phone must be 10 digit, Start with 0");
            check = false;
        }
        //Address
        String address = addressUserProfile.getText();
        if (check == true) {
            User user = new User(id, fullName, dob, email, phone, address);
            try {
                userService.update(user);
                updateLabel.setStyle("-fx-text-fill: #12bf3d;");
                updateLabel.setText("Update successful !!!");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else {
            updateLabel.setStyle("-fx-text-fill: #ff0900;");
            updateLabel.setText("Update failed !!!");
        }
    }

    public void resetErrorLabel() {
        errorName.setText("");
        errorDob.setText("");
        errorEmail.setText("");
        errorPhone.setText("");
        updateLabel.setText("");

    }

    @FXML
    public void openFileChosser(){
        String email_user = emailUserProfile.getText();
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg")
        );
        Stage stage = (Stage) uploadCV.getScene().getWindow();
        File selectedFile = fileChooser.showOpenDialog(stage);
        
        if (selectedFile != null) {
            System.out.println("Selected file: " + selectedFile.getAbsolutePath());
            int res = UtilityAlert.showConfirm("Upload CV", "Your file choose: "+ selectedFile.getName());
            if(res==1){
                int send_status = UtilityData.sendImageWithUsername(selectedFile, email_user);
                if(send_status==1){
                    UtilityAlert.showInfo("Success", "Upload CV successfully !");
                    uploadCV.setDisable(true);
                    return;
                }
                UtilityAlert.showError(Alert.AlertType.ERROR, "Upload Failed", "Please try agains");

            }

        } else {
            UtilityAlert.showError(Alert.AlertType.ERROR, "Img failed", "Select file .png or .jpg");
            return;
        }
    }
}

