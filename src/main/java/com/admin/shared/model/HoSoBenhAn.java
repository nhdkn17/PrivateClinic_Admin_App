package com.admin.shared.model;

public class HoSoBenhAn {
    private int maHoSo;
    private int maLichKham;
    private int maBenhNhan;
    private String chanDoan;
    private String ghiChu;

    public HoSoBenhAn(int maHoSo, int maLichKham, int maBenhNhan, String chanDoan, String ghiChu) {
        this.maHoSo = maHoSo;
        this.maLichKham = maLichKham;
        this.maBenhNhan = maBenhNhan;
        this.chanDoan = chanDoan;
        this.ghiChu = ghiChu;
    }

    public HoSoBenhAn() {
    }

    public int getMaHoSo() {
        return maHoSo;
    }

    public void setMaHoSo(int maHoSo) {
        this.maHoSo = maHoSo;
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

    public String getChanDoan() {
        return chanDoan;
    }

    public void setChanDoan(String chanDoan) {
        this.chanDoan = chanDoan;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    @Override
    public String toString() {
        return "HoSoBenhAn{" +
                "maHoSo=" + maHoSo +
                ", maLichKham=" + maLichKham +
                ", maBenhNhan=" + maBenhNhan +
                ", chanDoan='" + chanDoan + '\'' +
                ", ghiChu='" + ghiChu + '\'' +
                '}';
    }
}
