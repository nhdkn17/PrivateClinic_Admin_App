package com.admin.server.dao;

import com.admin.shared.model.KetQuaKham;
import com.admin.server.utils.JDBCUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class KetQuaKhamDAO {
    public List<KetQuaKham> getAll() {
        List<KetQuaKham> list = new ArrayList<>();
        String sql = "SELECT * FROM KetQuaKham";

        try (Connection conn = JDBCUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                KetQuaKham kq = new KetQuaKham();
                kq.setMaKetQuaKham(rs.getInt("MaKetQuaKham"));
                kq.setMaPhieuKham(rs.getInt("MaPhieuKham"));
                kq.setTrieuChung(rs.getString("TrieuChung"));
                kq.setChanDoanSoBo(rs.getString("ChanDoanSoBo"));
                kq.setKetLuan(rs.getString("KetLuan"));
                kq.setHuongDieuTri(rs.getString("HuongDieuTri"));
                kq.setGhiChu(rs.getString("GhiChu"));
                kq.setHuyetAp(rs.getString("HuyetAp"));
                kq.setNhietDo(rs.getDouble("NhietDo"));
                kq.setNhipTim(rs.getInt("NhipTim"));
                kq.setBmi(rs.getDouble("BMI"));
                kq.setNgayTaiKham(rs.getTimestamp("NgayTaiKham"));
                kq.setTrangThai(rs.getString("TrangThai"));
                list.add(kq);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public KetQuaKham getById(int id) {
        String sql = "SELECT * FROM KetQuaKham WHERE MaKetQuaKham = ?";
        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                KetQuaKham kq = new KetQuaKham();
                kq.setMaKetQuaKham(rs.getInt("MaKetQuaKham"));
                kq.setMaPhieuKham(rs.getInt("MaPhieuKham"));
                kq.setTrieuChung(rs.getString("TrieuChung"));
                kq.setChanDoanSoBo(rs.getString("ChanDoanSoBo"));
                kq.setKetLuan(rs.getString("KetLuan"));
                kq.setHuongDieuTri(rs.getString("HuongDieuTri"));
                kq.setGhiChu(rs.getString("GhiChu"));
                kq.setHuyetAp(rs.getString("HuyetAp"));
                kq.setNhietDo(rs.getDouble("NhietDo"));
                kq.setNhipTim(rs.getInt("NhipTim"));
                kq.setBmi(rs.getDouble("BMI"));
                kq.setNgayTaiKham(rs.getTimestamp("NgayTaiKham"));
                kq.setTrangThai(rs.getString("TrangThai"));
                return kq;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public boolean insert(KetQuaKham kq) {
        String sql = "INSERT INTO KetQuaKham (MaPhieuKham, TrieuChung, ChanDoanSoBo, KetLuan, HuongDieuTri, GhiChu, HuyetAp, NhietDo, NhipTim, BMI, NgayTaiKham, TrangThai) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, kq.getMaPhieuKham());
            stmt.setString(2, kq.getTrieuChung());
            stmt.setString(3, kq.getChanDoanSoBo());
            stmt.setString(4, kq.getKetLuan());
            stmt.setString(5, kq.getHuongDieuTri());
            stmt.setString(6, kq.getGhiChu());
            stmt.setString(7, kq.getHuyetAp());
            stmt.setDouble(8, kq.getNhietDo());
            stmt.setInt(9, kq.getNhipTim());
            stmt.setDouble(10, kq.getBmi());
            stmt.setTimestamp(11, kq.getNgayTaiKham());
            stmt.setString(12, kq.getTrangThai());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean update(KetQuaKham kq) {
        String sql = "UPDATE KetQuaKham SET TrieuChung=?, ChanDoanSoBo=?, KetLuan=?, HuongDieuTri=?, GhiChu=?, HuyetAp=?, NhietDo=?, NhipTim=?, BMI=?, NgayTaiKham=?, TrangThai=? WHERE MaKetQuaKham=?";
        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, kq.getTrieuChung());
            stmt.setString(2, kq.getChanDoanSoBo());
            stmt.setString(3, kq.getKetLuan());
            stmt.setString(4, kq.getHuongDieuTri());
            stmt.setString(5, kq.getGhiChu());
            stmt.setString(6, kq.getHuyetAp());
            stmt.setDouble(7, kq.getNhietDo());
            stmt.setInt(8, kq.getNhipTim());
            stmt.setDouble(9, kq.getBmi());
            stmt.setTimestamp(10, kq.getNgayTaiKham());
            stmt.setString(11, kq.getTrangThai());
            stmt.setInt(12, kq.getMaKetQuaKham());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM KetQuaKham WHERE MaKetQuaKham = ?";
        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public List<KetQuaKham> getKetQuaByBenhNhan(int maBenhNhan) {
        List<KetQuaKham> list = new ArrayList<>();
        String sql = "SELECT * FROM KetQuaKham kq " +
                "JOIN PhieuKham pk ON kq.MaPhieuKham = pk.MaPhieuKham " +
                "WHERE pk.MaBenhNhan = ?";

        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, maBenhNhan);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                KetQuaKham kq = new KetQuaKham();
                kq.setMaKetQuaKham(rs.getInt("MaKetQuaKham"));
                kq.setMaPhieuKham(rs.getInt("MaPhieuKham"));
                kq.setTrieuChung(rs.getString("TrieuChung"));
                kq.setChanDoanSoBo(rs.getString("ChanDoanSoBo"));
                kq.setKetLuan(rs.getString("KetLuan"));
                kq.setHuongDieuTri(rs.getString("HuongDieuTri"));
                kq.setGhiChu(rs.getString("GhiChu"));
                kq.setHuyetAp(rs.getString("HuyetAp"));
                kq.setNhietDo(rs.getDouble("NhietDo"));
                kq.setNhipTim(rs.getInt("NhipTim"));
                kq.setBmi(rs.getDouble("BMI"));
                kq.setNgayTaiKham(rs.getTimestamp("NgayTaiKham"));
                kq.setTrangThai(rs.getString("TrangThai"));

                list.add(kq);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }
}
