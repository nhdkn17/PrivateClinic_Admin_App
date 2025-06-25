package com.admin.shared.model;

public class BacSi {
    private int maBacSi;
    private String tenBacSi;
    private String chuyenKhoa;
    private String soDienThoai;
    private String email;

    public BacSi(int maBacSi, String tenBacSi, String chuyenKhoa, String soDienThoai, String email) {
        this.maBacSi = maBacSi;
        this.tenBacSi = tenBacSi;
        this.chuyenKhoa = chuyenKhoa;
        this.soDienThoai = soDienThoai;
        this.email = email;
    }

    public BacSi(String tenBacSi, String chuyenKhoa, String soDienThoai, String email) {
        this.tenBacSi = tenBacSi;
        this.chuyenKhoa = chuyenKhoa;
        this.soDienThoai = soDienThoai;
        this.email = email;
    }

    public BacSi() {
    }

    public int getMaBacSi() {
        return maBacSi;
    }

    public void setMaBacSi(int maBacSi) {
        this.maBacSi = maBacSi;
    }

    public String getTenBacSi() {
        return tenBacSi;
    }

    public void setTenBacSi(String tenBacSi) {
        this.tenBacSi = tenBacSi;
    }

    public String getChuyenKhoa() {
        return chuyenKhoa;
    }

    public void setChuyenKhoa(String chuyenKhoa) {
        this.chuyenKhoa = chuyenKhoa;
    }

    public String getSoDienThoai() {
        return soDienThoai;
    }

    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "BacSi{" +
                "maBacSi=" + maBacSi +
                ", tenBacSi='" + tenBacSi + '\'' +
                ", chuyenKhoa='" + chuyenKhoa + '\'' +
                ", soDienThoai='" + soDienThoai + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
