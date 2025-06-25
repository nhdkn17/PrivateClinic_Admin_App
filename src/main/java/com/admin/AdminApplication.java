package com.admin;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class AdminApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(AdminApplication.class.getResource("AuthView.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1530, 787);
        stage.getIcons().add(new Image(getClass().getResource("/image/Clinic-logo.png").toString()));
        stage.setTitle("Đăng nhập Admin App");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
