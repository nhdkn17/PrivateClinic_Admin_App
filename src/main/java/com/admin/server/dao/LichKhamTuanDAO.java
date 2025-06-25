package com.admin.server.dao;

import com.admin.server.utils.JDBCUtil;
import com.admin.shared.model.LichKham;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LichKhamTuanDAO {
    public List<LichKham> getAllLichKham() {
        List<LichKham> list = new ArrayList<>();
        String sql = "SELECT * FROM LichKham";
        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                LichKham lich = extractFromResultSet(rs);
                list.add(lich);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Lấy danh sách lịch khám theo mã bác sĩ
    public List<LichKham> getLichKhamByBacSi(int maBacSi) {
        List<LichKham> list = new ArrayList<>();
        String sql = "SELECT * FROM LichKham WHERE maBacSi = ?";
        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, maBacSi);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(extractFromResultSet(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean addLichKham(LichKham lichKham) {
        String sql = "INSERT INTO LichKham (maBenhNhan, maBacSi, gioBatDau, trangThai, ghiChu) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, lichKham.getMaBenhNhan());
            ps.setInt(2, lichKham.getMaBacSi());
            ps.setTimestamp(3, lichKham.getGioBatDau());
            ps.setString(4, lichKham.getTrangThai());
            ps.setString(5, lichKham.getGhiChu());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateLichKham(LichKham lichKham) {
        String sql = "UPDATE LichKham SET maBenhNhan=?, maBacSi=?, gioBatDau=?, trangThai=?, ghiChu=? WHERE maLichKham=?";
        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, lichKham.getMaBenhNhan());
            ps.setInt(2, lichKham.getMaBacSi());
            ps.setTimestamp(3, lichKham.getGioBatDau());
            ps.setString(4, lichKham.getTrangThai());
            ps.setString(5, lichKham.getGhiChu());
            ps.setInt(6, lichKham.getMaLichKham());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Xóa lịch khám
    public boolean deleteLichKham(int maLichKham) {
        String sql = "DELETE FROM LichKham WHERE maLichKham = ?";
        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, maLichKham);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    private LichKham extractFromResultSet(ResultSet rs) throws SQLException {
        return new LichKham(
                rs.getInt("maLichKham"),
                rs.getInt("maBenhNhan"),
                rs.getInt("maBacSi"),
                rs.getTimestamp("gioBatDau"),
                rs.getString("trangThai"),
                rs.getString("ghiChu")
        );
    }
}
