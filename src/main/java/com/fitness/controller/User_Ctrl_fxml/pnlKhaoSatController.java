package com.fitness.controller.User_Ctrl_fxml;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class pnlKhaoSatController {

    // Khai báo các CheckBox ngày trong tuần
    @FXML
    private CheckBox mondayCheckBox;
    @FXML
    private CheckBox tuesdayCheckBox;
    @FXML
    private CheckBox wednesdayCheckBox;
    @FXML
    private CheckBox thursdayCheckBox;
    @FXML
    private CheckBox fridayCheckBox;
    @FXML
    private CheckBox saturdayCheckBox;
    @FXML
    private CheckBox sundayCheckBox;

    // Khai báo các CheckBox thời gian trong ngày
    @FXML
    private CheckBox morning;
    @FXML
    private CheckBox afternoon;
    @FXML
    private CheckBox evening;

    private Stage surveyStage = null;

    public void handleClick(ActionEvent actionEvent) {
        if (mondayCheckBox.isSelected()) {
            System.out.println("Đã chọn Thứ Hai");
        }
        if (tuesdayCheckBox.isSelected()) {
            System.out.println("Đã chọn Thứ Ba");
        }
        if (wednesdayCheckBox.isSelected()) {
            System.out.println("Đã chọn Thứ Tư");
        }
        if (thursdayCheckBox.isSelected()) {
            System.out.println("Đã chọn Thứ Năm");
        }
        if (fridayCheckBox.isSelected()) {
            System.out.println("Đã chọn Thứ Sáu");
        }
        if (saturdayCheckBox.isSelected()) {
            System.out.println("Đã chọn Thứ Bảy");
        }
        if (sundayCheckBox.isSelected()) {
            System.out.println("Đã chọn Chủ Nhật");
        }

        // Kiểm tra thời gian trong ngày được chọn
        if (morning.isSelected()) {
            System.out.println("Đã chọn Buổi Sáng");
        }
        if (afternoon.isSelected()) {
            System.out.println("Đã chọn Buổi Chiều");
        }
        if (evening.isSelected()) {
            System.out.println("Đã chọn Buổi Tối");
        }
    }


    @FXML
    public void showSurvey() {
        try {
            // Tải tệp FXML cho khảo sát
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/User_fxml/Main_Pane/pnlKhaoSat.fxml"));
            Parent surveyRoot = fxmlLoader.load();
            surveyStage = new Stage();
            // Tạo một Stage mới để hiển thị khảo sát
            surveyStage.setTitle("Khảo Sát");
            surveyStage.setScene(new Scene(surveyRoot, 1000, 600)); // Kích thước giao diện
            // Ngăn người dùng thay đổi kích thước cửa sổ
            surveyStage.setResizable(false);


            surveyStage.show(); // Hiển thị cửa sổ khảo sát
        } catch (Exception e) {
            e.printStackTrace(); // In lỗi ra nếu có vấn đề khi tải tệp FXML
        }
    }

}
