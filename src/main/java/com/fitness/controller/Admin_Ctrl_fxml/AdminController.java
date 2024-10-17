package com.fitness.controller.Admin_Ctrl_fxml;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import com.fitness.model.fitness.Course;
import com.fitness.utility.UtilityAlert;
import com.fitness.repositories.CourseRepositoryImpl;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.List;

/**
 * UserController
 */
public class AdminController {

    private CourseRepositoryImpl courseRepoImpl = new CourseRepositoryImpl();

    @FXML
    private Button btnSignout, btnOverview, btnCoach, btnCourse, btnExit, btnUser;
    
    @FXML
    private Pane pnlOverview, pnlCoach, pnlUser, pnlCourse;

    @FXML
    private TableView<Course> courseTable;
    @FXML
    private TableColumn<Course, String> courseIdColumn;
    @FXML
    private TableColumn<Course, String> nameColumn;
    @FXML
    private TableColumn<Course, String> descriptionColumn;
    @FXML
    private TableColumn<Course, String> coachIdColumn;
    @FXML
    private TableColumn<Course, String> scheduleColumn;
    @FXML
    private TableColumn<Course, Integer> maxParticipantsColumn;
    @FXML
    private TableColumn<Course, Integer> currentParticipantsColumn;
    @FXML
    private TableColumn<Course, Double> feeColumn;

    public AdminController() throws SQLException {
    }


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

    @FXML
    public void initialize() throws SQLException {
            courseIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
            nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
            descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
            coachIdColumn.setCellValueFactory(new PropertyValueFactory<>("coachId"));
            scheduleColumn.setCellValueFactory(new PropertyValueFactory<>("schedule"));
            maxParticipantsColumn.setCellValueFactory(new PropertyValueFactory<>("maxParticipants"));
            currentParticipantsColumn.setCellValueFactory(new PropertyValueFactory<>("currentParticipants"));
            feeColumn.setCellValueFactory(new PropertyValueFactory<>("fee"));
    }

    @FXML
    private void handleDisplayAll() throws SQLException {
        List<Course> courses = courseRepoImpl.getAll();
        if (courses == null) {
            System.out.println("No courses were found.");
        }
        ObservableList<Course> courseList = FXCollections.observableArrayList(courses);
        courseTable.setItems(courseList);
    }
    
}