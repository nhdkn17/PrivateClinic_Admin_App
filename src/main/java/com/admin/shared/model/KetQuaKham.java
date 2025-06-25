package com.admin.shared.model;

import java.sql.Timestamp;

public class KetQuaKham {
    private int maKetQuaKham;
    private int maPhieuKham;
    private Timestamp ngayKham;

    private String trieuChung;
    private String chanDoanSoBo;
    private String ketLuan;
    private String huongDieuTri;
    private String ghiChu;

    private String huyetAp;
    private double nhietDo;
    private int nhipTim;
    private double bmi;
    private String trangThai;
    private Timestamp ngayTaiKham;

    public KetQuaKham(int maKetQuaKham, int maPhieuKham, Timestamp ngayKham, String trieuChung, String chanDoanSoBo, String ketLuan, String huongDieuTri, String ghiChu, String huyetAp, double nhietDo, int nhipTim, double bmi, String trangThai, Timestamp ngayTaiKham) {
        this.maKetQuaKham = maKetQuaKham;
        this.maPhieuKham = maPhieuKham;
        this.ngayKham = ngayKham;
        this.trieuChung = trieuChung;
        this.chanDoanSoBo = chanDoanSoBo;
        this.ketLuan = ketLuan;
        this.huongDieuTri = huongDieuTri;
        this.ghiChu = ghiChu;
        this.huyetAp = huyetAp;
        this.nhietDo = nhietDo;
        this.nhipTim = nhipTim;
        this.bmi = bmi;
        this.trangThai = trangThai;
        this.ngayTaiKham = ngayTaiKham;
    }

    public KetQuaKham() {
    }

    public double getBmi() {
        return bmi;
    }

    public String getChanDoanSoBo() {
        return chanDoanSoBo;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public String getHuongDieuTri() {
        return huongDieuTri;
    }

    public String getHuyetAp() {
        return huyetAp;
    }

    public String getKetLuan() {
        return ketLuan;
    }

    public int getMaKetQuaKham() {
        return maKetQuaKham;
    }

    public int getMaPhieuKham() {
        return maPhieuKham;
    }

    public Timestamp getNgayKham() {
        return ngayKham;
    }

    public Timestamp getNgayTaiKham() {
        return ngayTaiKham;
    }

    public double getNhietDo() {
        return nhietDo;
    }

    public int getNhipTim() {
        return nhipTim;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public String getTrieuChung() {
        return trieuChung;
    }

    public void setBmi(double bmi) {
        this.bmi = bmi;
    }

    public void setChanDoanSoBo(String chanDoanSoBo) {
        this.chanDoanSoBo = chanDoanSoBo;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public void setHuongDieuTri(String huongDieuTri) {
        this.huongDieuTri = huongDieuTri;
    }

    public void setHuyetAp(String huyetAp) {
        this.huyetAp = huyetAp;
    }

    public void setKetLuan(String ketLuan) {
        this.ketLuan = ketLuan;
    }

    public void setMaKetQuaKham(int maKetQuaKham) {
        this.maKetQuaKham = maKetQuaKham;
    }

    public void setMaPhieuKham(int maPhieuKham) {
        this.maPhieuKham = maPhieuKham;
    }

    public void setNgayKham(Timestamp ngayKham) {
        this.ngayKham = ngayKham;
    }

    public void setNgayTaiKham(Timestamp ngayTaiKham) {
        this.ngayTaiKham = ngayTaiKham;
    }

    public void setNhietDo(double nhietDo) {
        this.nhietDo = nhietDo;
    }

    public void setNhipTim(int nhipTim) {
        this.nhipTim = nhipTim;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public void setTrieuChung(String trieuChung) {
        this.trieuChung = trieuChung;
    }
}

