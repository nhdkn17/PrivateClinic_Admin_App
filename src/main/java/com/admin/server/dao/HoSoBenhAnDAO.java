package com.admin.server.dao;

import com.admin.server.utils.JDBCUtil;
import com.admin.shared.model.HoSoBenhAn;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HoSoBenhAnDAO implements DAO<HoSoBenhAn> {

    @Override
    public void add(HoSoBenhAn hoSoBenhAn) {
        String sql = "INSERT INTO HoSoBenhAn (MaLichKham, MaBenhNhan, ChanDoan, GhiChu) VALUES (?, ?, ?, ?)";
        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, hoSoBenhAn.getMaLichKham());
            stmt.setInt(2, hoSoBenhAn.getMaBenhNhan());
            stmt.setString(3, hoSoBenhAn.getChanDoan());
            stmt.setString(4, hoSoBenhAn.getGhiChu());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public HoSoBenhAn getById(int id) {
        String sql = "SELECT * FROM HoSoBenhAn WHERE MaHoSo = ?";
        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                HoSoBenhAn hoSoBenhAn = new HoSoBenhAn();
                hoSoBenhAn.setMaHoSo(rs.getInt("MaHoSo"));
                hoSoBenhAn.setMaLichKham(rs.getInt("MaLichKham"));
                hoSoBenhAn.setMaBenhNhan(rs.getInt("MaBenhNhan"));
                hoSoBenhAn.setChanDoan(rs.getString("ChanDoan"));
                hoSoBenhAn.setGhiChu(rs.getString("GhiChu"));
                return hoSoBenhAn;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<HoSoBenhAn> getAll() {
        List<HoSoBenhAn> hoSoBenhAnList = new ArrayList<>();
        String sql = "SELECT * FROM HoSoBenhAn";
        try (Connection conn = JDBCUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                HoSoBenhAn hoSoBenhAn = new HoSoBenhAn();
                hoSoBenhAn.setMaHoSo(rs.getInt("MaHoSo"));
                hoSoBenhAn.setMaLichKham(rs.getInt("MaLichKham"));
                hoSoBenhAn.setMaBenhNhan(rs.getInt("MaBenhNhan"));
                hoSoBenhAn.setChanDoan(rs.getString("ChanDoan"));
                hoSoBenhAn.setGhiChu(rs.getString("GhiChu"));
                hoSoBenhAnList.add(hoSoBenhAn);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return hoSoBenhAnList;
    }

    @Override
    public void update(HoSoBenhAn hoSoBenhAn) {
        String sql = "UPDATE HoSoBenhAn SET MaLichKham = ?, MaBenhNhan = ?, ChanDoan = ?, GhiChu = ? WHERE MaHoSo = ?";
        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, hoSoBenhAn.getMaLichKham());
            stmt.setInt(2, hoSoBenhAn.getMaBenhNhan());
            stmt.setString(3, hoSoBenhAn.getChanDoan());
            stmt.setString(4, hoSoBenhAn.getGhiChu());
            stmt.setInt(5, hoSoBenhAn.getMaHoSo());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM HoSoBenhAn WHERE MaHoSo = ?";
        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
