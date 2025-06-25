package com.admin.shared.model;

import javafx.beans.property.*;

import java.time.LocalDateTime;

public class LichKhamModel {
    private final IntegerProperty maLichKham;
    private final StringProperty tenBenhNhan;
    private final StringProperty tenBacSi;
    private final ObjectProperty<LocalDateTime> gioBatDau;
    private final StringProperty trangThai;
    private final StringProperty ghiChu;

    public LichKhamModel(IntegerProperty maLichKham, StringProperty tenBenhNhan, StringProperty tenBacSi, ObjectProperty<LocalDateTime> gioBatDau, StringProperty trangThai, StringProperty ghiChu) {
        this.maLichKham = maLichKham;
        this.tenBenhNhan = tenBenhNhan;
        this.tenBacSi = tenBacSi;
        this.gioBatDau = gioBatDau;
        this.trangThai = trangThai;
        this.ghiChu = ghiChu;
    }

    public LichKhamModel(int maLichKham, String tenBenhNhan, String tenBacSi, LocalDateTime gioBatDau, String trangThai, String ghiChu) {
        this.maLichKham = new SimpleIntegerProperty(maLichKham);
        this.tenBenhNhan = new SimpleStringProperty(tenBenhNhan);
        this.tenBacSi = new SimpleStringProperty(tenBacSi);
        this.gioBatDau = new SimpleObjectProperty<>(gioBatDau);
        this.trangThai = new SimpleStringProperty(trangThai);
        this.ghiChu = new SimpleStringProperty(ghiChu);
    }

    public int getMaLichKham() {
        return maLichKham.get();
    }

    public IntegerProperty maLichKhamProperty() {
        return maLichKham;
    }

    public String getTenBenhNhan() {
        return tenBenhNhan.get();
    }

    public StringProperty tenBenhNhanProperty() {
        return tenBenhNhan;
    }

    public String getTenBacSi() {
        return tenBacSi.get();
    }

    public StringProperty tenBacSiProperty() {
        return tenBacSi;
    }

    public LocalDateTime getGioBatDau() {
        return gioBatDau.get();
    }

    public ObjectProperty<LocalDateTime> gioBatDauProperty() {
        return gioBatDau;
    }

    public String getTrangThai() {
        return trangThai.get();
    }

    public StringProperty trangThaiProperty() {
        return trangThai;
    }

    public String getGhiChu() {
        return ghiChu.get();
    }

    public StringProperty ghiChuProperty() {
        return ghiChu;
    }
}
