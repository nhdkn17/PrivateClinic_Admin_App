package com.admin.shared.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ToaThuoc {
    private int maToaThuoc;
    private int maBenhNhan;
    private LocalDate ngayLayThuoc;

    private List<ChiTietToaThuoc> chiTietToaThuocList;

    public ToaThuoc() {
        chiTietToaThuocList = new ArrayList<>();
    }

    public ToaThuoc(int maBenhNhan, LocalDate ngayLayThuoc) {
        this.maBenhNhan = maBenhNhan;
        this.ngayLayThuoc = ngayLayThuoc;
    }

    public ToaThuoc(int maToaThuoc, int maBenhNhan, LocalDate ngayLayThuoc, List<ChiTietToaThuoc> chiTietToaThuocList) {
        this.maToaThuoc = maToaThuoc;
        this.maBenhNhan = maBenhNhan;
        this.ngayLayThuoc = ngayLayThuoc;
        this.chiTietToaThuocList = chiTietToaThuocList;
    }

    public int getMaToaThuoc() {
        return maToaThuoc;
    }

    public void setMaToaThuoc(int maToaThuoc) {
        this.maToaThuoc = maToaThuoc;
    }

    public int getMaBenhNhan() {
        return maBenhNhan;
    }

    public void setMaBenhNhan(int maBenhNhan) {
        this.maBenhNhan = maBenhNhan;
    }

    public LocalDate getNgayLayThuoc() {
        return ngayLayThuoc;
    }

    public void setNgayLayThuoc(LocalDate ngayLayThuoc) {
        this.ngayLayThuoc = ngayLayThuoc;
    }

    public List<ChiTietToaThuoc> getChiTietToaThuocList() {
        return chiTietToaThuocList;
    }

    public void setChiTietToaThuocList(List<ChiTietToaThuoc> chiTietToaThuocList) {
        this.chiTietToaThuocList = chiTietToaThuocList;
    }
}
