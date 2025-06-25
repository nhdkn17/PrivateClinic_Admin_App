package com.admin.shared.model;

import java.math.BigDecimal;

public class DashboardStats {
    private int soBenhNhan;
    private int soLichHenHomNay;
    private int soDonThuoc;
    private BigDecimal doanhThuHomNay;

    public DashboardStats() {
    }

    public DashboardStats(int soBenhNhan, int soLichHenHomNay, int soDonThuoc, BigDecimal doanhThuHomNay) {
        this.soBenhNhan = soBenhNhan;
        this.soLichHenHomNay = soLichHenHomNay;
        this.soDonThuoc = soDonThuoc;
        this.doanhThuHomNay = doanhThuHomNay;
    }

    public int getSoBenhNhan() {
        return soBenhNhan;
    }

    public void setSoBenhNhan(int soBenhNhan) {
        this.soBenhNhan = soBenhNhan;
    }

    public int getSoLichHenHomNay() {
        return soLichHenHomNay;
    }

    public void setSoLichHenHomNay(int soLichHenHomNay) {
        this.soLichHenHomNay = soLichHenHomNay;
    }

    public int getSoDonThuoc() {
        return soDonThuoc;
    }

    public void setSoDonThuoc(int soDonThuoc) {
        this.soDonThuoc = soDonThuoc;
    }

    public BigDecimal getDoanhThuHomNay() {
        return doanhThuHomNay;
    }

    public void setDoanhThuHomNay(BigDecimal doanhThuHomNay) {
        this.doanhThuHomNay = doanhThuHomNay;
    }
}
