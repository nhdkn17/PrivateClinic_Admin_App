package com.admin.shared.model;

import javafx.beans.property.*;

public class MedicineRow {
    private final IntegerProperty maThuoc;
    private final StringProperty tenThuoc;
    private final StringProperty donVi;
    private final IntegerProperty soLuongKe;
    private final IntegerProperty soLuongTon;
    private final IntegerProperty soLuongLay;
    private final DoubleProperty gia;
    private final DoubleProperty thanhTien;

    public MedicineRow(int maThuoc, String tenThuoc, String donVi, int soLuongKe, int soLuongTon, double gia) {
        this.maThuoc = new SimpleIntegerProperty(maThuoc);
        this.tenThuoc = new SimpleStringProperty(tenThuoc);
        this.donVi = new SimpleStringProperty(donVi);
        this.soLuongKe = new SimpleIntegerProperty(soLuongKe);
        this.soLuongTon = new SimpleIntegerProperty(soLuongTon);
        this.soLuongLay = new SimpleIntegerProperty(0);
        this.gia = new SimpleDoubleProperty(gia);
        this.thanhTien = new SimpleDoubleProperty(0.0);
    }

    public int getMaThuoc() {
        return maThuoc.get();
    }

    public IntegerProperty maThuocProperty() {
        return maThuoc;
    }

    public String getTenThuoc() {
        return tenThuoc.get();
    }

    public StringProperty tenThuocProperty() {
        return tenThuoc;
    }

    public String getDonVi() {
        return donVi.get();
    }

    public StringProperty donViProperty() {
        return donVi;
    }

    public int getSoLuongKe() {
        return soLuongKe.get();
    }

    public IntegerProperty soLuongKeProperty() {
        return soLuongKe;
    }

    public int getSoLuongTon() {
        return soLuongTon.get();
    }

    public IntegerProperty soLuongTonProperty() {
        return soLuongTon;
    }

    public int getSoLuongLay() {
        return soLuongLay.get();
    }

    public void setSoLuongLay(int soLuongLay) {
        this.soLuongLay.set(soLuongLay);
        this.thanhTien.set(soLuongLay * this.gia.get());
    }

    public IntegerProperty soLuongLayProperty() {
        return soLuongLay;
    }

    public double getGia() {
        return gia.get();
    }

    public DoubleProperty giaProperty() {
        return gia;
    }

    public double getThanhTien() {
        return thanhTien.get();
    }

    public DoubleProperty thanhTienProperty() {
        return thanhTien;
    }

    @Override
    public String toString() {
        return "MedicineRow{" +
                "maThuoc=" + maThuoc +
                ", tenThuoc=" + tenThuoc +
                ", donVi=" + donVi +
                ", soLuongKe=" + soLuongKe +
                ", soLuongTon=" + soLuongTon +
                ", soLuongLay=" + soLuongLay +
                ", gia=" + gia +
                ", thanhTien=" + thanhTien +
                '}';
    }
}
