package com.admin.server.dao;

import com.admin.server.utils.JDBCUtil;
import com.admin.shared.model.HoaDon;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HoaDonDAO implements DAO<HoaDon> {

    @Override
    public void add(HoaDon hoaDon) {
        String sql = "INSERT INTO HoaDon (MaLichKham, MaBenhNhan, TongTien, TrangThaiThanhToan) VALUES (?, ?, ?, ?)";
        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, hoaDon.getMaLichKham());
            stmt.setInt(2, hoaDon.getMaBenhNhan());
            stmt.setDouble(3, hoaDon.getTongTien());
            stmt.setString(4, hoaDon.getTrangThaiThanhToan());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public HoaDon getById(int id) {
        String sql = "SELECT * FROM HoaDon WHERE MaHoaDon = ?";
        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                HoaDon hoaDon = new HoaDon();
                hoaDon.setMaHoaDon(rs.getInt("MaHoaDon"));
                hoaDon.setMaLichKham(rs.getInt("MaLichKham"));
                hoaDon.setMaBenhNhan(rs.getInt("MaBenhNhan"));
                hoaDon.setTongTien(rs.getDouble("TongTien"));
                hoaDon.setTrangThaiThanhToan(rs.getString("TrangThaiThanhToan"));
                hoaDon.setNgayTao(rs.getTimestamp("NgayTao"));
                return hoaDon;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<HoaDon> getAll() {
        List<HoaDon> hoaDonList = new ArrayList<>();
        String sql = "SELECT * FROM HoaDon";
        try (Connection conn = JDBCUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                HoaDon hoaDon = new HoaDon();
                hoaDon.setMaHoaDon(rs.getInt("MaHoaDon"));
                hoaDon.setMaLichKham(rs.getInt("MaLichKham"));
                hoaDon.setMaBenhNhan(rs.getInt("MaBenhNhan"));
                hoaDon.setTongTien(rs.getDouble("TongTien"));
                hoaDon.setTrangThaiThanhToan(rs.getString("TrangThaiThanhToan"));
                hoaDon.setNgayTao(rs.getTimestamp("NgayTao"));
                hoaDonList.add(hoaDon);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return hoaDonList;
    }

    @Override
    public void update(HoaDon hoaDon) {
        String sql = "UPDATE HoaDon SET MaLichKham = ?, MaBenhNhan = ?, TongTien = ?, TrangThaiThanhToan = ? WHERE MaHoaDon = ?";
        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, hoaDon.getMaLichKham());
            stmt.setInt(2, hoaDon.getMaBenhNhan());
            stmt.setDouble(3, hoaDon.getTongTien());
            stmt.setString(4, hoaDon.getTrangThaiThanhToan());
            stmt.setInt(5, hoaDon.getMaHoaDon());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM HoaDon WHERE MaHoaDon = ?";
        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
