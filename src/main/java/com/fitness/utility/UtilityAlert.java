package com.fitness.utility;

import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.stage.Modality;
import javafx.stage.Stage;



/**
 * Alert
 */
public class UtilityAlert {

    private static final String iconSuccessNotiPath = "/img/tick2.png";

    /*
     * show info Success Alert
     */
    public static void showInfo(String title, String message) {
        Alert alertShowInfo = new Alert(Alert.AlertType.INFORMATION);
        addDialogIconTo(alertShowInfo, iconSuccessNotiPath, message);
        alertShowInfo.setTitle(title);
        alertShowInfo.setHeaderText(null);
        alertShowInfo.setGraphic(null);

        alertShowInfo.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        alertShowInfo.getDialogPane().setMinWidth(300);
        alertShowInfo.getDialogPane().setMaxHeight(200);

        alertShowInfo.initModality(Modality.APPLICATION_MODAL);
        alertShowInfo.getDialogPane().getStylesheets().add(UtilityAlert.class.getResource("/css/alert.css").toExternalForm());
        alertShowInfo.showAndWait();
    }
    public static void addDialogIconTo(Alert alert, String path, String message) {
        final Image APPLICATION_ICON = new Image(path);
        Stage dialogStage = (Stage) alert.getDialogPane().getScene().getWindow();
        dialogStage.getIcons().add(APPLICATION_ICON);

        final ImageView DIALOG_HEADER_ICON = new ImageView(path);
        DIALOG_HEADER_ICON.setFitHeight(48);
        DIALOG_HEADER_ICON.setFitWidth(48);

        Label messageLabel = new Label(message);
        HBox hBox = new HBox(30); 
        hBox.setAlignment(Pos.CENTER_LEFT);
        HBox.setHgrow(messageLabel, Priority.ALWAYS);
        messageLabel.setWrapText(true);
        hBox.getChildren().addAll(DIALOG_HEADER_ICON, messageLabel);
        alert.getDialogPane().setContent(hBox);
    }


    /*
     * show failed alert
     */
    public static void showError(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}