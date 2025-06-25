package com.admin.server.dao;

import com.admin.shared.model.PhieuKham;
import com.admin.server.utils.JDBCUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PhieuKhamDAO {

    public List<PhieuKham> getAll() {
        List<PhieuKham> list = new ArrayList<>();
        String sql = "SELECT * FROM PhieuKham";

        try (Connection conn = JDBCUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                PhieuKham pk = new PhieuKham();
                pk.setMaPhieuKham(rs.getInt("MaPhieuKham"));
                pk.setMaBenhNhan(rs.getInt("MaBenhNhan"));
                pk.setMaBacSi(rs.getInt("MaBacSi"));
                pk.setNgayKham(rs.getDate("NgayKham"));
                pk.setTrieuChung(rs.getString("TrieuChung"));
                pk.setChanDoan(rs.getString("ChanDoan"));
                pk.setTrangThai(rs.getString("TrangThai"));
                list.add(pk);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public PhieuKham getById(int id) {
        String sql = "SELECT * FROM PhieuKham WHERE MaPhieuKham = ?";
        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new PhieuKham(
                        rs.getInt("MaPhieuKham"),
                        rs.getInt("MaBenhNhan"),
                        rs.getInt("MaBacSi"),
                        rs.getDate("NgayKham"),
                        rs.getString("TrieuChung"),
                        rs.getString("ChanDoan"),
                        rs.getString("TrangThai")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public boolean insert(PhieuKham pk) {
        String sql = "INSERT INTO PhieuKham (MaBenhNhan, MaBacSi, NgayKham, TrieuChung, ChanDoan, TrangThai) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, pk.getMaBenhNhan());
            stmt.setInt(2, pk.getMaBacSi());
            stmt.setDate(3, pk.getNgayKham());
            stmt.setString(4, pk.getTrieuChung());
            stmt.setString(5, pk.getChanDoan());
            stmt.setString(6, pk.getTrangThai());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean update(PhieuKham pk) {
        String sql = "UPDATE PhieuKham SET MaBenhNhan=?, MaBacSi=?, NgayKham=?, TrieuChung=?, ChanDoan=?, TrangThai=? WHERE MaPhieuKham=?";
        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, pk.getMaBenhNhan());
            stmt.setInt(2, pk.getMaBacSi());
            stmt.setDate(3, pk.getNgayKham());
            stmt.setString(4, pk.getTrieuChung());
            stmt.setString(5, pk.getChanDoan());
            stmt.setString(6, pk.getTrangThai());
            stmt.setInt(7, pk.getMaPhieuKham());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM PhieuKham WHERE MaPhieuKham = ?";
        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}
