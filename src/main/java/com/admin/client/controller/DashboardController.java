package com.admin.client.controller;

import com.admin.client.service.CL_AuthService;
import com.admin.client.utils.NetworkUtil;
import com.admin.client.controller.other.Dialog;
import com.admin.server.utils.JDBCUtil;
import com.admin.shared.model.DashboardStats;
import com.admin.shared.model.OnlineUser;
import com.admin.shared.model.TaiKhoan;
import com.admin.shared.protocol.Action;
import com.admin.shared.protocol.Request;
import com.admin.shared.protocol.Response;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DashboardController {
    @FXML
    private Label lblSoBenhNhan;
    @FXML
    private Label lblSoDonThuoc;
    @FXML
    private Label lblSoLichHen;
    @FXML
    private Label lblDoanhThu;
    @FXML
    private TableView<TaiKhoan> tblAccounts;
    @FXML
    private TableColumn<TaiKhoan, String> colEmail;
    @FXML
    private TableColumn<TaiKhoan, String> colRole;
    @FXML
    private TableColumn<TaiKhoan, String> colStatus;
    @FXML
    private Button btnLock;
    @FXML
    private Button btnUnlock;
    @FXML
    private FlowPane cardContainer;

    private final Gson gson = new Gson();
    private final CL_AuthService CLAuthService = new CL_AuthService();
    private final ObservableList<TaiKhoan> accountList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        try (NetworkUtil network = new NetworkUtil()) {
            Request req = new Request(Action.DASHBOARD_STATS, null);
            network.send(gson.toJson(req));
            String resJson = network.receive();

            Response res = gson.fromJson(resJson, Response.class);
            if ("success".equals(res.getStatus())) {
                DashboardStats stats = gson.fromJson(gson.toJson(res.getData()), DashboardStats.class);

                lblSoBenhNhan.setText(String.valueOf(stats.getSoBenhNhan()));
                lblSoLichHen.setText(String.valueOf(stats.getSoLichHenHomNay()));
                lblSoDonThuoc.setText(String.valueOf(stats.getSoDonThuoc()));

                String doanhThu = String.valueOf(stats.getDoanhThuHomNay());
                lblDoanhThu.setText(doanhThu != null ? doanhThu + " VNĐ" : "0 VNĐ");
            } else {
                lblSoBenhNhan.setText("Lỗi");
                lblSoLichHen.setText("Lỗi");
                lblSoDonThuoc.setText("Lỗi");
                lblDoanhThu.setText("Lỗi");
            }

            colEmail.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEmail()));
            colRole.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getVaiTro()));
            colStatus.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().isTrangThai() ? "Mở khóa" : "Khóa"));

            loadAccounts();
            loadOnlineAccounts();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadAccounts() throws IOException {
        Response res = CLAuthService.getAllAccounts();

        if (res != null && res.isSuccess()) {
            String dataJson = gson.toJson(res.getData());
            Type listType = new TypeToken<List<TaiKhoan>>() {}.getType();
            List<TaiKhoan> list = gson.fromJson(dataJson, listType);

            accountList.setAll(list);
            tblAccounts.setItems(accountList);
        } else {
            Dialog.showNotice("Lỗi", "Không thể tải danh sách tài khoản.", false);
        }
    }

    private void loadOnlineAccounts() {
        List<OnlineUser> onlineUsers = new ArrayList<>();
        String query = """
        SELECT 
            tk.VaiTro,
            CASE 
                WHEN tk.VaiTro = 'BAC_SI' THEN bs.TenBacSi
                WHEN tk.VaiTro = 'LE_TAN' THEN nv.HoTen
            END AS HoTen
        FROM DangNhapHienTai d
        JOIN TaiKhoan tk ON d.MaTaiKhoan = tk.MaTaiKhoan
        LEFT JOIN BacSi bs ON tk.MaBacSi = bs.MaBacSi
        LEFT JOIN NhanVien nv ON tk.MaNhanVien = nv.MaNhanVien
        WHERE tk.VaiTro IN ('BAC_SI', 'LE_TAN')
    """;

        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String ten = rs.getString("HoTen");
                String vaiTro = rs.getString("VaiTro");
                onlineUsers.add(new OnlineUser(ten, vaiTro));
            }

            loadOnlineUserCards(onlineUsers);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleLockAccount() throws IOException {
        TaiKhoan selected = tblAccounts.getSelectionModel().getSelectedItem();
        if (selected == null) {
            Dialog.showNotice("Thông báo", "Vui lòng chọn tài khoản cần khóa", false);
            return;
        }

        Response res = CLAuthService.lockAccount(selected.getEmail());
        if ("success".equals(res.getStatus())) {
            Dialog.showNotice("Thành công", "Đã khóa tài khoản", true);
            loadAccounts();
        } else {
            Dialog.showNotice("Lỗi", res.getMessage(), false);
        }
    }

    @FXML
    private void handleUnlockAccount() throws IOException {
        TaiKhoan selected = tblAccounts.getSelectionModel().getSelectedItem();
        if (selected == null) {
            Dialog.showNotice("Thông báo", "Vui lòng chọn tài khoản cần mở khóa", false);
            return;
        }

        Response res = CLAuthService.unlockAccount(selected.getEmail());
        if ("success".equals(res.getStatus())) {
            Dialog.showNotice("Thành công", "Đã mở khóa tài khoản", true);
            loadAccounts();
        } else {
            Dialog.showNotice("Lỗi", res.getMessage(), false);
        }
    }

    private void loadOnlineUserCards(List<OnlineUser> users) {
        cardContainer.getChildren().clear();

        for (OnlineUser user : users) {
            VBox card = new VBox(5);
            card.setPadding(new Insets(10));
            card.setPrefWidth(200);
            card.setStyle("-fx-background-color: #ecf0f1; -fx-background-radius: 10; -fx-border-radius: 10; -fx-border-color: #bdc3c7;");

            Label nameLabel = new Label(user.getName());
            nameLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

            Label roleLabel = new Label(user.getRole().equals("BAC_SI") ? "Bác sĩ" : "Lễ tân");
            roleLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #7f8c8d;");

            card.getChildren().addAll(nameLabel, roleLabel);
            cardContainer.getChildren().add(card);
        }
    }
}
