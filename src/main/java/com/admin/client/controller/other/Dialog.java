package com.admin.client.controller.other;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Dialog {
    public static void showNotice(String title, String message, boolean isSuccess) {
        try {
            FXMLLoader loader = new FXMLLoader(Dialog.class.getResource("/com/admin/Other/Alert.fxml"));
            Parent root = loader.load();
            Notice controller = loader.getController();
            controller.setNotice(title, message, isSuccess);
            Stage stage = new Stage();
            stage.getIcons().add(new Image(Dialog.class.getResource(isSuccess ? "/image/Clinic-logo.png" : "/image/alert_icon.png").toString()));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle(isSuccess ? "Thành Công" : "Lỗi");
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}