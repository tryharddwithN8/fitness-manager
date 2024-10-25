package com.fitness.controller.User_Ctrl_fxml;

import com.fitness.controller.LoginController;
import com.fitness.services.UserServiceImpl;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import com.fitness.utility.UtilityAlert;

public class UserController implements Initializable {
    @FXML
    private HBox hbox_mycourse = null;
    @FXML
    private VBox pnItems = null;
    @FXML
    private VBox Vbox_ad = null;
    @FXML
    private HBox pnItems2 = null;
    @FXML
    private Button btnOverview;
    @FXML
    private Button btnOrders;
    @FXML
    private Button btnCustomers;
    @FXML
    private Button btnMenus, btnFeedback;
    @FXML
    private Button btnSettings;
    @FXML
    private Button btnSignout;
    @FXML
    private Pane pnlMenus;
    @FXML
    private Pane pnlCustomer ;
    @FXML
    private Pane pnlOrders, pnlSetting, pnlFeedback;
    private Pane overviewPane;
    @FXML
    private StackPane stackPane_all = null;
    private final String[] adFiles = {
            "/fxml/User_fxml/AD/ad1.fxml",
            "/fxml/User_fxml/AD/ad2.fxml",
            "/fxml/User_fxml/AD/ad3.fxml"
    };
    private int currentAdIndex = 0;
    private ADController adController;

    //Khai Báo Controller
    LoginController loginController = new LoginController();
    pnlSettingController pnlSettingController ;
    pnlOrdersController pnlOrdersController;
    pnlOverViewController pnlOverViewController = new pnlOverViewController();

    //Khai Báo Service
    UserServiceImpl userService = new UserServiceImpl();

    public UserController() throws SQLException {
    }

    private ExecutorService executor = Executors.newFixedThreadPool(4); // 4 thread

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadPnlOverview();
        loadPnlSetting();
        loadPnlFeedBack();
        loadPnlMenus();
        loadPnlOder();
    }

    private void loadPnlOverview() {
        Task<Void> task = new Task<>() {
            @Override
            protected Void call() throws Exception {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/User_fxml/Main_Pane/pnlOverview.fxml"));
                    overviewPane = loader.load();
                    Platform.runLater(() -> {
                        pnlOverViewController = loader.getController();
                        pnlOverViewController.advertiseController();
                        pnlOverViewController.setPnlOrdersController(pnlOrdersController);
                        pnlOverViewController.loadItem1();
                        stackPane_all.getChildren().add(overviewPane);
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }
        };
        executor.submit(task);
    }
    private void loadPnlOder(){
        Task<Void> task = new Task<>() {
            @Override
            protected Void call() throws Exception {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/User_fxml/Main_Pane/pnlOrders.fxml"));
                    pnlOrders = loader.load();
                    Platform.runLater(() -> {
                        pnlOrdersController=loader.getController();
                        pnlOrdersController.loadOrder();
                        stackPane_all.getChildren().add(pnlOrders);
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }
        };
        executor.submit(task);
    }
    private void loadPnlMenus() {
        Task<Void> task = new Task<>() {
            @Override
            protected Void call() throws Exception {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/User_fxml/Main_Pane/pnlMenus.fxml"));
                    pnlMenus = loader.load();
                    Platform.runLater(() -> {
                        pnlMenusController MenusController = loader.getController();
                        MenusController.startClock();
                        MenusController.coachProfileManager();
                        MenusController.loadUserName();
                        pnlMenus.setVisible(false);
                        stackPane_all.getChildren().add(pnlMenus);
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }
        };
        executor.submit(task);
    }

    private void loadPnlSetting() {
        Task<Void> task = new Task<>() {
            @Override
            protected Void call() throws Exception {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/User_fxml/Main_Pane/pnlSetting.fxml"));
                    pnlSetting = loader.load();
                    Platform.runLater(() -> {
                        pnlSettingController = loader.getController();
                        pnlSettingController.setUserService(userService);
                        pnlSetting.setVisible(false);
                        stackPane_all.getChildren().add(pnlSetting);
                    });

                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }
        };
        executor.submit(task);
    }

    private void loadPnlFeedBack() {
        Task<Void> task = new Task<>() {
            @Override
            protected Void call() throws Exception {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/User_fxml/Main_Pane/pnlFeedback.fxml"));
                    pnlFeedback = loader.load();
                    Platform.runLater(() -> {
                        pnlFeedback.setVisible(false);
                        stackPane_all.getChildren().add(pnlFeedback);
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }
        };
        executor.submit(task);
    }

    public void shutdown() {
        executor.shutdown();
    }

    public void giohang() {
    }

    // pnlFeedback
    public void handleClicks(ActionEvent actionEvent) {
        resetButtonStyles();
        if (actionEvent.getSource() == btnOrders) {
            stackPane_all.setStyle("-fx-background-color : #e9e9e9");
            stackPane_all.getChildren().clear();
            stackPane_all.getChildren().add(pnlOrders);
            btnOrders.setStyle("-fx-background-color : #fff");
        } else if (actionEvent.getSource() == btnMenus) {
            stackPane_all.setStyle("-fx-background-color : #76ace3");
            stackPane_all.getChildren().clear();
            pnlMenus.setVisible(true);
            stackPane_all.getChildren().add(pnlMenus);
            btnMenus.setStyle("-fx-background-color : #fff");
        } else if (actionEvent.getSource() == btnOverview) {
            stackPane_all.setStyle("-fx-background-color : #e9e9e9");
            stackPane_all.getChildren().clear();
            stackPane_all.getChildren().add(overviewPane);
            btnOverview.setStyle("-fx-background-color : #fff");
        } else if (actionEvent.getSource() == btnSettings) {

            try {
                pnlSettingController.loadSettingProfile(userService.getByUserName(loginController.getNameUser()));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            stackPane_all.setStyle("-fx-background-color : #e9e9e9");
            stackPane_all.getChildren().clear();
            pnlSetting.setVisible(true);
            stackPane_all.getChildren().add(pnlSetting);
            btnSettings.setStyle("-fx-background-color : #fff");

        } else if (actionEvent.getSource() == btnFeedback) {
            stackPane_all.setStyle("-fx-background-color : #e9e9e9");
            stackPane_all.getChildren().clear();
            pnlFeedback.setVisible(true);
            stackPane_all.getChildren().add(pnlFeedback);
            btnFeedback.setStyle("-fx-background-color : #fff");
        }
    }

    private void resetButtonStyles() {
        btnOverview.setStyle("");
        btnMenus.setStyle("");
        btnOrders.setStyle("");
        btnSettings.setStyle("");
        btnSignout.setStyle("");
        btnFeedback.setStyle("");
    }
    /*
     * Exit App
     */


    public void handleExit() {
        UtilityAlert.showConfimExit("Exit", "Do you want to exit :(");
    }
    /*
     * return login page
     */
    public void handleSignOut(){
        UtilityAlert.showConfirmSignOut("Sign Out", "Do you want to logout", (Stage)btnSignout.getScene().getWindow());
    }
}