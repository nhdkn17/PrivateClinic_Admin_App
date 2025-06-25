package com.admin.shared.model;

import java.sql.Date;

public class PhieuKham {
    private int maPhieuKham;
    private int maBenhNhan;
    private int maBacSi;
    private Date ngayKham;
    private String trieuChung;
    private String chanDoan;
    private String trangThai;

    public PhieuKham() {}

    public PhieuKham(int maPhieuKham, int maBenhNhan, int maBacSi, Date ngayKham, String trieuChung, String chanDoan, String trangThai) {
        this.maPhieuKham = maPhieuKham;
        this.maBenhNhan = maBenhNhan;
        this.maBacSi = maBacSi;
        this.ngayKham = ngayKham;
        this.trieuChung = trieuChung;
        this.chanDoan = chanDoan;
        this.trangThai = trangThai;
    }

    public int getMaPhieuKham() {
        return maPhieuKham;
    }

    public void setMaPhieuKham(int maPhieuKham) {
        this.maPhieuKham = maPhieuKham;
    }

    public int getMaBenhNhan() {
        return maBenhNhan;
    }

    public void setMaBenhNhan(int maBenhNhan) {
        this.maBenhNhan = maBenhNhan;
    }

    public int getMaBacSi() {
        return maBacSi;
    }

    public void setMaBacSi(int maBacSi) {
        this.maBacSi = maBacSi;
    }

    public Date getNgayKham() {
        return ngayKham;
    }

    public void setNgayKham(Date ngayKham) {
        this.ngayKham = ngayKham;
    }

    public String getTrieuChung() {
        return trieuChung;
    }

    public void setTrieuChung(String trieuChung) {
        this.trieuChung = trieuChung;
    }

    public String getChanDoan() {
        return chanDoan;
    }

    public void setChanDoan(String chanDoan) {
        this.chanDoan = chanDoan;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    @Override
    public String toString() {
        return "PhieuKham{" +
                "maPhieuKham=" + maPhieuKham +
                ", maBenhNhan=" + maBenhNhan +
                ", maBacSi=" + maBacSi +
                ", ngayKham=" + ngayKham +
                ", trieuChung='" + trieuChung + '\'' +
                ", chanDoan='" + chanDoan + '\'' +
                ", trangThai='" + trangThai + '\'' +
                '}';
    }
}
