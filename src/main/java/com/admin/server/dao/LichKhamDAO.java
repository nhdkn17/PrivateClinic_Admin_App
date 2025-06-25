package com.admin.server.dao;

import com.admin.server.utils.JDBCUtil;
import com.admin.shared.model.LichKham;
import com.admin.shared.model.LichKhamModel;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class LichKhamDAO {
    public boolean add(LichKham lichKham) {
        String sql = "INSERT INTO LichKham (MaBenhNhan, MaBacSi, GioBatDau, TrangThai, GhiChu) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, lichKham.getMaBenhNhan());
            stmt.setInt(2, lichKham.getMaBacSi());
            stmt.setTimestamp(3, lichKham.getGioBatDau());
            stmt.setString(4, lichKham.getTrangThai());
            stmt.setString(5, lichKham.getGhiChu());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean update(LichKham lichKham) {
        String sql = "UPDATE LichKham SET MaBenhNhan = ?, MaBacSi = ?, GioBatDau = ?, TrangThai = ?, GhiChu = ? WHERE MaLichKham = ?";
        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, lichKham.getMaBenhNhan());
            stmt.setInt(2, lichKham.getMaBacSi());
            stmt.setTimestamp(3, lichKham.getGioBatDau());
            stmt.setString(4, lichKham.getTrangThai());
            stmt.setString(5, lichKham.getGhiChu());
            stmt.setInt(6, lichKham.getMaLichKham());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM LichKham WHERE MaLichKham = ?";
        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public LichKham getById(int id) {
        String sql = "SELECT * FROM LichKham WHERE MaLichKham = ?";
        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                LichKham lichKham = new LichKham();
                lichKham.setMaLichKham(rs.getInt("MaLichKham"));
                lichKham.setMaBenhNhan(rs.getInt("MaBenhNhan"));
                lichKham.setMaBacSi(rs.getInt("MaBacSi"));
                lichKham.setGioBatDau(rs.getTimestamp("GioBatDau"));
                lichKham.setTrangThai(rs.getString("TrangThai"));
                lichKham.setGhiChu(rs.getString("GhiChu"));
                return lichKham;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<LichKham> getAll() {
        List<LichKham> lichKhamList = new ArrayList<>();
        String sql = "SELECT * FROM LichKham";
        try (Connection conn = JDBCUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                LichKham lichKham = new LichKham();
                lichKham.setMaLichKham(rs.getInt("MaLichKham"));
                lichKham.setMaBenhNhan(rs.getInt("MaBenhNhan"));
                lichKham.setMaBacSi(rs.getInt("MaBacSi"));
                lichKham.setGioBatDau(rs.getTimestamp("GioBatDau"));
                lichKham.setTrangThai(rs.getString("TrangThai"));
                lichKham.setGhiChu(rs.getString("GhiChu"));
                lichKhamList.add(lichKham);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lichKhamList;
    }

    public List<LichKhamModel> getLichKhamHomNay() {
        List<LichKhamModel> list = new ArrayList<>();
        String sql = """
            SELECT lk.MaLichKham, bn.TenBenhNhan, bs.TenBacSi, lk.GioBatDau, lk.TrangThai, lk.GhiChu
                    FROM LichKham lk
                    JOIN BenhNhan bn ON lk.MaBenhNhan = bn.MaBenhNhan
                    JOIN BacSi bs ON lk.MaBacSi = bs.MaBacSi
                    WHERE CAST(lk.GioBatDau AS DATE) = CAST(GETDATE() AS DATE)
        """;

        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int maLichKham = rs.getInt("MaLichKham");
                String tenBenhNhan = rs.getString("TenBenhNhan");
                String tenBacSi = rs.getString("TenBacSi");
                LocalDateTime gioBatDau = rs.getTimestamp("GioBatDau").toLocalDateTime();
                String trangThai = rs.getString("TrangThai");
                String ghiChu = rs.getString("GhiChu");

                LichKhamModel model = new LichKhamModel(maLichKham, tenBenhNhan, tenBacSi, gioBatDau, trangThai, ghiChu);
                list.add(model);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<LichKhamModel> getLichKhamTheoNgay(LocalDate date) throws SQLException {
        List<LichKhamModel> list = new ArrayList<>();

        Timestamp startOfDay = Timestamp.valueOf(date.atStartOfDay());
        Timestamp endOfDay = Timestamp.valueOf(date.plusDays(1).atStartOfDay());

        String query = """
        SELECT lk.MaLichKham, bn.TenBenhNhan, bs.TenBacSi, lk.GioBatDau, lk.TrangThai, lk.GhiChu
        FROM LichKham lk
        JOIN BenhNhan bn ON lk.MaBenhNhan = bn.MaBenhNhan
        JOIN BacSi bs ON lk.MaBacSi = bs.MaBacSi
        WHERE lk.GioBatDau >= ? AND lk.GioBatDau < ?
        ORDER BY lk.GioBatDau
    """;

        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setTimestamp(1, startOfDay);
            ps.setTimestamp(2, endOfDay);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int maLichKham = rs.getInt("MaLichKham");
                String tenBenhNhan = rs.getString("TenBenhNhan");
                String tenBacSi = rs.getString("TenBacSi");
                LocalDateTime gioBatDau = rs.getTimestamp("GioBatDau").toLocalDateTime();
                String trangThai = rs.getString("TrangThai");
                String ghiChu = rs.getString("GhiChu");

                LichKhamModel model = new LichKhamModel(maLichKham, tenBenhNhan, tenBacSi, gioBatDau, trangThai, ghiChu);
                list.add(model);
            }
        }
        return list;
    }

    public List<LichKham> getLichKhamEntitiesTheoNgay(LocalDate date) throws SQLException {
        List<LichKham> list = new ArrayList<>();

        Timestamp startOfDay = Timestamp.valueOf(date.atStartOfDay());
        Timestamp endOfDay = Timestamp.valueOf(date.plusDays(1).atStartOfDay());

        String query = "SELECT * FROM LichKham WHERE GioBatDau >= ? AND GioBatDau < ? ORDER BY GioBatDau";

        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setTimestamp(1, startOfDay);
            ps.setTimestamp(2, endOfDay);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int maLichKham = rs.getInt("MaLichKham");
                int maBenhNhan = rs.getInt("MaBenhNhan");
                int maBacSi = rs.getInt("MaBacSi");
                Timestamp tsGioBatDau = rs.getTimestamp("GioBatDau");
                String trangThai = rs.getString("TrangThai");
                String ghiChu = rs.getString("GhiChu");

                LichKham lk = new LichKham(maLichKham, maBenhNhan, maBacSi, tsGioBatDau, trangThai, ghiChu);
                list.add(lk);
            }
        }
        return list;
    }
}