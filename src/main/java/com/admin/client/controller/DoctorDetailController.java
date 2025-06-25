package com.admin.client.controller;

import com.admin.client.service.CL_DoctorService;
import com.admin.client.controller.other.Dialog;
import com.admin.shared.model.BacSi;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.util.Optional;

public class DoctorDetailController {
    @FXML
    private Label lblTen, lblChuyenKhoa, lblSDT, lblEmail;

    private Stage dialogStage;
    private BacSi currentBacSi;
    private DoctorController doctorController;

    public void setBacSi(BacSi bacSi) {
        this.currentBacSi = bacSi;

        lblTen.setText(bacSi.getTenBacSi());
        lblChuyenKhoa.setText("Chuyên khoa: " + bacSi.getChuyenKhoa());
        lblSDT.setText("SĐT: " + bacSi.getSoDienThoai());
        lblEmail.setText("Email: " + bacSi.getEmail());
    }

    public void setDialogStage(Stage stage) {
        this.dialogStage = stage;
    }

    @FXML
    private void handleEdit() {
        if (doctorController != null) {
            doctorController.loadBacSiForEdit(currentBacSi);
        }
        handleClose();
    }

    @FXML
    private void handleDelete() {
        if (currentBacSi == null) return;

        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle("Xác nhận xóa");
        confirmAlert.setHeaderText("Bạn có chắc chắn muốn xóa bác sĩ này?");
        confirmAlert.setContentText("Tên: " + currentBacSi.getTenBacSi());

        Optional<ButtonType> result = confirmAlert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                CL_DoctorService service = new CL_DoctorService();
                boolean success = service.deleteDoctor(currentBacSi.getMaBacSi());

                if (success) {
                    Dialog.showNotice("Thành công", "Xóa thành công!", true);
                    handleClose();
                    if (doctorController != null) {
                        doctorController.loadDanhSachBacSi();
                        doctorController.loadBieuDoSoBacSi();
                    }
                } else {
                    Dialog.showNotice("Lỗi", "Xóa thất bại!", false);
                }

            } catch (Exception e) {
                Dialog.showNotice("Lỗi", "Xóa thất bại!\n" + e.getMessage(), false);
            }
        }
    }

    @FXML
    private void handleClose() {
        dialogStage.close();
    }

    public void setDoctorController(DoctorController controller) {
        this.doctorController = controller;
    }

    public Stage getDialogStage() {
        return this.dialogStage;
    }
}
