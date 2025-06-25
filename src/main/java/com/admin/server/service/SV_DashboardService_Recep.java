package com.admin.server.service;

import com.admin.server.dao.DashboardService;
import com.admin.shared.protocol.Action;
import com.admin.shared.protocol.Response;

import java.sql.SQLException;
import java.util.Map;

public class SV_DashboardService_Recep {
    private final DashboardService dashboardDAO = new DashboardService();

    public Response handle(String action, Map<String, Object> data) {
        return switch (action) {
            case Action.DASHBOARD_COUNT_PATIENT_TODAY -> handleCountPatientToday();
            case Action.DASHBOARD_COUNT_APPOINTMENT_TODAY -> handleCountAppointmentToday();
            case Action.DASHBOARD_COUNT_PRESCRIPTION_TODAY -> handleCountPrescriptionToday();
            case Action.DASHBOARD_REVENUE_TODAY -> handleRevenueToday();
            case Action.DASHBOARD_REVENUE_MONTH -> handleRevenueByDayInMonth(data);
            default -> new Response("fail", "Không hỗ trợ action dashboard: " + action, null);
        };
    }

    private Response handleCountPatientToday() {
        try {
            int count = dashboardDAO.getSoBenhNhanHomNay();
            return new Response("success", "Số bệnh nhân hôm nay", count);
        } catch (SQLException e) {
            return new Response("fail", "Lỗi khi truy vấn số bệnh nhân hôm nay", null);
        }
    }

    private Response handleCountAppointmentToday() {
        try {
            int count = dashboardDAO.getSoLichHenHomNay();
            return new Response("success", "Số lịch hẹn hôm nay", count);
        } catch (SQLException e) {
            return new Response("fail", "Lỗi khi truy vấn số lịch hẹn hôm nay", null);
        }
    }

    private Response handleCountPrescriptionToday() {
        try {
            int count = dashboardDAO.getSoDonThuocHomNay();
            return new Response("success", "Số đơn thuốc hôm nay", count);
        } catch (SQLException e) {
            return new Response("fail", "Lỗi khi truy vấn số đơn thuốc hôm nay", null);
        }
    }

    private Response handleRevenueToday() {
        try {
            String doanhThu = dashboardDAO.getFormattedDoanhThuHomNay();
            return new Response("success", "Doanh thu hôm nay", doanhThu);
        } catch (SQLException e) {
            return new Response("fail", "Lỗi khi truy vấn doanh thu hôm nay", null);
        }
    }

    private Response handleRevenueByDayInMonth(Map<String, Object> data) {
        if (data == null || !data.containsKey("thang") || !data.containsKey("nam")) {
            return new Response("fail", "Thiếu thông tin tháng/năm", null);
        }

        try {
            int thang = ((Number) data.get("thang")).intValue();
            int nam = ((Number) data.get("nam")).intValue();

            Map<Integer, ?> doanhThuTheoNgay = dashboardDAO.getTongTienTheoNgayTrongThang(thang, nam);
            return new Response("success", "Doanh thu theo ngày trong tháng", doanhThuTheoNgay);
        } catch (SQLException e) {
            return new Response("fail", "Lỗi khi truy vấn doanh thu theo tháng", null);
        }
    }
}
