package com.admin.server.service;

import com.admin.server.dao.DashboardStatsDAO;
import com.admin.server.dao.TaiKhoanDAO;
import com.admin.server.utils.EmailUtil;
import com.admin.server.utils.JDBCUtil;
import com.admin.shared.model.DashboardStats;
import com.admin.shared.protocol.Action;
import com.admin.shared.protocol.Response;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.Map;

public class SV_DashboardService {

    private final TaiKhoanDAO taiKhoanDAO = new TaiKhoanDAO();

    public Response handle(String action, Map<String, Object> data) {
        return switch (action) {
            case Action.DASHBOARD_STATS -> handleDashboardStats();
            case Action.GET_ALL_ACCOUNTS -> handleGetAllAccounts();
            case Action.LOCK_ACCOUNT -> handleLockAccount(data);
            case Action.UNLOCK_ACCOUNT -> handleUnlockAccount(data);
            default -> new Response("error", "Yêu cầu không hợp lệ", null);
        };
    }

    private Response handleDashboardStats() {
        try (Connection conn = JDBCUtil.getConnection()) {
            DashboardStatsDAO dao = new DashboardStatsDAO(conn);

            int soBenhNhan = dao.getSoBenhNhanHomNay();
            int soLichHen = dao.getSoLichHenHomNay();
            int soDonThuoc = dao.getSoDonThuocHomNay();
            BigDecimal doanhThu = dao.getRawDoanhThuHomNay();

            DashboardStats stats = new DashboardStats(soBenhNhan, soLichHen, soDonThuoc, doanhThu);
            return new Response("success", "Lấy dữ liệu dashboard thành công", stats);
        } catch (Exception e) {
            e.printStackTrace();
            return new Response("error", "Không thể lấy dữ liệu dashboard", null);
        }
    }

    private Response handleGetAllAccounts() {
        try {
            return new Response("success", "Tải danh sách thành công", taiKhoanDAO.findAll());
        } catch (Exception e) {
            return new Response("fail", "Lỗi khi lấy danh sách tài khoản", null);
        }
    }

    private Response handleLockAccount(Map<String, Object> data) {
        String email = (String) data.get("email");
        if (email == null || email.isBlank()) {
            return new Response("fail", "Thiếu email để khóa tài khoản", null);
        }

        boolean success = taiKhoanDAO.capNhatTrangThai(email, false);
        return success
                ? new Response("success", "Đã khóa tài khoản", null)
                : new Response("fail", "Không tìm thấy tài khoản để khóa", null);
    }

    private Response handleUnlockAccount(Map<String, Object> data) {
        String email = (String) data.get("email");
        if (email == null || email.isBlank()) {
            return new Response("fail", "Thiếu email để mở khóa tài khoản", null);
        }

        boolean success = taiKhoanDAO.capNhatTrangThai(email, true);
        if (success) {
            EmailUtil.sendMail(
                    email,
                    "Tài khoản đã được mở khóa",
                    """
                    Chào bạn,

                    Tài khoản của bạn đã được kích hoạt. Giờ đây bạn có thể đăng nhập hệ thống PrivateClinic Application.

                    Trân trọng.
                    -------------------
                    Thông tin liên hệ:
                    Điện thoại: +84845612378
                    Email: Privateclinic_ms@company.com.vn
                    """
            );
            return new Response("success", "Đã mở khóa tài khoản", null);
        } else {
            return new Response("fail", "Không tìm thấy tài khoản để mở khóa", null);
        }
    }
}
