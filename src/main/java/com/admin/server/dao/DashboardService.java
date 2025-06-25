package com.admin.server.dao;

import com.admin.server.utils.JDBCUtil;

import java.math.BigDecimal;
import java.sql.*;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Map;
import java.util.TreeMap;

public class DashboardService {

    public int getSoBenhNhanHomNay() throws SQLException {
        String sql = """
            SELECT COUNT(DISTINCT MaBenhNhan) FROM (
                SELECT MaBenhNhan FROM LichKham WHERE CAST(GioBatDau AS DATE) = CAST(GETDATE() AS DATE)
                UNION
                SELECT MaBenhNhan FROM BenhNhan WHERE CAST(NgayKham AS DATE) = CAST(GETDATE() AS DATE)
            ) AS Combined
        """;

        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            return rs.next() ? rs.getInt(1) : 0;
        }
    }

    public int getSoLichHenHomNay() throws SQLException {
        String sql = "SELECT COUNT(*) FROM LichKham WHERE CAST(GioBatDau AS DATE) = CAST(GETDATE() AS DATE)";

        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            return rs.next() ? rs.getInt(1) : 0;
        }
    }

    public int getSoDonThuocHomNay() throws SQLException {
        String sql = "SELECT COUNT(*) FROM ToaThuoc WHERE CAST(NgayLayThuoc AS DATE) = CAST(GETDATE() AS DATE)";

        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            return rs.next() ? rs.getInt(1) : 0;
        }
    }

    public BigDecimal getRawDoanhThuHomNay() throws SQLException {
        String sql = """
            SELECT SUM(ct.ThanhTien)
            FROM ChiTietToaThuoc ct
            JOIN ToaThuoc tt ON ct.MaToaThuoc = tt.MaToaThuoc
            WHERE CAST(tt.NgayLayThuoc AS DATE) = CAST(GETDATE() AS DATE)
        """;

        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                BigDecimal result = rs.getBigDecimal(1);
                return result != null ? result : BigDecimal.ZERO;
            }
            return BigDecimal.ZERO;
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

    public Map<Integer, BigDecimal> getTongTienTheoNgayTrongThang(int thang, int nam) throws SQLException {
        Map<Integer, BigDecimal> map = new TreeMap<>();
        String sql = """
            SELECT DAY(tt.NgayLayThuoc) AS ngay, SUM(ct.ThanhTien) AS tong_tien
            FROM ChiTietToaThuoc ct
            JOIN ToaThuoc tt ON ct.MaToaThuoc = tt.MaToaThuoc
            WHERE MONTH(tt.NgayLayThuoc) = ? AND YEAR(tt.NgayLayThuoc) = ?
            GROUP BY DAY(tt.NgayLayThuoc)
            ORDER BY ngay
        """;

        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, thang);
            stmt.setInt(2, nam);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int ngay = rs.getInt("ngay");
                BigDecimal tongTien = rs.getBigDecimal("tong_tien");
                map.put(ngay, tongTien);
            }
        }

        return map;
    }
}
