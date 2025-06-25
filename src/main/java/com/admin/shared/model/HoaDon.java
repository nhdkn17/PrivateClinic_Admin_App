package com.admin.shared.model;

import java.sql.Timestamp;

public class HoaDon {
    private int maHoaDon;
    private int maLichKham;
    private int maBenhNhan;
    private double tongTien;
    private String trangThaiThanhToan;
    private Timestamp ngayTao;

    public HoaDon(int maHoaDon, int maLichKham, int maBenhNhan, double tongTien, String trangThaiThanhToan, Timestamp ngayTao) {
        this.maHoaDon = maHoaDon;
        this.maLichKham = maLichKham;
        this.maBenhNhan = maBenhNhan;
        this.tongTien = tongTien;
        this.trangThaiThanhToan = trangThaiThanhToan;
        this.ngayTao = ngayTao;
    }

    public HoaDon() {
    }

    public int getMaHoaDon() {
        return maHoaDon;
    }

    public void setMaHoaDon(int maHoaDon) {
        this.maHoaDon = maHoaDon;
    }

    public int getMaLichKham() {
        return maLichKham;
    }

    public void setMaLichKham(int maLichKham) {
        this.maLichKham = maLichKham;
    }

    public int getMaBenhNhan() {
        return maBenhNhan;
    }

    public void setMaBenhNhan(int maBenhNhan) {
        this.maBenhNhan = maBenhNhan;
    }

    public double getTongTien() {
        return tongTien;
    }

    public void setTongTien(double tongTien) {
        this.tongTien = tongTien;
    }

    public String getTrangThaiThanhToan() {
        return trangThaiThanhToan;
    }

    public void setTrangThaiThanhToan(String trangThaiThanhToan) {
        this.trangThaiThanhToan = trangThaiThanhToan;
    }

    public Timestamp getNgayTao() {
        return ngayTao;
    }

    public void setNgayTao(Timestamp ngayTao) {
        this.ngayTao = ngayTao;
    }

    @Override
    public String toString() {
        return "HoaDon{" +
                "maHoaDon=" + maHoaDon +
                ", maLichKham=" + maLichKham +
                ", maBenhNhan=" + maBenhNhan +
                ", tongTien=" + tongTien +
                ", trangThaiThanhToan='" + trangThaiThanhToan + '\'' +
                ", ngayTao=" + ngayTao +
                '}';
    }
}
