package com.admin.server.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

public class DashboardStatsDAO {
    private Connection conn;

    public DashboardStatsDAO(Connection conn) {
        this.conn = conn;
    }

    public int getSoBenhNhanHomNay() throws SQLException {
        String sql = """
        SELECT COUNT(DISTINCT MaBenhNhan) FROM (
            SELECT MaBenhNhan FROM LichKham WHERE CAST(GioBatDau AS DATE) = CAST(GETDATE() AS DATE)
            UNION
            SELECT MaBenhNhan FROM BenhNhan WHERE CAST(NgayKham AS DATE) = CAST(GETDATE() AS DATE)
        ) AS Combined
        """;
        try (PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            return rs.next() ? rs.getInt(1) : 0;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }


    public int getSoLichHenHomNay() throws SQLException {
        String sql = "SELECT COUNT(*) FROM LichKham WHERE CAST(GioBatDau AS DATE) = CAST(GETDATE() AS DATE)";
        try (PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            return rs.next() ? rs.getInt(1) : 0;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public int getSoDonThuocHomNay() throws SQLException {
        String sql = "SELECT COUNT(*) FROM ToaThuoc WHERE CAST(NgayLayThuoc AS DATE) = CAST(GETDATE() AS DATE)";
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            return rs.next() ? rs.getInt(1) : 0;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public BigDecimal getRawDoanhThuHomNay() throws SQLException {
        String sql = "SELECT SUM(ct.ThanhTien) " +
                "FROM ChiTietToaThuoc ct " +
                "JOIN ToaThuoc tt ON ct.MaToaThuoc = tt.MaToaThuoc " +
                "WHERE CAST(tt.NgayLayThuoc AS DATE) = CAST(GETDATE() AS DATE)";
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                BigDecimal result = rs.getBigDecimal(1);
                return result != null ? result : BigDecimal.ZERO;
            }
            return BigDecimal.ZERO;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public String getFormattedDoanhThuHomNay() throws SQLException {
        BigDecimal raw = getRawDoanhThuHomNay();

        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setGroupingSeparator(',');
        symbols.setDecimalSeparator('.');

        DecimalFormat formatter = new DecimalFormat("#,###.##", symbols);

        return formatter.format(raw);
    }
}
