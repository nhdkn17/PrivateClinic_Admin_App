package com.admin.server.service;

import com.admin.server.dao.LichKhamDAO;
import com.admin.shared.model.LichKham;
import com.admin.shared.model.LichKhamModel;
import com.admin.shared.protocol.Action;
import com.admin.shared.protocol.Response;
import com.admin.shared.util.JsonUtils;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class SV_AppointmentService {
    private final LichKhamDAO lichKhamDAO = new LichKhamDAO();

    public Response handle(String action, Map<String, Object> data) {
        return switch (action) {
            case Action.APPOINTMENT_ADD -> handleAdd(data);
            case Action.APPOINTMENT_UPDATE -> handleUpdate(data);
            case Action.APPOINTMENT_DELETE -> handleDelete(data);
            case Action.APPOINTMENT_FIND_ALL -> handleFindAll();
            case Action.APPOINTMENT_FIND_BY_DATE -> handleFindByDate(data);
            case Action.APPOINTMENT_ENTITY_BY_DATE -> handleFindEntitiesByDate(data);
            default -> new Response("fail", "Không hỗ trợ action: " + action, null);
        };
    }

    private Response handleAdd(Map<String, Object> data) {
        LichKham lk = parseLichKham(data.get("appointment"));
        if (lk == null) return new Response("fail", "Dữ liệu không hợp lệ", null);

        boolean success = lichKhamDAO.add(lk);
        return success
                ? new Response("success", "Thêm lịch khám thành công", null)
                : new Response("fail", "Thêm lịch khám thất bại", null);
    }

    private Response handleUpdate(Map<String, Object> data) {
        LichKham lk = parseLichKham(data.get("appointment"));
        if (lk == null) return new Response("fail", "Dữ liệu không hợp lệ", null);

        boolean success = lichKhamDAO.update(lk);
        return success
                ? new Response("success", "Cập nhật lịch khám thành công", null)
                : new Response("fail", "Không thể cập nhật lịch khám", null);
    }

    private Response handleDelete(Map<String, Object> data) {
        if (data == null || !data.containsKey("maLichKham")) {
            return new Response("fail", "Thiếu mã lịch khám", null);
        }
        int maLichKham = ((Double) data.get("maLichKham")).intValue();
        boolean success = lichKhamDAO.delete(maLichKham);
        return success
                ? new Response("success", "Xóa lịch khám thành công", null)
                : new Response("fail", "Không thể xóa lịch khám", null);
    }

    private Response handleFindAll() {
        List<LichKham> list = lichKhamDAO.getAll();
        return new Response("success", "Danh sách lịch khám hôm nay", list);
    }

    private Response handleFindByDate(Map<String, Object> data) {
        if (data == null || !data.containsKey("date")) {
            return new Response("fail", "Thiếu ngày cần tìm", null);
        }

        try {
            String dateStr = (String) data.get("date");
            LocalDate date = LocalDate.parse(dateStr);

            List<LichKham> list = lichKhamDAO.getLichKhamEntitiesTheoNgay(date);

            return new Response("success", "Tìm kiếm lịch theo ngày thành công", list);
        } catch (Exception e) {
            e.printStackTrace();
            return new Response("fail", "Lỗi định dạng ngày", null);
        }
    }


    private Response handleFindEntitiesByDate(Map<String, Object> data) {
        try {
            LocalDate date = LocalDate.parse((String) data.get("date"));
            List<LichKham> list = lichKhamDAO.getLichKhamEntitiesTheoNgay(date);
            return new Response("success", "Tải lịch khám theo ngày (entity)", list);
        } catch (Exception e) {
            e.printStackTrace();
            return new Response("fail", "Lỗi khi lấy lịch khám theo ngày", null);
        }
    }

    private LichKham parseLichKham(Object raw) {
        try {
            String json = JsonUtils.toJson(raw);
            return JsonUtils.fromJson(json, LichKham.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
