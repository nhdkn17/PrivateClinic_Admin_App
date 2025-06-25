package com.admin.client.controller;

import com.admin.client.controller.other.Dialog;
import com.admin.shared.model.BacSi;
import com.admin.client.service.CL_DoctorService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.controlsfx.control.textfield.TextFields;

import java.io.IOException;
import java.util.*;

public class DoctorController {
    @FXML private FlowPane flowCardBacSi;
    @FXML private TextField txtTenBacSi, txtChuyenKhoa, txtSoDienThoai, txtEmail, txtTimKiem;
    @FXML private Pane paneBieuDo;

    private List<BacSi> danhSachBacSi = new ArrayList<>();
    private BacSi currentEditingBacSi;
    private final CL_DoctorService doctorService = new CL_DoctorService();

    @FXML
    public void initialize() throws IOException {
        loadDanhSachBacSi();
        setupAutoCompleteChuyenKhoa();
        loadBieuDoSoBacSi();
    }

    private void setupAutoCompleteChuyenKhoa() {
        List<String> dsChuyenKhoa = List.of(
                "Nội khoa", "Ngoại khoa", "Sản phụ khoa", "Nhi khoa",
                "Tai mũi họng", "Răng hàm mặt", "Da liễu", "Tim mạch",
                "Ngoại thần kinh", "Tâm thần", "Mắt", "Nội tiết",
                "Hồi sức cấp cứu", "Phục hồi chức năng"
        );
        TextFields.bindAutoCompletion(txtChuyenKhoa, dsChuyenKhoa);
    }

    @FXML
    private void handleThem() throws IOException {
        String ten = txtTenBacSi.getText().trim();
        String chuyenKhoa = txtChuyenKhoa.getText().trim();
        String sdt = txtSoDienThoai.getText().trim();
        String email = txtEmail.getText().trim();

        if (ten.isEmpty()) {
            Dialog.showNotice("Lỗi", "Tên bác sĩ không được để trống", false);
            return;
        }

        BacSi bacSiMoi = new BacSi(ten, chuyenKhoa, sdt, email);
        boolean success = doctorService.addDoctor(bacSiMoi);

        if (success) {
            Dialog.showNotice("Thành công", "Thêm bác sĩ thành công!", true);
            loadDanhSachBacSi();
            clearForm();
            loadBieuDoSoBacSi();
        } else {
            Dialog.showNotice("Lỗi", "Thêm bác sĩ thất bại!", false);
        }
    }

    @FXML
    private void handleSua() throws IOException {
        if (currentEditingBacSi == null) {
            Dialog.showNotice("Lỗi", "Chưa chọn bác sĩ để sửa", false);
            return;
        }

        currentEditingBacSi.setTenBacSi(txtTenBacSi.getText().trim());
        currentEditingBacSi.setChuyenKhoa(txtChuyenKhoa.getText().trim());
        currentEditingBacSi.setSoDienThoai(txtSoDienThoai.getText().trim());
        currentEditingBacSi.setEmail(txtEmail.getText().trim());

        boolean success = doctorService.updateDoctor(currentEditingBacSi);

        if (success) {
            Dialog.showNotice("Thành công", "Cập nhật bác sĩ thành công!", true);
            loadDanhSachBacSi();
            clearForm();
            currentEditingBacSi = null;
            loadBieuDoSoBacSi();
        } else {
            Dialog.showNotice("Lỗi", "Cập nhật bác sĩ thất bại!", false);
        }
    }

    @FXML
    private void handleTimKiem() throws IOException {
        String keyword = txtTimKiem.getText().trim().toLowerCase();
        if (keyword.isEmpty()) {
            loadDanhSachBacSi();
            return;
        }

        List<BacSi> filteredList = new ArrayList<>();
        for (BacSi bacSi : danhSachBacSi) {
            if (bacSi.getTenBacSi().toLowerCase().contains(keyword) ||
                    bacSi.getChuyenKhoa().toLowerCase().contains(keyword)) {
                filteredList.add(bacSi);
            }
        }
        hienThiDanhSachBacSi(filteredList);
    }

    @FXML
    private void clearForm() {
        txtTenBacSi.clear();
        txtChuyenKhoa.clear();
        txtSoDienThoai.clear();
        txtEmail.clear();
    }

    public void loadDanhSachBacSi() throws IOException {
        List<BacSi> list = doctorService.getAllDoctors();
        if (list != null) {
            danhSachBacSi = list;
            hienThiDanhSachBacSi(list);
        } else {
            Dialog.showNotice("Lỗi", "Không thể tải danh sách bác sĩ", false);
        }
    }

    private void hienThiDanhSachBacSi(List<BacSi> list) {
        flowCardBacSi.getChildren().clear();
        for (BacSi bacSi : list) {
            VBox card = taoCardBacSi(bacSi);
            flowCardBacSi.getChildren().add(card);
        }
    }

    public void loadBacSiForEdit(BacSi bacSi) {
        this.currentEditingBacSi = bacSi;
        txtTenBacSi.setText(bacSi.getTenBacSi());
        txtChuyenKhoa.setText(bacSi.getChuyenKhoa());
        txtSoDienThoai.setText(bacSi.getSoDienThoai());
        txtEmail.setText(bacSi.getEmail());
    }

    private VBox taoCardBacSi(BacSi bacSi) {
        VBox card = new VBox(5);
        card.setStyle("-fx-background-color: #c1f7cf; -fx-padding: 10px; -fx-border-radius: 10px; -fx-background-radius: 10px; -fx-cursor: hand;");
        card.setPrefWidth(250);

        Label lblTen = new Label("👨‍⚕️ " + bacSi.getTenBacSi());
        lblTen.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        Label lblChuyenKhoa = new Label("🩺 " + bacSi.getChuyenKhoa());
        Label lblSDT = new Label("📞 " + bacSi.getSoDienThoai());

        card.getChildren().addAll(lblTen, lblChuyenKhoa, lblSDT);
        card.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                hienThiThongTinChiTiet(bacSi);
            }
        });

        return card;
    }

    private void hienThiThongTinChiTiet(BacSi bacSi) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/admin/DoctorDetailView.fxml"));
            Parent root = loader.load();
            DoctorDetailController controller = loader.getController();

            controller.setDoctorController(this);
            controller.setDialogStage(new Stage());
            controller.setBacSi(bacSi);

            Stage stage = controller.getDialogStage();
            stage.setTitle("Thông tin chi tiết bác sĩ");
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadBieuDoSoBacSi() throws IOException {
        Map<String, Integer> data = doctorService.getStats();
        if (data == null || data.isEmpty()) return;

        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Khoa");
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Số bác sĩ");

        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
        barChart.setTitle("Số bác sĩ theo chuyên khoa");
        barChart.setPrefSize(paneBieuDo.getPrefWidth(), paneBieuDo.getPrefHeight());

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Bác sĩ");

        for (Map.Entry<String, Integer> entry : data.entrySet()) {
            series.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue()));
        }

        barChart.getData().add(series);
        paneBieuDo.getChildren().clear();
        paneBieuDo.getChildren().add(barChart);
    }
}
