package com.admin.server.dao;

import com.admin.server.utils.JDBCUtil;
import com.admin.shared.model.ToaThuoc;

import java.sql.*;

public class ToaThuocDAO {
    public int add(ToaThuoc toaThuoc) {
        String sql = "INSERT INTO ToaThuoc (MaBenhNhan, NgayLayThuoc) VALUES (?, ?)";
        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, toaThuoc.getMaBenhNhan());
            stmt.setDate(2, Date.valueOf(toaThuoc.getNgayLayThuoc()));
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }
}
