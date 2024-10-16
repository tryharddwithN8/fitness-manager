package com.fitness.controller.Admin_Ctrl_fxml;

import com.fitness.utility.UtilityAlert;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * UserController
 */
public class AdminController {

    @FXML
    private Button btnSignout, btnOverview, btnCoach, btnCourse, btnExit, btnUser;
    
    @FXML
    private Pane pnlOverview, pnlCoach, pnlUser, pnlCourse;


    @FXML
    public void handleClicks(ActionEvent event)
    {
        resetButtonStyles();
        if(event.getSource() == btnOverview)
        {   
            btnOverview.setStyle("-fx-background-color : #1620A1");
            OverviewPanel();
            pnlOverview.toFront();
            btnOverview.setStyle("-fx-background-color : #fff");
        } 
        else if(event.getSource() == btnUser)
        {
            btnUser.setStyle("-fx-background-color : #1620A1");
            UserPanel();
            pnlUser.toFront();
            btnUser.setStyle("-fx-background-color : #fff");
        }
        else if(event.getSource() == btnCoach)
        {
            btnCoach.setStyle("-fx-background-color : #1620A1");
            CoachPanel();
            pnlCoach.toFront();
            btnCoach.setStyle("-fx-background-color : #fff");
        }
        else if(event.getSource() == btnCourse)
        {
            btnCourse.setStyle("-fx-background-color : #1620A1");
            CoursePanel();
            pnlCourse.toFront();
            btnCourse.setStyle("-fx-background-color : #fff");
        }
    }

    

    public void handleSignOut(){
        UtilityAlert.showConfirmSignOut("Sign Out", "Do you want to logout", (Stage)btnSignout.getScene().getWindow());
    }

    public void handleExit(){
        UtilityAlert.showConfimExit("Exit", "Do you want to exit :((");
    }


    private void OverviewPanel()
    {
        pnlUser.setVisible(false);
        pnlOverview.setVisible(true);
        pnlCoach.setVisible(false);
        pnlCourse.setVisible(false);
    }
    private void UserPanel()
    {
        pnlUser.setVisible(true);
        pnlOverview.setVisible(false);
        pnlCoach.setVisible(false);
        pnlCourse.setVisible(false);
    }
    private void CoachPanel()
    {
        pnlUser.setVisible(false);
        pnlOverview.setVisible(false);
        pnlCoach.setVisible(true);
        pnlCourse.setVisible(false);
    }
    private void CoursePanel()
    {
        pnlUser.setVisible(false);
        pnlOverview.setVisible(false);
        pnlCoach.setVisible(false);
        pnlCourse.setVisible(true);
    }



    private void resetButtonStyles() {
        btnOverview.setStyle("");
        btnCoach.setStyle("");
        btnCourse.setStyle("");
        btnExit.setStyle("");
        btnUser.setStyle("");
        btnSignout.setStyle("");
    }

    
}