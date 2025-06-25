package com.admin.shared.model;

import java.math.BigDecimal;

public class ChiTietToaThuoc {
    private int maChiTiet;
    private int maToaThuoc;
    private int maThuoc;
    private int soLuong;
    private String lieuLuong;
    private BigDecimal thanhTien;

    public ChiTietToaThuoc() {
    }

    public ChiTietToaThuoc(int maToaThuoc, int maThuoc, int soLuong, String lieuLuong, BigDecimal thanhTien) {
        this.maToaThuoc = maToaThuoc;
        this.maThuoc = maThuoc;
        this.soLuong = soLuong;
        this.lieuLuong = lieuLuong;
        this.thanhTien = thanhTien;
    }

    public int getMaChiTiet() {
        return maChiTiet;
    }

    public void setMaChiTiet(int maChiTiet) {
        this.maChiTiet = maChiTiet;
    }

    public int getMaToaThuoc() {
        return maToaThuoc;
    }

    public void setMaToaThuoc(int maToaThuoc) {
        this.maToaThuoc = maToaThuoc;
    }

    public int getMaThuoc() {
        return maThuoc;
    }

    public void setMaThuoc(int maThuoc) {
        this.maThuoc = maThuoc;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public String getLieuLuong() {
        return lieuLuong;
    }

    public void setLieuLuong(String lieuLuong) {
        this.lieuLuong = lieuLuong;
    }

    public BigDecimal getThanhTien() {
        return thanhTien;
    }

    public void setThanhTien(BigDecimal thanhTien) {
        this.thanhTien = thanhTien;
    }
}
