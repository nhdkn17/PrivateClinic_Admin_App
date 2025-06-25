package com.admin.server.dao;

import com.admin.server.utils.JDBCUtil;
import com.admin.shared.model.BenhNhan;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BenhNhanDAO {
    public boolean add(BenhNhan benhNhan) {
        String sql = "INSERT INTO BenhNhan(TenBenhNhan, NgaySinh, GioiTinh, SoDienThoai, Email, DiaChi, NgayKham) VALUES(?,?,?,?,?,?,?)";
        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, benhNhan.getTenBenhNhan());
            stmt.setDate(2, benhNhan.getNgaySinh());
            stmt.setString(3, benhNhan.getGioiTinh());
            stmt.setString(4, benhNhan.getSoDienThoai());
            stmt.setString(5, benhNhan.getEmail());
            stmt.setString(6, benhNhan.getDiaChi());
            stmt.setDate(7, benhNhan.getNgayKham());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public BenhNhan getById(int id) {
        String sql = "SELECT * FROM BenhNhan WHERE MaBenhNhan = ?";
        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                BenhNhan benhNhan = new BenhNhan();
                benhNhan.setMaBenhNhan(rs.getInt("MaBenhNhan"));
                benhNhan.setTenBenhNhan(rs.getString("TenBenhNhan"));
                benhNhan.setNgaySinh(rs.getDate("NgaySinh"));
                benhNhan.setGioiTinh(rs.getString("GioiTinh"));
                benhNhan.setSoDienThoai(rs.getString("SoDienThoai"));
                benhNhan.setEmail(rs.getString("Email"));
                benhNhan.setDiaChi(rs.getString("DiaChi"));
                benhNhan.setNgayKham(rs.getDate("NgayKham"));
                return benhNhan;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<BenhNhan> getAll() {
        List<BenhNhan> benhNhanList = new ArrayList<>();
        String sql = "SELECT * FROM BenhNhan";
        try (Connection conn = JDBCUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                BenhNhan benhNhan = new BenhNhan();
                benhNhan.setMaBenhNhan(rs.getInt("MaBenhNhan"));
                benhNhan.setTenBenhNhan(rs.getString("TenBenhNhan"));
                benhNhan.setNgaySinh(rs.getDate("NgaySinh"));
                benhNhan.setGioiTinh(rs.getString("GioiTinh"));
                benhNhan.setSoDienThoai(rs.getString("SoDienThoai"));
                benhNhan.setEmail(rs.getString("Email"));
                benhNhan.setDiaChi(rs.getString("DiaChi"));
                benhNhan.setNgayKham(rs.getDate("NgayKham"));
                benhNhanList.add(benhNhan);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return benhNhanList;
    }

    public boolean update(BenhNhan benhNhan) {
        String sql = "UPDATE BenhNhan SET TenBenhNhan = ?, NgaySinh = ?, GioiTinh = ?, SoDienThoai = ?, Email = ?, DiaChi = ?, NgayKham = ? WHERE MaBenhNhan = ?";
        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, benhNhan.getTenBenhNhan());
            stmt.setDate(2, benhNhan.getNgaySinh());
            stmt.setString(3, benhNhan.getGioiTinh());
            stmt.setString(4, benhNhan.getSoDienThoai());
            stmt.setString(5, benhNhan.getEmail());
            stmt.setString(6, benhNhan.getDiaChi());
            stmt.setDate(7, benhNhan.getNgayKham());
            stmt.setInt(8, benhNhan.getMaBenhNhan());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM BenhNhan WHERE MaBenhNhan = ?";
        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Integer getMaBenhNhanByTen(String tenBenhNhan) {
        String sql = "SELECT MaBenhNhan FROM BenhNhan WHERE TenBenhNhan = ?";
        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, tenBenhNhan);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("MaBenhNhan");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Integer addReturnId(BenhNhan benhNhan) {
        String sql = "INSERT INTO BenhNhan(TenBenhNhan, NgaySinh, GioiTinh, SoDienThoai, Email, DiaChi, NgayKham) VALUES(?,?,?,?,?,?,?)";
        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, benhNhan.getTenBenhNhan());
            stmt.setDate(2, benhNhan.getNgaySinh());
            stmt.setString(3, benhNhan.getGioiTinh());
            stmt.setString(4, benhNhan.getSoDienThoai());
            stmt.setString(5, benhNhan.getEmail());
            stmt.setString(6, benhNhan.getDiaChi());
            stmt.setDate(7, benhNhan.getNgayKham());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) return null;

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
