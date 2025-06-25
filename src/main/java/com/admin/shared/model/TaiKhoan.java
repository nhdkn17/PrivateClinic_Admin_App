package com.admin.shared.model;

public class TaiKhoan {
    private Integer maTaiKhoan;
    private String email;
    private String matKhau;
    private String vaiTro; // ADMIN | LE_TAN | BAC_SI
    private Integer maNhanVien;
    private Integer maBacSi;
    private boolean trangThai;

    public TaiKhoan() {
        setTrangThai(false);
    }

    public TaiKhoan(Integer maTaiKhoan, String email, String matKhau, String vaiTro, Integer maNhanVien, Integer maBacSi, boolean trangThai) {
        this.maTaiKhoan = maTaiKhoan;
        this.email = email;
        this.matKhau = matKhau;
        this.vaiTro = vaiTro;
        this.maNhanVien = maNhanVien;
        this.maBacSi = maBacSi;
        this.trangThai = trangThai;
    }

    public TaiKhoan(String email, String matKhau, String vaiTro, Integer maNhanVien, Integer maBacSi, boolean trangThai) {
        this.email = email;
        this.matKhau = matKhau;
        this.vaiTro = vaiTro;
        this.maNhanVien = maNhanVien;
        this.maBacSi = maBacSi;
        this.trangThai = trangThai;
    }

    public Integer getMaTaiKhoan() {
        return maTaiKhoan;
    }

    public void setMaTaiKhoan(Integer maTaiKhoan) {
        this.maTaiKhoan = maTaiKhoan;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    public String getVaiTro() {
        return vaiTro;
    }

    public void setVaiTro(String vaiTro) {
        this.vaiTro = vaiTro;
    }

    public Integer getMaNhanVien() {
        return maNhanVien;
    }

    public void setMaNhanVien(Integer maNhanVien) {
        this.maNhanVien = maNhanVien;
    }

    public Integer getMaBacSi() {
        return maBacSi;
    }

    public void setMaBacSi(Integer maBacSi) {
        this.maBacSi = maBacSi;
    }

    public boolean isTrangThai() {
        return trangThai;
    }

    public void setTrangThai(boolean trangThai) {
        this.trangThai = trangThai;
    }
}

