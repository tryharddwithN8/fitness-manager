package com.fitness.controller.User_Ctrl_fxml;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.io.IOException;

public class pnlOverViewController {
    @FXML
    private VBox pnItems = null;
    @FXML
    private VBox Vbox_ad = null;
    @FXML private Button btn_back_ad;
    @FXML private Button btn_next_ad;
    private ADController adController;
    private final String[] adFiles = {
            "/fxml/User_fxml/AD/ad1.fxml",
            "/fxml/User_fxml/AD/ad2.fxml",
            "/fxml/User_fxml/AD/ad3.fxml"
    };
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
    public void loadItem1(){
        Node[] nodes = new Node[9];
        int itemsPerRow = 3;
        HBox currentHBox = null;
        for (int i = 0; i < nodes.length; i++) {
            try {
                final int j = i;
                nodes[i] = FXMLLoader.load(getClass().getResource("/fxml/User_fxml/Item/itemOverView.fxml"));
                if (i % itemsPerRow == 0) {
                    currentHBox = new HBox();
                    currentHBox.setSpacing(10);
                    pnItems.getChildren().add(currentHBox);
                }
                if (currentHBox != null) {
                    currentHBox.getChildren().add(nodes[i]);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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
