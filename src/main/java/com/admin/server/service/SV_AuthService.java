package com.admin.server.service;

import com.admin.server.dao.BacSiDAO;
import com.admin.server.dao.NhanVienDAO;
import com.admin.server.dao.TaiKhoanDAO;
import com.admin.server.security.PasswordUtil;
import com.admin.shared.model.BacSi;
import com.admin.shared.model.NhanVien;
import com.admin.shared.model.TaiKhoan;
import com.admin.shared.protocol.Action;
import com.admin.shared.protocol.Response;

import java.sql.SQLException;
import java.util.Map;

public class SV_AuthService {
    private final TaiKhoanDAO taiKhoanDAO = new TaiKhoanDAO();
    private final BacSiDAO bacSiDAO = new BacSiDAO();
    private final NhanVienDAO nhanVienDAO = new NhanVienDAO();

    public Response handle(String action, Map<String, Object> data) throws SQLException {
        return switch (action) {
            case Action.LOGIN -> handleLogin(data);
            case Action.REGISTER -> handleRegister(data);
            default -> new Response("error", "Yêu cầu không hợp lệ", null);
        };
    }

    private Response handleLogin(Map<String, Object> data) {
        String email = (String) data.get("email");
        String password = (String) data.get("password");

        if (email == null || password == null) {
            return new Response("fail", "Thiếu email hoặc mật khẩu", null);
        }

        TaiKhoan tk = taiKhoanDAO.findByEmail(email);
        if (tk == null) {
            return new Response("fail", "Sai thông tin đăng nhập", null);
        }

        if (!tk.isTrangThai()) {
            return new Response("fail", "Tài khoản đã bị khóa. Vui lòng liên hệ quản trị viên.", null);
        }

        boolean passwordMatch = PasswordUtil.verifyPassword(password, tk.getMatKhau());
        return passwordMatch
                ? new Response("success", "Đăng nhập thành công", tk)
                : new Response("fail", "Sai thông tin đăng nhập", null);
    }

    private Response handleRegister(Map<String, Object> data) throws SQLException {
        String email = (String) data.get("email");
        String password = (String) data.get("password");
        String vaiTro = (String) data.get("vaiTro");

        if (email == null || password == null || vaiTro == null) {
            return new Response("fail", "Thiếu thông tin đăng ký", null);
        }

        if (taiKhoanDAO.findByEmail(email) != null) {
            return new Response("fail", "Email đã tồn tại", null);
        }

        int maNhanVien = 0;
        int maBacSi = 0;

        switch (vaiTro) {
            case "LE_TAN" -> {
                String hoTen = (String) data.get("hoTen");
                String soDienThoai = (String) data.get("soDienThoai");
                if (hoTen == null || soDienThoai == null) {
                    return new Response("fail", "Thiếu thông tin lễ tân", null);
                }
                NhanVien nv = new NhanVien(hoTen, soDienThoai, email);
                maNhanVien = nhanVienDAO.insertAndReturnId(nv);
                if (maNhanVien == 0) return new Response("fail", "Không thể tạo nhân viên", null);
            }
            case "BAC_SI" -> {
                String tenBacSi = (String) data.get("tenBacSi");
                String chuyenKhoa = (String) data.get("chuyenKhoa");
                String soDienThoai = (String) data.get("soDienThoai");
                if (tenBacSi == null || chuyenKhoa == null || soDienThoai == null) {
                    return new Response("fail", "Thiếu thông tin bác sĩ", null);
                }
                BacSi bs = new BacSi(tenBacSi, chuyenKhoa, soDienThoai, email);
                maBacSi = bacSiDAO.insertAndReturnId(bs);
                if (maBacSi == 0) return new Response("fail", "Không thể tạo bác sĩ", null);
            }
        }

        String hashedPassword = PasswordUtil.hashPassword(password);

        TaiKhoan tk = new TaiKhoan();
        tk.setEmail(email);
        tk.setMatKhau(hashedPassword);
        tk.setVaiTro(vaiTro);
        tk.setTrangThai(vaiTro.equals("ADMIN"));
        if (maNhanVien > 0) tk.setMaNhanVien(maNhanVien);
        if (maBacSi > 0) tk.setMaBacSi(maBacSi);

        boolean success = taiKhoanDAO.insert(tk);
        return success
                ? new Response("success", "Đăng ký thành công", null)
                : new Response("fail", "Lỗi khi tạo tài khoản", null);
    }
}
