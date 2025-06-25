package com.admin.client.controller;

import com.admin.shared.model.NhanVien;
import com.admin.server.utils.JDBCUtil;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.sql.*;

public class NhanVienController {

    @FXML private TableView<NhanVien> tblNhanVien;
    @FXML private TableColumn<NhanVien, String> colHoTen;
    @FXML private TableColumn<NhanVien, String> colSoDienThoai;
    @FXML private TableColumn<NhanVien, String> colEmail;

    @FXML private TextField txtHoTen;
    @FXML private TextField txtSoDienThoai;
    @FXML private TextField txtEmail;

    @FXML private Button btnThem;
    @FXML private Button btnCapNhat;
    @FXML private Button btnXoa;
    @FXML private Button btnLamMoi;

    private final ObservableList<NhanVien> nhanVienList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        colHoTen.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getHoTen()));
        colSoDienThoai.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getSoDienThoai()));
        colEmail.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getEmail()));

        tblNhanVien.setItems(nhanVienList);
        loadNhanVienData();

        tblNhanVien.setOnMouseClicked(this::handleTableClick);
    }

    private void loadNhanVienData() {
        nhanVienList.clear();

        String query = "SELECT * FROM NhanVien";
        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                NhanVien nv = new NhanVien(
                        rs.getInt("MaNhanVien"),
                        rs.getString("HoTen"),
                        rs.getString("SoDienThoai"),
                        rs.getString("Email")
                );
                nhanVienList.add(nv);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleThem() {
        String hoTen = txtHoTen.getText().trim();
        String soDT = txtSoDienThoai.getText().trim();
        String email = txtEmail.getText().trim();

        if (hoTen.isEmpty() || soDT.isEmpty() || email.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Lỗi", "Vui lòng điền đầy đủ thông tin.");
            return;
        }

        String insert = "INSERT INTO NhanVien (HoTen, SoDienThoai, Email) VALUES (?, ?, ?)";

        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(insert)) {

            stmt.setString(1, hoTen);
            stmt.setString(2, soDT);
            stmt.setString(3, email);

            stmt.executeUpdate();
            showAlert(Alert.AlertType.INFORMATION, "Thành công", "Đã thêm nhân viên.");
            loadNhanVienData();
            clearFields();

        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Lỗi", "Không thể thêm nhân viên.");
        }
    }

    @FXML
    private void handleCapNhat() {
        NhanVien selected = tblNhanVien.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert(Alert.AlertType.WARNING, "Thông báo", "Vui lòng chọn nhân viên cần cập nhật.");
            return;
        }

        String hoTen = txtHoTen.getText().trim();
        String soDT = txtSoDienThoai.getText().trim();
        String email = txtEmail.getText().trim();

        if (hoTen.isEmpty() || soDT.isEmpty() || email.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Lỗi", "Vui lòng điền đầy đủ thông tin.");
            return;
        }

        String update = "UPDATE NhanVien SET HoTen = ?, SoDienThoai = ?, Email = ? WHERE MaNhanVien = ?";

        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(update)) {

            stmt.setString(1, hoTen);
            stmt.setString(2, soDT);
            stmt.setString(3, email);
            stmt.setInt(4, selected.getMaNhanVien());

            stmt.executeUpdate();
            showAlert(Alert.AlertType.INFORMATION, "Thành công", "Cập nhật nhân viên thành công.");
            loadNhanVienData();
            clearFields();

        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Lỗi", "Không thể cập nhật nhân viên.");
        }
    }

    @FXML
    private void handleXoa() {
        NhanVien selected = tblNhanVien.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert(Alert.AlertType.WARNING, "Thông báo", "Vui lòng chọn nhân viên cần xóa.");
            return;
        }

        String delete = "DELETE FROM NhanVien WHERE MaNhanVien = ?";

        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(delete)) {

            stmt.setInt(1, selected.getMaNhanVien());
            stmt.executeUpdate();
            showAlert(Alert.AlertType.INFORMATION, "Thành công", "Xóa nhân viên thành công.");
            loadNhanVienData();
            clearFields();

        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Lỗi", "Không thể xóa nhân viên. Có thể đang được sử dụng.");
        }
    }

    @FXML
    private void handleLamMoi() {
        clearFields();
        tblNhanVien.getSelectionModel().clearSelection();
    }

    private void handleTableClick(MouseEvent event) {
        NhanVien selected = tblNhanVien.getSelectionModel().getSelectedItem();
        if (selected != null) {
            txtHoTen.setText(selected.getHoTen());
            txtSoDienThoai.setText(selected.getSoDienThoai());
            txtEmail.setText(selected.getEmail());
        }
    }

    private void clearFields() {
        txtHoTen.clear();
        txtSoDienThoai.clear();
        txtEmail.clear();
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

