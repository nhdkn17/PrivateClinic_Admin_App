package com.admin.client.controller;

import com.admin.client.service.CL_WarehouseService;
import com.admin.client.controller.other.Dialog;
import com.admin.shared.model.Thuoc;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WarehouseController {
    @FXML
    private FlowPane cardContainer;
    @FXML
    private TextField txtSearch;
    @FXML
    private Label lblTongSoLuong;
    @FXML
    private Button btnAdd, btnEdit, btnDelete, btnRefresh;
    @FXML
    private ToggleButton btnLowStock;
    @FXML
    private TextField txtTenThuoc;
    @FXML
    private TextField txtLoaiThuoc;
    @FXML
    private TextField txtDonVi;
    @FXML
    private TextField txtGia;
    @FXML
    private TextField txtSoLuongTon;

    private final CL_WarehouseService warehouseService = new CL_WarehouseService();
    private List<Thuoc> currentList;
    private Thuoc selectedThuoc = null;

    @FXML
    public void initialize() {
        try {
            currentList = warehouseService.findAll();
            if (currentList == null) currentList = new ArrayList<>();
            loadMedicineCards(currentList);
            updateTotalLabel();
        } catch (Exception e) {
            Dialog.showNotice("Lỗi", "Không thể tải dữ liệu thuốc từ server", false);
        }
    }

    public void loadMedicineCards(List<Thuoc> danhSachThuoc) {
        cardContainer.getChildren().clear();
        if (danhSachThuoc == null) return;
        for (Thuoc thuoc : danhSachThuoc) {
            HBox card = createMedicineCard(thuoc);
            cardContainer.getChildren().add(card);
        }
    }

    private HBox createMedicineCard(Thuoc thuoc) {
        HBox card = new HBox(10);
        card.setStyle("-fx-background-color: #d5f2f7; -fx-border-color: #ccc; -fx-border-radius: 6; -fx-background-radius: 6; -fx-padding: 10");
        card.setPrefWidth(204);
        card.setMinHeight(70);

        Label nameLabel = new Label(thuoc.getTenThuoc());
        nameLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");
        Label quantityLabel = new Label("Số lượng tồn: " + thuoc.getSoLuongTon());

        VBox info = new VBox(5);
        info.getChildren().addAll(nameLabel, quantityLabel);

        Button btnSelect = new Button("Chi tiết");
        btnSelect.setOnAction(e -> showDetails(thuoc));

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        card.getChildren().addAll(info, spacer, btnSelect);
        return card;
    }

    private void showDetails(Thuoc thuoc) {
        selectedThuoc = thuoc;
        txtTenThuoc.setText(thuoc.getTenThuoc());
        txtLoaiThuoc.setText(thuoc.getLoaiThuoc());
        txtDonVi.setText(thuoc.getDonVi());
        txtGia.setText(String.valueOf(thuoc.getGia()));
        txtSoLuongTon.setText(String.valueOf(thuoc.getSoLuongTon()));
    }

    private void updateTotalLabel() {
        int total = currentList.stream().mapToInt(Thuoc::getSoLuongTon).sum();
        lblTongSoLuong.setText("Tổng số lượng: " + total);
    }

    @FXML
    public void onSearch() {
        String keyword = txtSearch.getText().trim().toLowerCase();

        if (keyword.isEmpty()) {
            loadMedicineCards(currentList);
            return;
        }

        List<Thuoc> filteredList = new ArrayList<>();
        for (Thuoc thuoc : currentList) {
            if (thuoc.getTenThuoc().toLowerCase().contains(keyword) ||
                    thuoc.getLoaiThuoc().toLowerCase().contains(keyword)) {
                filteredList.add(thuoc);
            }
        }
        loadMedicineCards(filteredList);
    }

    @FXML
    public void onRefresh() {
        try {
            currentList = warehouseService.findAll();
            if (currentList == null) currentList = new ArrayList<>();
            loadMedicineCards(currentList);
            updateTotalLabel();
            clearDetails();
        } catch (Exception e) {
            Dialog.showNotice("Lỗi", "Không thể làm mới dữ liệu kho", false);
        }
    }

    private void clearDetails() {
        selectedThuoc = null;
        txtTenThuoc.clear();
        txtLoaiThuoc.clear();
        txtDonVi.clear();
        txtGia.clear();
        txtSoLuongTon.clear();
    }

    @FXML
    public void onFilterLowStock() throws IOException {
        if (btnLowStock.isSelected()) {
            currentList = warehouseService.getThuocSapHet();
        } else {
            currentList = warehouseService.findAll();
        }
        loadMedicineCards(currentList);
        updateTotalLabel();
    }

    @FXML
    public void onAdd() {
        try {
            String ten = txtTenThuoc.getText().trim();
            String loai = txtLoaiThuoc.getText().trim();
            String donVi = txtDonVi.getText().trim();
            double gia = Double.parseDouble(txtGia.getText().trim());
            int soLuong = Integer.parseInt(txtSoLuongTon.getText().trim());

            if (ten.isEmpty() || loai.isEmpty() || donVi.isEmpty()) {
                Dialog.showNotice("Lỗi", "Vui lòng nhập đầy đủ thông tin thuốc.", false);
                return;
            }

            Thuoc newThuoc = new Thuoc();
            newThuoc.setTenThuoc(ten);
            newThuoc.setLoaiThuoc(loai);
            newThuoc.setDonVi(donVi);
            newThuoc.setGia(gia);
            newThuoc.setSoLuongTon(soLuong);

            boolean added = warehouseService.addThuoc(newThuoc);
            if (added) {
                onRefresh();
                Dialog.showNotice("Thành công", "Đã thêm thành công!", true);
            } else {
                Dialog.showNotice("Lỗi", "Không thể thêm thuốc!", false);
            }
        } catch (NumberFormatException e) {
            Dialog.showNotice("Lỗi", "Giá hoặc số lượng tồn không hợp lệ!", false);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void onEdit() {
        if (selectedThuoc == null) {
            Dialog.showNotice("Lỗi", "Vui lòng chọn thuốc để sửa!", false);
            return;
        }

        try {
            String ten = txtTenThuoc.getText().trim();
            String loai = txtLoaiThuoc.getText().trim();
            String donVi = txtDonVi.getText().trim();
            double gia = Double.parseDouble(txtGia.getText().trim());
            int soLuong = Integer.parseInt(txtSoLuongTon.getText().trim());

            if (ten.isEmpty() || loai.isEmpty() || donVi.isEmpty()) {
                Dialog.showNotice("Lỗi", "Vui lòng nhập đầy đủ thông tin thuốc.", false);
                return;
            }

            selectedThuoc.setTenThuoc(ten);
            selectedThuoc.setLoaiThuoc(loai);
            selectedThuoc.setDonVi(donVi);
            selectedThuoc.setGia(gia);
            selectedThuoc.setSoLuongTon(soLuong);

            boolean updated = warehouseService.updateThuoc(selectedThuoc);
            if (updated) {
                onRefresh();
                Dialog.showNotice("Thành công", "Đã cập nhật thành công!", true);
            } else {
                Dialog.showNotice("Lỗi", "Không thể cập nhật thuốc!", false);
            }
        } catch (NumberFormatException e) {
            Dialog.showNotice("Lỗi", "Giá hoặc số lượng tồn không hợp lệ!", false);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void onDelete() {
        if (selectedThuoc == null) {
            Dialog.showNotice("Lỗi", "Vui lòng chọn thuốc để xóa!", false);
            return;
        }

        try {
            boolean deleted = warehouseService.deleteThuoc(selectedThuoc.getMaThuoc());
            if (deleted) {
                onRefresh();
                Dialog.showNotice("Thành công", "Đã xóa thành công!", true);
            } else {
                Dialog.showNotice("Lỗi", "Không thể xóa thuốc!", false);
            }
        } catch (Exception e) {
            Dialog.showNotice("Lỗi", "Không thể xóa thuốc!", false);
        }
    }
}
