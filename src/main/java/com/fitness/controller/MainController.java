package com.fitness.controller;

import com.fitness.controller.User_Ctrl_fxml.ADController;
import com.fitness.controller.User_Ctrl_fxml.pnlMenusController;
import com.fitness.controller.User_Ctrl_fxml.pnlOverViewController;
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

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.fitness.utility.UtilityAlert;

public class MainController implements Initializable {
    @FXML
    private HBox hbox_mycourse=null;
    @FXML
    private VBox pnItems = null;
    @FXML
    private VBox Vbox_ad=null;
    @FXML
    private HBox pnItems2=null;
    @FXML
    private Button btnOverview;

    @FXML
    private Button btnOrders;

    @FXML
    private Button btnCustomers;

    @FXML
    private Button btnMenus;

    @FXML
    private Button btnPackages;

    @FXML
    private Button btnSettings;

    @FXML
    private Button btnSignout;
    @FXML private Pane pnlMenus;
    @FXML
    private Pane pnlCustomer;

    @FXML
    private Pane pnlOrders;
    private Pane overviewPane;
    private Pane menusPane;
    @FXML private StackPane stackPane_all=null;
    private final String[] adFiles = {
            "/fxml/User_fxml/AD/ad1.fxml",
            "/fxml/User_fxml/AD/ad2.fxml",
            "/fxml/User_fxml/AD/ad3.fxml"
    };
    private int currentAdIndex = 0;
    private ADController adController;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadPnlMenus();
        loadPnlOverview();
    }
    @FXML
    private void loadPnlOverview() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/User_fxml/Main Pane/pnlOverview.fxml"));
            overviewPane = loader.load(); // Gán vào biến toàn cục
            pnlOverViewController overViewController = loader.getController();
            overViewController.advertiseController();
            overViewController.loadItem1();
            stackPane_all.getChildren().add(overviewPane); // Thêm overviewPane vào stackPane_all
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadPnlMenus(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/User_fxml/Main Pane/pnlMenus.fxml"));
            menusPane=loader.load();
            pnlMenusController MenusController=loader.getController();
            MenusController.startClock();
            MenusController.coachProifileManager();
            stackPane_all.getChildren().add(menusPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void handleClicks(ActionEvent actionEvent) {
        resetButtonStyles();

        if (actionEvent.getSource() == btnCustomers) {
            pnlCustomer.setStyle("-fx-background-color : #1620A1");
            pnlCustomer.toFront();
            btnCustomers.setStyle("-fx-background-color : #fff");

        } else if (actionEvent.getSource() == btnMenus) {
            stackPane_all.setStyle("-fx-background-color :  #76ace3");
            stackPane_all.getChildren().clear();
            stackPane_all.getChildren().add(menusPane);
            btnMenus.setStyle("-fx-background-color : #fff");

        } else if (actionEvent.getSource() == btnOverview) {
            stackPane_all.setStyle("-fx-background-color :  #e9e9e9");
            stackPane_all.getChildren().clear();
            stackPane_all.getChildren().add(overviewPane);
            btnOverview.setStyle("-fx-background-color : #fff");

        } else if (actionEvent.getSource() == btnOrders) {
            pnlOrders.setStyle("-fx-background-color : #464F67");
            pnlOrders.toFront();
            btnOrders.setStyle("-fx-background-color : #fff");
        }
    }


    private void resetButtonStyles() {
        btnOverview.setStyle("");
        btnCustomers.setStyle("");
        btnMenus.setStyle("");
        btnOrders.setStyle("");
        btnPackages.setStyle("");
        btnSettings.setStyle("");
        btnSignout.setStyle("");
    }
    /*
     * Exit App
     */
    public void handleExit(){
        UtilityAlert.showConfimExit("Exit", "Do you want to exit :((");
    }
    /*
     * return login page
     */
    public void handleSignOut(){
        UtilityAlert.showConfirmSignOut("Sign Out", "Do you want to logout", (Stage)btnSignout.getScene().getWindow());
    }
}