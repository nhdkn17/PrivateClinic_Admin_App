package com.admin.server.dao;

import com.admin.shared.model.TaiKhoan;
import com.admin.server.security.PasswordUtil;
import com.admin.server.utils.JDBCUtil;
import org.jetbrains.annotations.NotNull;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TaiKhoanDAO {
    private Connection conn;

    public TaiKhoanDAO() {
        conn = JDBCUtil.getConnection();
    }

    public TaiKhoanDAO(Connection conn) {
        this.conn = conn;
    }

    public boolean insert(TaiKhoan tk) throws SQLException {
        String sql = "INSERT INTO TaiKhoan (Email, MatKhau, VaiTro, MaNhanVien, MaBacSi, TrangThai) " +
                "VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, tk.getEmail());
            ps.setString(2, tk.getMatKhau());
            ps.setString(3, tk.getVaiTro());
            if (tk.getMaNhanVien() != null)
                ps.setInt(4, tk.getMaNhanVien());
            else
                ps.setNull(4, Types.INTEGER);
            if (tk.getMaBacSi() != null)
                ps.setInt(5, tk.getMaBacSi());
            else
                ps.setNull(5, Types.INTEGER);
            ps.setBoolean(6, tk.isTrangThai());
            return ps.executeUpdate() > 0;
        }
    }

    public boolean delete(int maTaiKhoan) throws SQLException {
        String sql = "DELETE FROM TaiKhoan WHERE MaTaiKhoan = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, maTaiKhoan);
            return ps.executeUpdate() > 0;
        }
    }

    public List<TaiKhoan> findAll() {
        List<TaiKhoan> list = new ArrayList<>();
        String sql = "SELECT * FROM TaiKhoan";
        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                list.add(extractTaiKhoan(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public TaiKhoan findByEmail(String username) {
        String sql = "SELECT * FROM TaiKhoan WHERE email = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                TaiKhoan tk = new TaiKhoan();
                tk.setEmail(rs.getString("email"));
                tk.setMatKhau(rs.getString("matKhau"));
                tk.setVaiTro(rs.getString("vaiTro"));
                tk.setMaNhanVien(rs.getObject("maNhanVien", Integer.class));
                tk.setMaBacSi(rs.getObject("maBacSi", Integer.class));
                tk.setTrangThai(rs.getBoolean("trangThai"));
                return tk;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @NotNull
    private TaiKhoan extractTaiKhoan(ResultSet rs) throws SQLException {
        TaiKhoan tk = new TaiKhoan();
        tk.setEmail(rs.getString("email"));
        tk.setMatKhau(rs.getString("matKhau"));
        tk.setVaiTro(rs.getString("vaiTro"));
        tk.setMaNhanVien((Integer) rs.getObject("maNhanVien"));
        tk.setMaBacSi((Integer) rs.getObject("maBacSi"));
        tk.setTrangThai(rs.getBoolean("trangThai"));
        return tk;
    }

    public boolean checkLogin(String email, String rawPassword) {
        TaiKhoan tk = findByEmail(email);
        if (tk == null || !tk.isTrangThai()) return false;
        return PasswordUtil.verifyPassword(rawPassword, tk.getMatKhau());
    }

    public boolean capNhatTrangThai(String email, boolean trangThai) {
        String sql = "UPDATE TaiKhoan SET trangThai = ? WHERE email = ?";
        try (Connection conn = JDBCUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setBoolean(1, trangThai);
            stmt.setString(2, email);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private TaiKhoan mapResultSetToTaiKhoan(ResultSet rs) throws SQLException {
        TaiKhoan tk = new TaiKhoan();
        tk.setMaTaiKhoan(rs.getInt("MaTaiKhoan"));
        tk.setEmail(rs.getString("Email"));
        tk.setMatKhau(rs.getString("MatKhau"));
        tk.setVaiTro(rs.getString("VaiTro"));
        int maNhanVien = rs.getInt("MaNhanVien");
        tk.setMaNhanVien(rs.wasNull() ? null : maNhanVien);
        int maBacSi = rs.getInt("MaBacSi");
        tk.setMaBacSi(rs.wasNull() ? null : maBacSi);
        tk.setTrangThai(rs.getBoolean("TrangThai"));
        return tk;
    }
}
