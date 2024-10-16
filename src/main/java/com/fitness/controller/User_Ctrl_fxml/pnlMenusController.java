package com.fitness.controller.User_Ctrl_fxml;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javafx.scene.image.Image;
public class pnlMenusController {
    @FXML private Label time_myplan;
    @FXML private HBox hbox_mycourse = null;
    @FXML private Pane pane_coachProfile;
    @FXML private Pane layer_coachProfile;
    @FXML private Pane myCourseItem=null;
    @FXML private Pane pnlMenus;
    @FXML private Circle circleAva;
    public void loadMyCourses() {
        Node[] nodes = new Node[3];
        for (int i = 0; i < nodes.length; i++) {
            try {
                final int j = i;
                nodes[i] = FXMLLoader.load(getClass().getResource("/fxml/User_fxml/Item/itemMyCourse.fxml"));
                nodes[i].setOnMouseClicked(event -> {
                    pane_coachProfile.setVisible(true);
                    event.consume();
                });
                hbox_mycourse.getChildren().add(nodes[i]);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void startClock() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            String currentDateTime = LocalDateTime.now()
                    .format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
            time_myplan.setText(currentDateTime);
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }
    public void loadCoachAva(){
        Image image = new Image(getClass().getResource("/img/coach1.png").toString());
        circleAva.setFill(new ImagePattern(image));
        pane_coachProfile.setVisible(false);
    }

    public void coachProifileManager(){
        loadMyCourses();
        loadCoachAva();
        pnlMenus.setOnMouseClicked(event -> {
            if (!hbox_mycourse.isHover()) {
                pane_coachProfile.setVisible(false);
            }
        });
    }
}
