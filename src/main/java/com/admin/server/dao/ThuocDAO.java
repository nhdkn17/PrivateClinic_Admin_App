package com.admin.server.dao;

import com.admin.shared.model.Thuoc;
import com.admin.server.utils.JDBCUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ThuocDAO {
    public boolean insert(Thuoc thuoc) {
        String sql = "INSERT INTO Thuoc (TenThuoc, LoaiThuoc, DonVi, Gia, SoLuongTon) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, thuoc.getTenThuoc());
            stmt.setString(2, thuoc.getLoaiThuoc());
            stmt.setString(3, thuoc.getDonVi());
            stmt.setDouble(4, thuoc.getGia());
            stmt.setInt(5, thuoc.getSoLuongTon());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Thuoc getById(int id) {
        String sql = "SELECT * FROM Thuoc WHERE MaThuoc = ?";
        try (Connection conn = JDBCUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Thuoc thuoc = new Thuoc();
                thuoc.setMaThuoc(rs.getInt("MaThuoc"));
                thuoc.setTenThuoc(rs.getString("TenThuoc"));
                thuoc.setLoaiThuoc(rs.getString("LoaiThuoc"));
                thuoc.setDonVi(rs.getString("DonVi"));
                thuoc.setGia(rs.getDouble("Gia"));
                thuoc.setSoLuongTon(rs.getInt("SoLuongTon"));
                return thuoc;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Thuoc> searchThuocByTen(String keyword) {
        List<Thuoc> thuocList = new ArrayList<>();
        String sql = "SELECT * FROM Thuoc WHERE LOWER(TenThuoc) LIKE ?";
        try (Connection conn = JDBCUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + keyword.toLowerCase() + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Thuoc thuoc = new Thuoc();
                thuoc.setMaThuoc(rs.getInt("MaThuoc"));
                thuoc.setTenThuoc(rs.getString("TenThuoc"));
                thuoc.setLoaiThuoc(rs.getString("LoaiThuoc"));
                thuoc.setDonVi(rs.getString("DonVi"));
                thuoc.setGia(rs.getDouble("Gia"));
                thuoc.setSoLuongTon(rs.getInt("SoLuongTon"));
                thuocList.add(thuoc);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return thuocList;
    }

    public List<Thuoc> getThuocSapHet() {
        List<Thuoc> thuocList = new ArrayList<>();
        String sql = "SELECT * FROM Thuoc WHERE SoLuongTon < 50";
        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Thuoc thuoc = new Thuoc();
                thuoc.setMaThuoc(rs.getInt("MaThuoc"));
                thuoc.setTenThuoc(rs.getString("TenThuoc"));
                thuoc.setLoaiThuoc(rs.getString("LoaiThuoc"));
                thuoc.setDonVi(rs.getString("DonVi"));
                thuoc.setGia(rs.getDouble("Gia"));
                thuoc.setSoLuongTon(rs.getInt("SoLuongTon"));
                thuocList.add(thuoc);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return thuocList;
    }

    public List<Thuoc> findAll() {
        List<Thuoc> thuocList = new ArrayList<>();
        String sql = "SELECT * FROM Thuoc";
        try (Connection conn = JDBCUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Thuoc thuoc = new Thuoc();
                thuoc.setMaThuoc(rs.getInt("MaThuoc"));
                thuoc.setTenThuoc(rs.getString("TenThuoc"));
                thuoc.setLoaiThuoc(rs.getString("LoaiThuoc"));
                thuoc.setDonVi(rs.getString("DonVi"));
                thuoc.setGia(rs.getDouble("Gia"));
                thuoc.setSoLuongTon(rs.getInt("SoLuongTon"));
                thuocList.add(thuoc);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return thuocList;
    }

    public boolean update(Thuoc thuoc) {
        String sql = "UPDATE Thuoc SET TenThuoc = ?, LoaiThuoc = ?, DonVi = ?, Gia = ?, SoLuongTon = ? WHERE MaThuoc = ?";
        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, thuoc.getTenThuoc());
            stmt.setString(2, thuoc.getLoaiThuoc());
            stmt.setString(3, thuoc.getDonVi());
            stmt.setDouble(4, thuoc.getGia());
            stmt.setInt(5, thuoc.getSoLuongTon());
            stmt.setInt(6, thuoc.getMaThuoc());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM Thuoc WHERE MaThuoc = ?";
        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void capNhatSoLuongTon(int maThuoc, int soLuongMoi) {
        String sql = "UPDATE Thuoc SET SoLuongTon = ? WHERE MaThuoc = ?";
        try (Connection conn = JDBCUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, soLuongMoi);
            stmt.setInt(2, maThuoc);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Thuoc> getRandomThuoc(int soLuong) throws SQLException {
        String sql = "SELECT TOP (?) * FROM Thuoc ORDER BY NEWID()";
        List<Thuoc> dsThuoc = new ArrayList<>();
        try (Connection conn = JDBCUtil.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, soLuong);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Thuoc t = new Thuoc();
                    t.setMaThuoc(rs.getInt("MaThuoc"));
                    t.setTenThuoc(rs.getString("TenThuoc"));
                    t.setDonVi(rs.getString("DonVi"));
                    t.setGia(rs.getDouble("Gia"));
                    dsThuoc.add(t);
                }
            }
        }
        return dsThuoc;
    }
}
