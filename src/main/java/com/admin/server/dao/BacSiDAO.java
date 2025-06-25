package com.admin.server.dao;

import com.admin.shared.model.BacSi;
import com.admin.server.utils.JDBCUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BacSiDAO {
    private final Connection conn;

    public BacSiDAO() {
        this.conn = JDBCUtil.getConnection();
    }

    public boolean insert(BacSi bs) {
        String sql = "INSERT INTO BacSi (tenBacSi, chuyenKhoa, soDienThoai, email) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, bs.getTenBacSi());
            ps.setString(2, bs.getChuyenKhoa());
            ps.setString(3, bs.getSoDienThoai());
            ps.setString(4, bs.getEmail());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public int insertAndReturnId(BacSi bs) {
        String sql = "INSERT INTO BacSi (TenBacSi, ChuyenKhoa, SoDienThoai, Email) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, bs.getTenBacSi());
            stmt.setString(2, bs.getChuyenKhoa());
            stmt.setString(3, bs.getSoDienThoai());
            stmt.setString(4, bs.getEmail());
            int affected = stmt.executeUpdate();
            if (affected == 0) return 0;

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public List<BacSi> findAll() {
        List<BacSi> list = new ArrayList<>();
        String sql = "SELECT * FROM BacSi";
        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                list.add(mapResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean update(BacSi bs) {
        String sql = "UPDATE BacSi SET tenBacSi = ?, chuyenKhoa = ?, soDienThoai = ?, email = ? WHERE maBacSi = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, bs.getTenBacSi());
            ps.setString(2, bs.getChuyenKhoa());
            ps.setString(3, bs.getSoDienThoai());
            ps.setString(4, bs.getEmail());
            ps.setInt(5, bs.getMaBacSi());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(int maBacSi) {
        String sql = "DELETE FROM BacSi WHERE maBacSi = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, maBacSi);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private BacSi mapResultSet(ResultSet rs) throws SQLException {
        BacSi bs = new BacSi();
        bs.setMaBacSi(rs.getInt("maBacSi"));
        bs.setTenBacSi(rs.getString("tenBacSi"));
        bs.setChuyenKhoa(rs.getString("chuyenKhoa"));
        bs.setSoDienThoai(rs.getString("soDienThoai"));
        bs.setEmail(rs.getString("email"));
        return bs;
    }

    public Map<String, Integer> laySoBacSiTheoChuyenKhoa() {
        Map<String, Integer> result = new HashMap<>();
        String sql = "SELECT ChuyenKhoa, COUNT(*) AS SoBacSi FROM BacSi GROUP BY ChuyenKhoa";

        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                String chuyenKhoa = rs.getString("ChuyenKhoa");
                int soBacSi = rs.getInt("SoBacSi");
                result.put(chuyenKhoa, soBacSi);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }
}
