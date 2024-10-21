package com.fitness.controller.User_Ctrl_fxml;

import com.fitness.model.fitness.Course;
import com.fitness.repositories.CourseRepositoryImpl;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Duration;


import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class pnlOverViewController {
    @FXML
    private VBox pnItems = null;
    @FXML
    private VBox Vbox_ad = null;
    @FXML private Button btn_back_ad;
    @FXML private Button btn_next_ad,btn_gioHang;
    private ADController adController;
    @FXML private Label label_itemOverviewName;
    private final String[] adFiles = {
            "/fxml/User_fxml/AD/ad1.fxml",
            "/fxml/User_fxml/AD/ad2.fxml",
            "/fxml/User_fxml/AD/ad3.fxml"
    };
    private CourseRepositoryImpl courseRepoImpl = new CourseRepositoryImpl();
    List<Course> courses = courseRepoImpl.getAll();
    public pnlOverViewController() throws SQLException {
    }

    public void advertiseController(){
        adController = new ADController(Vbox_ad, adFiles);
        adController.loadAdWithAnimation();
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(10), event -> {
            adController.loadAdWithAnimation();
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

        try {
            Pane adPane = FXMLLoader.load(getClass().getResource("/fxml/User_fxml/AD/ad1.fxml"));
            Vbox_ad.getChildren().setAll(adPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
        btn_back_ad.setOnAction(event -> showPreviousAd());
        btn_next_ad.setOnAction(event -> showNextAd());
    }

    public void loadItem1() {
        List<Node> nodes = new ArrayList<>();
        int itemsPerRow = 3;
        HBox currentHBox = null;
        int i = 0;
        for (Course course : courses) {
            try {
                Node node = FXMLLoader.load(getClass().getResource("/fxml/User_fxml/Item/itemOverView.fxml"));
                nodes.add(node);
                if (i % itemsPerRow == 0) {
                    currentHBox = new HBox();
                    currentHBox.setSpacing(10);
                    pnItems.getChildren().add(currentHBox);
                }
                if (currentHBox != null) {
                    currentHBox.getChildren().add(node);
                    Label label_itemOverview = (Label) node.lookup("#label_itemOverview");
                    Text price1=(Text) node.lookup("#price1");
                    Text price2=(Text) node.lookup("#price2");
                    String price= String.valueOf(course.getFee());
                    String Price2=String.valueOf(course.getDiscount());
                    label_itemOverview.setText(course.getName());
                    price1.setText(price);
                    price2.setText(Price2);
                }
                i++;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public boolean giohang(ActionEvent actionEvent){
        if (actionEvent.getSource() == btn_gioHang) {
            return true;
        }
        return false;
    }

    private void showPreviousAd() {
        int previousIndex = (adController.currentAdIndex - 1 + adController.adFiles.length) % adController.adFiles.length;
        adController.loadAdByIndex(previousIndex);
    }
    private void showNextAd() {
        int nextIndex = (adController.currentAdIndex + 1) % adController.adFiles.length;
        adController.loadAdByIndex(nextIndex);
    }

}
