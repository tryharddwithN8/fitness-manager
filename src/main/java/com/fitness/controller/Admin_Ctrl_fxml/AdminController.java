package com.fitness.controller.Admin_Ctrl_fxml;

import com.fitness.model.person.Coach;
import com.fitness.model.person.User;
import com.fitness.services.CoachServiceImpl;
import com.fitness.services.CourseServiceImpl;
import com.fitness.services.UserServiceImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.util.Duration;
import javafx.scene.control.*;
import com.fitness.model.fitness.Course;
import com.fitness.utility.UtilityAlert;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.List;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;




public class AdminController {

    final private CourseServiceImpl courseServiceImpl = new CourseServiceImpl();
    final private UserServiceImpl userServiceImpl = new UserServiceImpl();
    final private CoachServiceImpl coachServiceImpl = new CoachServiceImpl();

    @FXML
    private Button btnSignout, btnOverview, btnCoach, btnCourse, btnExit, btnUser, btnFindCoach, btnRemoveCoach, btnDisplayCoach, btnFindCourse, btnRemoveCourse, btnDisplayCourse, btnFindUser, btnRemoveUser, btnDisplayUser;
    @FXML
    private Pane pnlOverview, pnlCoach, pnlUser, pnlCourse;
    @FXML
    private TableView<Course> courseTable;
    @FXML
    private TableColumn<Course, String> courseIdColumn, nameColumn, descriptionColumn, coachIdColumn, scheduleColumn;
    @FXML
    private TableColumn<Course, Integer> maxParticipantsColumn, currentParticipantsColumn;
    @FXML
    private TableColumn<Course, Double> feeColumn;
    @FXML
    private TableView<Coach> coachTable;
    @FXML
    private TableColumn<Coach, String> coachIdCol, coachNameCol, coachUserNameCol, coachEmailCol, coachRoleCol, coachAddressCol;
    @FXML
    private TextField texRemoveCoach, textFindCoach, textFindCourse, textRemoveCourse, textFindUser, textRemoveUser;
    @FXML
    private ChoiceBox<String> choiceBoxSearch, choiceBoxSearchCourse, choiceBoxSearchUser;
    @FXML
    private TableView<User> userTable;
    @FXML
    private TableColumn<User, String> userIDCol, userNameCol, userUserNameCol, userEmailCol, userPhoneCol, userAddressCol;
    @FXML
    private Label lblWaitingFindCoach, lblWaitingFindCourse, lblWaitingRemoveCoach, lblWaitingRemoveCourse, lblWaitingDisplayCoach, lblWaitingDisplayCourse, lblWaitingFindUser, lblWaitingRemoveUser, lblWaitingDisplayUser, labelTotalCourses, labelTotalUsers, labelTotalCoachs;
    @FXML
    private LineChart<Number, Number> lineChart;
    @FXML
    private NumberAxis xAxis;
    @FXML
    private NumberAxis yAxis;

    @FXML
    private PieChart ratingPieChart;

    public void displayInfoOverview(){
        loadSeries(lineChart);
        loadPieChart();
        labelTotalUsers.setText(String.valueOf(userServiceImpl.getTotalUsers()));
        labelTotalCourses.setText(String.valueOf(courseServiceImpl.getTotalCourses()));
        labelTotalCoachs.setText(String.valueOf(coachServiceImpl.getTotalCoachs()));
    }

