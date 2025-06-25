package com.admin;

import com.admin.client.controller.other.Dialog;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class MainController {
    @FXML
    private AnchorPane mainContent;

    @FXML
    public void initialize() {
        loadDashboardContent();
    }

    @FXML
    private void loadDashboardContent() {
        loadFXMLContent("AdminDashboard.fxml");
    }

    @FXML
    private void loadDoctorContent() {
        loadFXMLContent("DoctorView.fxml");
    }

    @FXML
    private void loadEmployeeContent() {
        loadFXMLContent("NhanVienView.fxml");
    }

    @FXML
    private void loadWarehouseContent() {
        loadFXMLContent("WarehouseView.fxml");
    }

    private void loadFXMLContent(String fxmlFile) {
        try {
            mainContent.getChildren().clear();
            Node node = FXMLLoader.load(getClass().getResource(fxmlFile));
            mainContent.getChildren().add(node);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void actionLogout(ActionEvent event) {
        try {
            Stage currentStage = (Stage) mainContent.getScene().getWindow();
            currentStage.close();

            FXMLLoader loader = new FXMLLoader(AdminApplication.class.getResource("AuthView.fxml"));
            Scene loginScene = new Scene(loader.load());
            Stage loginStage = new Stage();
            loginStage.getIcons().add(new Image(getClass().getResource("/image/Clinic-logo.png").toString()));
            loginStage.setScene(loginScene);
            loginStage.setTitle("Đăng Nhập PC Admin");
            loginStage.show();
        } catch (Exception e) {
            Dialog.showNotice("Lỗi", "Không thể đăng xuất!", false);
            e.printStackTrace();
        }
    }
}