module com.admin {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires jbcrypt;
    requires com.google.gson;
    requires java.sql;
    requires annotations;
    requires jdk.httpserver;
    requires jakarta.mail;

    opens com.admin to javafx.fxml;
    opens com.admin.client.controller to javafx.fxml;
    opens com.admin.client.controller.other to javafx.fxml;
    opens com.admin.client.service to javafx.fxml;
    opens com.admin.server.network to com.google.gson;
    opens com.admin.shared.model to com.google.gson;
    opens com.admin.shared.protocol to com.google.gson;

    exports com.admin;
    exports com.admin.client.controller;
}