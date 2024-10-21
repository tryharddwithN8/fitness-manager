package com.fitness.controller.User_Ctrl_fxml;

import com.fitness.utility.UtilityAlert;
import com.fitness.utility.UtilityData;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.util.Duration;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

/**
 * pnlFeedbackController
 */
public class pnlFeedbackController {

    @FXML 
    private TextArea textArea_content_fb;
    @FXML
    private TextField textField_name_fb, textField_phone_fb, textField_donate_fb;
    @FXML
    private Button btn_clear_fb, btn_submit_fb,btn_vote1s,btn_vote2s,btn_vote3s,btn_vote4s,btn_vote5s;
    @FXML
    private Text text_waiting_fb;
    private String getrate;
    @FXML
    public void clearTextArea()
    {
        textArea_content_fb.setText("");
        textField_name_fb.setText("");
        textField_phone_fb.setText("");
        textField_donate_fb.setText("");
        resetButtonStyles();
    }

    @FXML
    public void sendFeedbackToServer(){
        String name = textField_name_fb.getText();
        String phone = textField_phone_fb.getText();
        String donate = textField_donate_fb.getText() + "$";
        String text = textArea_content_fb.getText();

        String rate = getrate;

        if (name == null || name.trim().isEmpty()) {
            UtilityAlert.showError(Alert.AlertType.ERROR, "Missing Information", "Please enter your name.");
            return;
        }
        if (phone == null || phone.trim().isEmpty()) {
            UtilityAlert.showError(Alert.AlertType.ERROR, "Missing Information", "Please enter your phone number.");
            return;
        }
        if (donate == null || donate.trim().isEmpty()) {
            UtilityAlert.showError(Alert.AlertType.ERROR, "Missing Information", "Please enter the amount you would like to donate.");
            return;
        }
        if (text == null || text.trim().isEmpty()) {
            UtilityAlert.showError(Alert.AlertType.ERROR, "Missing Information", "Please provide your feedback.");
            return;
        }

        Timeline timeline = new Timeline(
            new KeyFrame(Duration.seconds(0), e -> text_waiting_fb.setText("Waiting")),
            new KeyFrame(Duration.seconds(0.3), e -> text_waiting_fb.setText("Waiting.")),
            new KeyFrame(Duration.seconds(0.6), e -> text_waiting_fb.setText("Waiting..")),
            new KeyFrame(Duration.seconds(0.9), e -> text_waiting_fb.setText("Waiting..."))
        );
        timeline.setCycleCount(Timeline.INDEFINITE);  
        timeline.play(); 
        
        
        text_waiting_fb.setVisible(true);
        btn_submit_fb.setDisable(true);
        Task<Integer> task = new Task<Integer>() {
            @Override
            protected Integer call() throws Exception {
                return UtilityData.sendFeedBack(name, phone, donate, rate, text);
            }
        };
        task.setOnSucceeded(event -> {
            timeline.stop();
            text_waiting_fb.setVisible(false);
            btn_submit_fb.setDisable(false);
            int res = task.getValue();
            if(res == 1){
                UtilityAlert.showInfo("Send Successfully", "Your feedback has been sent successfully.\nThank you for your contribution!");
                return;
            }
            UtilityAlert.showError(Alert.AlertType.ERROR, "Send Failed", "There was an error sending your feedback. Please try again later.");
        });
        task.setOnFailed(event -> {
            timeline.stop();
            text_waiting_fb.setVisible(false);
            btn_submit_fb.setDisable(false);
            UtilityAlert.showError(Alert.AlertType.ERROR, "Send Failed", "There was an error sending your feedback. Please try again later.");
        });

        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
        
    }

    @FXML
    public void handleClicks(ActionEvent actionEvent) {
        if (actionEvent.getSource() == btn_vote1s) {
            resetButtonStyles();
            btn_vote1s.setStyle("-fx-background-color : #ffa6c6");
            getrate = "1/5";
        } else if (actionEvent.getSource() == btn_vote2s) {
            resetButtonStyles();
            btn_vote2s.setStyle("-fx-background-color : #ffa6c6");
            getrate = "2/5";
        } else if (actionEvent.getSource() == btn_vote3s) {
            resetButtonStyles();
            btn_vote3s.setStyle("-fx-background-color : #ffa6c6");
            getrate = "3/5";
        } else if (actionEvent.getSource() == btn_vote4s) {
            resetButtonStyles();
            btn_vote4s.setStyle("-fx-background-color : #ffa6c6");
            getrate = "4/5";
        } else if (actionEvent.getSource() == btn_vote5s) {
            resetButtonStyles();
            btn_vote5s.setStyle("-fx-background-color : #ffa6c6");
            getrate = "5/5";
        }
    }
    private void resetButtonStyles() {
        btn_vote1s.setStyle("");
        btn_vote2s.setStyle("");
        btn_vote3s.setStyle("");
        btn_vote4s.setStyle("");
        btn_vote5s.setStyle("");
    }
}