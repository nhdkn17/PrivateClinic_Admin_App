package com.admin.client.controller.other;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Notice {
    @FXML
    private AnchorPane anchorPane;

    @FXML
    private Label errorMessage;

    @FXML
    private Label errorTitle;

    @FXML
    private Button closeButton;

    @FXML
    public void closePane() {
        Stage stage = (Stage) anchorPane.getScene().getWindow();
        stage.close();
    }

    public void setNotice(String title, String message, boolean isSuccess) {
        errorTitle.setText(title);
        errorMessage.setText(message);
        if (isSuccess) {
            errorTitle.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #2e7d32;");
            errorMessage.setStyle("-fx-font-size: 14px; -fx-text-fill: #2e7d32;");
        } else {
            errorTitle.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #d32f2f;");
            errorMessage.setStyle("-fx-font-size: 14px; -fx-text-fill: #d32f2f;");
        }
    }
}