    private void loadSeries(XYChart<Number, Number> lineChart) {
        lineChart.getData().clear();

        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        series.setName("Sales Data");

        series.getData().add(new XYChart.Data<>(0, 0));   
        series.getData().add(new XYChart.Data<>(1, 50));
        series.getData().add(new XYChart.Data<>(3, 192)); 
        series.getData().add(new XYChart.Data<>(4, 129)); 
        series.getData().add(new XYChart.Data<>(6, 310)); 
        series.getData().add(new XYChart.Data<>(10, 242));

        lineChart.getData().add(series);

        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(2), lineChart);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);
        fadeTransition.play();
    }


    
    private void loadPieChart() {
        ratingPieChart.getData().clear();
    
        int oneStar = 20, twoStar = 30, threeStar = 50, fourStar = 400, fiveStar = 500;  
    
        int total = oneStar + twoStar + threeStar + fourStar + fiveStar;
    
        PieChart.Data oneStarData = new PieChart.Data("1⭐️", oneStar);
        PieChart.Data twoStarData = new PieChart.Data("2⭐️", twoStar);
        PieChart.Data threeStarData = new PieChart.Data("3⭐️", threeStar);
        PieChart.Data fourStarData = new PieChart.Data("4⭐️", fourStar);
        PieChart.Data fiveStarData = new PieChart.Data("5⭐️", fiveStar);
    
        ratingPieChart.getData().addAll(oneStarData, twoStarData, threeStarData, fourStarData, fiveStarData);
        ratingPieChart.setLegendVisible(false);
        ratingPieChart.setStartAngle(90);
        ratingPieChart.getData().forEach(data -> data.getNode().setOpacity(1));

        Timeline timeline = new Timeline();
        int delay = 0;

        for (PieChart.Data data : ratingPieChart.getData()) {
            KeyFrame keyFrameShow = new KeyFrame(
                Duration.millis(delay),
                new KeyValue(data.getNode().opacityProperty(), 1),
                new KeyValue(data.getNode().scaleXProperty(), 1.3), 
                new KeyValue(data.getNode().scaleYProperty(), 1.3)
            );
            KeyFrame keyFrameReturn = new KeyFrame(
                Duration.millis(delay + 500),
                new KeyValue(data.getNode().scaleXProperty(), 1),
                new KeyValue(data.getNode().scaleYProperty(), 1)
            );
            timeline.getKeyFrames().addAll(keyFrameShow, keyFrameReturn);
            delay += 555;
        }
        timeline.play(); 
    }


    @FXML
    public void handleClicks(ActionEvent event) {
        resetButtonStyles();
        if(event.getSource() == btnOverview) {
            btnOverview.setStyle("-fx-background-color : #1620A1");
            OverviewPanel();
            pnlOverview.toFront();
            btnOverview.setStyle("-fx-background-color : #fff");
        } else if(event.getSource() == btnUser) {
            btnUser.setStyle("-fx-background-color : #1620A1");
            UserPanel();
            pnlUser.toFront();
            btnUser.setStyle("-fx-background-color : #fff");
        } else if(event.getSource() == btnCoach) {
            btnCoach.setStyle("-fx-background-color : #1620A1");
            CoachPanel();
            pnlCoach.toFront();
            btnCoach.setStyle("-fx-background-color : #fff");
        } else if(event.getSource() == btnCourse) {
            btnCourse.setStyle("-fx-background-color : #1620A1");
            CoursePanel();
            pnlCourse.toFront();
            btnCourse.setStyle("-fx-background-color : #fff");
        }
    }

    public void handleSignOut() {
        UtilityAlert.showConfirmSignOut("Sign Out", "Do you want to logout", (Stage)btnSignout.getScene().getWindow());
    }

    public void handleExit() {
        UtilityAlert.showConfimExit("Exit", "Do you want to exit :((");
    }

    private void OverviewPanel() {
        pnlUser.setVisible(false);
        pnlOverview.setVisible(true);
        pnlCoach.setVisible(false);
        pnlCourse.setVisible(false);
        displayInfoOverview();
    }

    private void UserPanel() {
        pnlUser.setVisible(true);
        pnlOverview.setVisible(false);
        pnlCoach.setVisible(false);
        pnlCourse.setVisible(false);
    }

    private void CoachPanel() {
        pnlUser.setVisible(false);
        pnlOverview.setVisible(false);
        pnlCoach.setVisible(true);
        pnlCourse.setVisible(false);
    }

    private void CoursePanel() {
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
        OverviewPanel();
        loadDataFromDB();
    }

    public void loadDataFromDB() throws SQLException {
        handleDisplayAllCourse();
        handleDisplayAllUsers();
        handleDisplayAllCoach();
    }

    @FXML
    private void handleDisplayAllUsers() throws SQLException {
        lblWaitingDisplayUser.setVisible(true);
        btnFindUser.setDisable(true);
        btnDisplayUser.setDisable(true);
        btnRemoveUser.setDisable(true);

        Task<List<User>> task = new Task<List<User>>() {
            @Override
            protected List<User> call() throws SQLException {
                return userServiceImpl.getAll();
            }
        };

        task.setOnSucceeded(event -> {
            lblWaitingDisplayUser.setVisible(false);
            btnFindUser.setDisable(false);
            btnDisplayUser.setDisable(false);
            btnRemoveUser.setDisable(false);

            List<User> users = null;
            try {
                users = userServiceImpl.getAll();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            if (users == null) {
                UtilityAlert.showError(Alert.AlertType.ERROR, "Update Failed", "Failed rồi\nKhông biết lỗi gì :((");
                textFindUser.setText("");
                return;
            }
            ObservableList<User> userList = FXCollections.observableArrayList(users);

            userIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
            userNameCol.setCellValueFactory(new PropertyValueFactory<>("fullName"));
            userUserNameCol.setCellValueFactory(new PropertyValueFactory<>("username"));
            userEmailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
            userPhoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
            userAddressCol.setCellValueFactory(new PropertyValueFactory<>("address"));

            userTable.setItems(userList);
        });

        task.setOnFailed(event -> {
            lblWaitingDisplayUser.setVisible(false);
            btnFindUser.setDisable(false);
            btnDisplayUser.setDisable(false);
            btnRemoveUser.setDisable(false);
            UtilityAlert.showError(Alert.AlertType.ERROR, "Errorr", "Please try agains!");
        });

        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
    }

    @FXML
    private void handleRemoveUser() throws SQLException {
        if (textRemoveUser.getText().isEmpty()) {
            UtilityAlert.showError(Alert.AlertType.ERROR, "Remove User Failed", "Please enter user ID to remove");
            return;
        }
        if (textRemoveUser.getText().equals("admin")){
            UtilityAlert.showError(Alert.AlertType.ERROR, "Permission Denied", "Cannot remove admin user");
            return;
        }
        User selectedUser = userServiceImpl.getById(Integer.parseInt(textRemoveUser.getText()));
        if (selectedUser == null) {
            UtilityAlert.showError(Alert.AlertType.ERROR, "Remove User Failed", "User not found");
            return;
        }else {
            int choice = UtilityAlert.showConfirm("Remove User", "Do you want to remove this user?");
            if (choice == 1) {
                lblWaitingRemoveUser.setVisible(true);
                btnFindUser.setDisable(true);
                btnDisplayUser.setDisable(true);
                btnRemoveUser.setDisable(true);

                Task<Boolean> task = new Task<Boolean>() {
                    @Override
                    protected Boolean call() throws SQLException {
                        return userServiceImpl.delete(Integer.parseInt(textRemoveUser.getText()));
                    }
                };

                task.setOnSucceeded(event -> {
                    lblWaitingRemoveUser.setVisible(false);
                    btnFindUser.setDisable(false);
                    btnDisplayUser.setDisable(false);
                    btnRemoveUser.setDisable(false);

                    if (task.getValue()) {
                        UtilityAlert.showInfo("Remove User", "Remove user success");
                        try {
                            handleDisplayAllUsers();
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                    }else {
                        UtilityAlert.showError(Alert.AlertType.ERROR, "Remove User", "Remove user failed");
                    }
                });

                task.setOnFailed(event -> {
                    lblWaitingRemoveUser.setVisible(false);
                    btnFindUser.setDisable(false);
                    btnDisplayUser.setDisable(false);
                    btnRemoveUser.setDisable(false);
                    UtilityAlert.showError(Alert.AlertType.ERROR, "Error", "Please try again");
                });

                Thread thread = new Thread(task);
                thread.setDaemon(true);
                thread.start();
            }else {
                UtilityAlert.showInfo("Remove User", "Remove user canceled");
            }
        }
    }

    @FXML
    private void handleDisplayUser() throws SQLException {
        if (choiceBoxSearchUser.getValue() == null || textFindUser.getText().isEmpty()) {
            UtilityAlert.showError(Alert.AlertType.ERROR, "Search User Failed", "Please enter search key and select search column");
            return;
        }
        String col = choiceBoxSearchUser.getValue().toLowerCase();
        if (col.equals("id")) {
            try {
                Integer.parseInt(textFindUser.getText());
            } catch (NumberFormatException e) {
                UtilityAlert.showError(Alert.AlertType.ERROR, "Search User Failed", "ID must be a number");
                return;
            }
        }else {
            textFindUser.setText(textFindUser.getText().toLowerCase());
        }
        String key = textFindUser.getText();

        lblWaitingFindUser.setVisible(true);
        btnFindUser.setDisable(true);
        btnDisplayUser.setDisable(true);
        btnRemoveUser.setDisable(true);

        Task<List<User>> task = new Task<>() {
            @Override
            protected List<User> call() throws SQLException {
                return userServiceImpl.display(col, key);
            }
        };

        task.setOnSucceeded(event -> {
            lblWaitingFindUser.setVisible(false);
            btnFindUser.setDisable(false);
            btnDisplayUser.setDisable(false);
            btnRemoveUser.setDisable(false);

            List<User> users = null;
            try {
                users = userServiceImpl.display(col, key);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            if (users == null) {
                UtilityAlert.showError(Alert.AlertType.ERROR, "Update Failed", "Failed rồi\nKhông biết lỗi gì :((");
                textFindUser.setText("");
                return;
            }
            ObservableList<User> userList = FXCollections.observableArrayList(users);

            userIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
            userNameCol.setCellValueFactory(new PropertyValueFactory<>("fullName"));
            userUserNameCol.setCellValueFactory(new PropertyValueFactory<>("username"));
            userEmailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
            userPhoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
            userAddressCol.setCellValueFactory(new PropertyValueFactory<>("address"));

            userTable.setItems(userList);
        });

        task.setOnFailed(event -> {
            lblWaitingFindUser.setVisible(false);
            btnFindUser.setDisable(false);
            btnDisplayUser.setDisable(false);
            btnRemoveUser.setDisable(false);
            UtilityAlert.showError(Alert.AlertType.ERROR, "Errorr", "Please try agains!");
        });

        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
    }

    @FXML
    private void handleDisplayAllCourse() throws SQLException {
        lblWaitingDisplayCourse.setVisible(true);
        btnFindCourse.setDisable(true);
        btnDisplayCourse.setDisable(true);
        btnRemoveCourse.setDisable(true);

        Task<List<Course>> task = new Task<List<Course>>() {
            @Override
            protected List<Course> call() throws SQLException {
                return courseServiceImpl.getAll();
            }
        };

        task.setOnSucceeded(event -> {
            lblWaitingDisplayCourse.setVisible(false);
            btnFindCourse.setDisable(false);
            btnDisplayCourse.setDisable(false);
            btnRemoveCourse.setDisable(false);

            List<Course> courses = null;
            try {
                courses = courseServiceImpl.getAll();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            if (courses == null) {
                UtilityAlert.showError(Alert.AlertType.ERROR, "Update Failed", "Failed rồi\nKhông biết lỗi gì :((");
                textFindCourse.setText("");
                return;
            }
            ObservableList<Course> courseList = FXCollections.observableArrayList(courses);

            courseIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
            nameColumn.setCellValueFactory(new PropertyValueFactory<>("coachName"));
            descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
            coachIdColumn.setCellValueFactory(new PropertyValueFactory<>("coachId"));
            scheduleColumn.setCellValueFactory(new PropertyValueFactory<>("schedule"));
            maxParticipantsColumn.setCellValueFactory(new PropertyValueFactory<>("maxParticipants"));
            currentParticipantsColumn.setCellValueFactory(new PropertyValueFactory<>("currentParticipants"));
            feeColumn.setCellValueFactory(new PropertyValueFactory<>("fee"));

            courseTable.setItems(courseList);
        });

        task.setOnFailed(event -> {
            lblWaitingDisplayCourse.setVisible(false);
            btnFindCourse.setDisable(false);
            btnDisplayCourse.setDisable(false);
            btnRemoveCourse.setDisable(false);
            UtilityAlert.showError(Alert.AlertType.ERROR, "Errorr", "Please try agains!");
        });

        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
    }

    @FXML
    private void handleRemoveCourse() throws SQLException, NumberFormatException {
        if (textRemoveCourse.getText().isEmpty()) {
            UtilityAlert.showError(Alert.AlertType.ERROR, "Remove Course Failed", "Please enter course ID to remove");
            return;
        }
        Course selectedCourse = courseServiceImpl.getById(Integer.parseInt(textRemoveCourse.getText()));
        if (selectedCourse == null) {
            UtilityAlert.showError(Alert.AlertType.ERROR, "Remove Course Failed", "Course not found");
            return;
        }else {
            int choice = UtilityAlert.showConfirm("Remove Course", "Do you want to remove this course?");
            if (choice == 1) {
                lblWaitingRemoveCoach.setVisible(true);
                btnFindCoach.setDisable(true);
                btnDisplayCoach.setDisable(true);
                btnRemoveCoach.setDisable(true);

                Task<Boolean> task = new Task<Boolean>() {
                    @Override
                    protected Boolean call() throws SQLException {
                        return courseServiceImpl.delete(Integer.parseInt(textRemoveCourse.getText()));
                    }
                };

                task.setOnSucceeded(event -> {
                    lblWaitingRemoveCoach.setVisible(false);
                    btnFindCoach.setDisable(false);
                    btnDisplayCoach.setDisable(false);
                    btnRemoveCoach.setDisable(false);

                    if (task.getValue()) {
                        UtilityAlert.showInfo("Remove Course", "Remove course success");
                        try {
                            handleDisplayAllCourse();
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                    }else {
                        UtilityAlert.showError(Alert.AlertType.ERROR, "Remove Course", "Remove course failed");
                    }
                });

                task.setOnFailed(event -> {
                    lblWaitingRemoveCoach.setVisible(false);
                    btnFindCoach.setDisable(false);
                    btnDisplayCoach.setDisable(false);
                    btnRemoveCoach.setDisable(false);
                    UtilityAlert.showError(Alert.AlertType.ERROR, "Error", "Please try again");
                });

                Thread thread = new Thread(task);
                thread.setDaemon(true);
                thread.start();
            }else {
                UtilityAlert.showInfo("Remove Course", "Remove course canceled");
            }
        }
    }

    @FXML
    private void handleDisplayCourse() throws SQLException {
        if (choiceBoxSearchCourse.getValue() == null || textFindCourse.getText().isEmpty()) {
            UtilityAlert.showError(Alert.AlertType.ERROR, "Search Course Failed", "Please enter search key and select search column");
            return;
        }
        String col = choiceBoxSearchCourse.getValue().toLowerCase();
        if (col.equals("id")) {
            try {
                Integer.parseInt(textFindCourse.getText());
            } catch (NumberFormatException e) {
                UtilityAlert.showError(Alert.AlertType.ERROR, "Search Course Failed", "ID must be a number");
                return;
            }
        }else {
            textFindCourse.setText(textFindCourse.getText().toLowerCase());
        }
        String key = textFindCourse.getText();

        lblWaitingFindCourse.setVisible(true);
        btnFindCoach.setDisable(true);
        btnDisplayCoach.setDisable(true);
        btnRemoveCoach.setDisable(true);

        Task<List<Course>> task = new Task<List<Course>>() {
            @Override
            protected List<Course> call() throws SQLException {
                return courseServiceImpl.displayCourses(col, key);
            }
        };

        task.setOnSucceeded(event -> {
            lblWaitingFindCourse.setVisible(false);
            btnFindCoach.setDisable(false);
            btnDisplayCoach.setDisable(false);
            btnRemoveCoach.setDisable(false);

            List<Course> courses = null;
            try {
                courses = courseServiceImpl.displayCourses(col, key);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            if (courses == null) {
                UtilityAlert.showError(Alert.AlertType.ERROR, "Update Failed", "Failed rồi\nKhông biết lỗi gì :((");
                textFindCourse.setText("");
                return;
            }
            ObservableList<Course> courseList = FXCollections.observableArrayList(courses);

            courseIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
            nameColumn.setCellValueFactory(new PropertyValueFactory<>("coachName"));
            descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
            coachIdColumn.setCellValueFactory(new PropertyValueFactory<>("coachId"));
            scheduleColumn.setCellValueFactory(new PropertyValueFactory<>("schedule"));
            maxParticipantsColumn.setCellValueFactory(new PropertyValueFactory<>("maxParticipants"));
            currentParticipantsColumn.setCellValueFactory(new PropertyValueFactory<>("currentParticipants"));
            feeColumn.setCellValueFactory(new PropertyValueFactory<>("fee"));

            courseTable.setItems(courseList);
        });

        task.setOnFailed(event -> {
            lblWaitingFindCourse.setVisible(false);
            btnFindCoach.setDisable(false);
            btnDisplayCoach.setDisable(false);
            btnRemoveCoach.setDisable(false);
            UtilityAlert.showError(Alert.AlertType.ERROR, "Errorr", "Please try agains!");
        });

        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
    }

    @FXML
    private void handleDisplayAllCoach() throws SQLException {
        lblWaitingDisplayCoach.setVisible(true);
        btnFindCoach.setDisable(true);
        btnDisplayCoach.setDisable(true);
        btnRemoveCoach.setDisable(true);

        Task<List<Coach>> task = new Task<List<Coach>>() {
            @Override
            protected List<Coach> call() throws SQLException {
                return coachServiceImpl.getAll();
            }
        };

        task.setOnSucceeded(event -> {
            lblWaitingDisplayCoach.setVisible(false);
            btnFindCoach.setDisable(false);
            btnDisplayCoach.setDisable(false);
            btnRemoveCoach.setDisable(false);

            List<Coach> coaches = null;
            try {
                coaches = coachServiceImpl.getAll();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            if (coaches == null) {
                UtilityAlert.showError(Alert.AlertType.ERROR, "Update Failed", "Failed rồi\nKhông biết lỗi gì :((");
                textFindCoach.setText("");
                return;
            }
            ObservableList<Coach> coachList = FXCollections.observableArrayList(coaches);

            coachIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
            coachNameCol.setCellValueFactory(new PropertyValueFactory<>("fullName"));
            coachUserNameCol.setCellValueFactory(new PropertyValueFactory<>("username"));
            coachEmailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
            coachRoleCol.setCellValueFactory(new PropertyValueFactory<>("role"));
            coachAddressCol.setCellValueFactory(new PropertyValueFactory<>("address"));

            coachTable.setItems(coachList);
        });

        task.setOnFailed(event -> {
            lblWaitingDisplayCoach.setVisible(false);
            btnFindCoach.setDisable(false);
            btnDisplayCoach.setDisable(false);
            btnRemoveCoach.setDisable(false);
            UtilityAlert.showError(Alert.AlertType.ERROR, "Errorr", "Please try agains!");
        });

        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
    }

    @FXML
    private void handleRemoveCoach() throws SQLException, NumberFormatException {
        if (texRemoveCoach.getText().isEmpty()) {
            UtilityAlert.showError(Alert.AlertType.ERROR, "Remove Coach Failed", "Please enter coach ID to remove");
            return;
        }
        Coach selectedCoach = coachServiceImpl.getById(Integer.parseInt(texRemoveCoach.getText()));
        if (selectedCoach == null) {
            UtilityAlert.showError(Alert.AlertType.ERROR, "Remove Coach Failed", "Coach not found");
            return;
        }else {
            int choice = UtilityAlert.showConfirm("Remove Coach", "Do you want to remove this coach?");
            if (choice == 1) {
                lblWaitingRemoveCoach.setVisible(true);
                btnFindCoach.setDisable(true);
                btnDisplayCoach.setDisable(true);
                btnRemoveCoach.setDisable(true);

                Task<Boolean> task = new Task<Boolean>() {
                    @Override
                    protected Boolean call() throws SQLException {
                        return coachServiceImpl.delete(Integer.parseInt(texRemoveCoach.getText()));
                    }
                };

                task.setOnSucceeded(event -> {
                    lblWaitingRemoveCoach.setVisible(false);
                    btnFindCoach.setDisable(false);
                    btnDisplayCoach.setDisable(false);
                    btnRemoveCoach.setDisable(false);

                    if (task.getValue()) {
                        UtilityAlert.showInfo("Remove Coach", "Remove coach success");
                        try {
                            handleDisplayAllCoach();
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                    }else {
                        UtilityAlert.showError(Alert.AlertType.ERROR, "Remove Coach", "Remove coach failed");
                    }
                });

                task.setOnFailed(event -> {
                    lblWaitingRemoveCoach.setVisible(false);
                    btnFindCoach.setDisable(false);
                    btnDisplayCoach.setDisable(false);
                    btnRemoveCoach.setDisable(false);
                    UtilityAlert.showError(Alert.AlertType.ERROR, "Error", "Please try again");
                });

                Thread thread = new Thread(task);
                thread.setDaemon(true);
                thread.start();
            }else {
                UtilityAlert.showInfo("Remove Coach", "Remove coach canceled");
            }
        }
    }

    @FXML
    private void handleDisplayCoach() throws SQLException{
        if (choiceBoxSearch.getValue() == null || textFindCoach.getText().isEmpty()) {
            UtilityAlert.showError(Alert.AlertType.ERROR, "Search Coach Failed", "Please enter search key and select search column");
            return;
        }
        String col = choiceBoxSearch.getValue().toLowerCase();
        if (col.equals("id")) {
            try {
                Integer.parseInt(textFindCoach.getText());
            } catch (NumberFormatException e) {
                UtilityAlert.showError(Alert.AlertType.ERROR, "Search Coach Failed", "ID must be a number");
                return;
            }
        }else {
            textFindCoach.setText(textFindCoach.getText().toLowerCase());
        }
        String key = textFindCoach.getText();

        lblWaitingFindCoach.setVisible(true);
        btnFindCoach.setDisable(true);
        btnDisplayCoach.setDisable(true);
        btnRemoveCoach.setDisable(true);

        Task<List<Coach>> task = new Task<>() {
            @Override
            protected List<Coach> call() throws SQLException {
                return coachServiceImpl.displayCoaches(col, key);
            }
        };

        task.setOnSucceeded(event -> {
            lblWaitingFindCoach.setVisible(false);
            btnFindCoach.setDisable(false);
            btnDisplayCoach.setDisable(false);
            btnRemoveCoach.setDisable(false);

            List<Coach> coaches = null;
            try {
                coaches = coachServiceImpl.displayCoaches(col, key);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            if (coaches == null) {
                UtilityAlert.showError(Alert.AlertType.ERROR, "Update Failed", "Failed rồi\nKhông biết lỗi gì :((");
                textFindCoach.setText("");
                return;
            }
            ObservableList<Coach> coachList = FXCollections.observableArrayList(coaches);

            coachIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
            coachNameCol.setCellValueFactory(new PropertyValueFactory<>("fullName"));
            coachUserNameCol.setCellValueFactory(new PropertyValueFactory<>("username"));
            coachEmailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
            coachRoleCol.setCellValueFactory(new PropertyValueFactory<>("role"));
            coachAddressCol.setCellValueFactory(new PropertyValueFactory<>("address"));

            coachTable.setItems(coachList);
        });

        task.setOnFailed(event -> {
            lblWaitingFindCoach.setVisible(false);
            btnFindCoach.setDisable(false);
            btnDisplayCoach.setDisable(false);
            btnRemoveCoach.setDisable(false);
            UtilityAlert.showError(Alert.AlertType.ERROR, "Errorr", "Please try agains!");
        });

        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
    }
}