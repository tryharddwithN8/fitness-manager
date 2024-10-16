package com.fitness.controller.User_Ctrl_fxml;

import javafx.animation.FadeTransition;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.io.IOException;

public class ADController {

    private final Pane advertisePane;
    public final String[] adFiles;
    public int currentAdIndex = 0;
    public ADController(Pane advertisePane, String[] adFiles) {
        this.advertisePane = advertisePane;
        this.adFiles = adFiles;
    }
    public void loadAdWithAnimation() {
        try {
            FadeTransition fadeOut = new FadeTransition(Duration.seconds(1), advertisePane);
            fadeOut.setFromValue(1.0);
            fadeOut.setToValue(0.0);
            fadeOut.setOnFinished(event -> {
                try {
                    Pane adPane = FXMLLoader.load(getClass().getResource(adFiles[currentAdIndex]));
                    advertisePane.getChildren().setAll(adPane.getChildren());
                    FadeTransition fadeIn = new FadeTransition(Duration.seconds(1), advertisePane);
                    fadeIn.setFromValue(0.0);
                    fadeIn.setToValue(1.0);
                    fadeIn.play();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            fadeOut.play();
            currentAdIndex = (currentAdIndex + 1) % adFiles.length;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void loadAdByIndex(int index) {
        currentAdIndex = index;
        loadAdWithAnimation();
    }

}
