package com.admin.server.dao;

import com.admin.server.utils.JDBCUtil;
import com.admin.shared.model.ChiTietToaThuoc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ChiTietToaThuocDAO {
    public void add(ChiTietToaThuoc chiTiet) {
        String sql = "INSERT INTO ChiTietToaThuoc (MaToaThuoc, MaThuoc, SoLuong, LieuLuong, ThanhTien) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, chiTiet.getMaToaThuoc());
            stmt.setInt(2, chiTiet.getMaThuoc());
            stmt.setInt(3, chiTiet.getSoLuong());
            stmt.setString(4, chiTiet.getLieuLuong());
            stmt.setBigDecimal(5, chiTiet.getThanhTien());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

