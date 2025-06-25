package com.admin.shared.model;

public class Thuoc {
    private int maThuoc;
    private String tenThuoc;
    private String loaiThuoc;
    private String donVi;
    private double gia;
    private int soLuongTon;

    public Thuoc(int maThuoc, String tenThuoc, String loaiThuoc, String donVi, double gia, int soLuongTon) {
        this.maThuoc = maThuoc;
        this.tenThuoc = tenThuoc;
        this.loaiThuoc = loaiThuoc;
        this.donVi = donVi;
        this.gia = gia;
        this.soLuongTon = soLuongTon;
    }

    public Thuoc() {
    }

    public int getMaThuoc() {
        return maThuoc;
    }

    public void setMaThuoc(int maThuoc) {
        this.maThuoc = maThuoc;
    }

    public String getTenThuoc() {
        return tenThuoc;
    }

    public void setTenThuoc(String tenThuoc) {
        this.tenThuoc = tenThuoc;
    }

    public String getLoaiThuoc() {
        return loaiThuoc;
    }

    public void setLoaiThuoc(String loaiThuoc) {
        this.loaiThuoc = loaiThuoc;
    }

    public String getDonVi() {
        return donVi;
    }

    public void setDonVi(String donVi) {
        this.donVi = donVi;
    }

    public double getGia() {
        return gia;
    }

    public void setGia(double gia) {
        this.gia = gia;
    }

    public int getSoLuongTon() {
        return soLuongTon;
    }

    public void setSoLuongTon(int soLuongTon) {
        this.soLuongTon = soLuongTon;
    }

    @Override
    public String toString() {
        return "Thuoc{" +
                "maThuoc=" + maThuoc +
                ", tenThuoc='" + tenThuoc + '\'' +
                ", loaiThuoc='" + loaiThuoc + '\'' +
                ", donVi='" + donVi + '\'' +
                ", gia=" + gia +
                ", soLuongTon=" + soLuongTon +
                '}';
    }
}
