package com.admin.server.service;

import com.admin.server.dao.LichKhamTuanDAO;
import com.admin.shared.model.LichKham;
import com.admin.shared.protocol.Action;
import com.admin.shared.protocol.Response;
import com.admin.shared.util.JsonUtils;

import java.util.List;
import java.util.Map;

public class SV_LichKhamTuanService {
    private final LichKhamTuanDAO lichKhamDAO = new LichKhamTuanDAO();

    public Response handle(String action, Map<String, Object> data) {
        return switch (action) {
            case Action.SCHEDULE_ADD -> handleAdd(data);
            case Action.SCHEDULE_UPDATE -> handleUpdate(data);
            case Action.SCHEDULE_DELETE -> handleDelete(data);
            case Action.SCHEDULE_FIND_ALL -> handleFindAll();
            case Action.SCHEDULE_FIND_BY_DOCTOR -> handleFindByDoctor(data);
            default -> new Response("fail", "Không hỗ trợ action: " + action, null);
        };
    }

    private Response handleAdd(Map<String, Object> data) {
        LichKham lich = parseLichKham(data.get("schedule"));
        if (lich == null) return new Response("fail", "Dữ liệu không hợp lệ", null);

        boolean success = lichKhamDAO.addLichKham(lich);
        return success
                ? new Response("success", "Thêm lịch khám thành công", null)
                : new Response("fail", "Không thể thêm lịch khám", null);
    }

    private Response handleUpdate(Map<String, Object> data) {
        LichKham lich = parseLichKham(data.get("schedule"));
        if (lich == null) return new Response("fail", "Dữ liệu không hợp lệ", null);

        boolean success = lichKhamDAO.updateLichKham(lich);
        return success
                ? new Response("success", "Cập nhật lịch khám thành công", null)
                : new Response("fail", "Không thể cập nhật lịch khám", null);
    }

    private Response handleDelete(Map<String, Object> data) {
        if (data == null || !data.containsKey("maLichKham")) {
            return new Response("fail", "Thiếu mã lịch khám để xóa", null);
        }

        int maLichKham = ((Double) data.get("maLichKham")).intValue();
        boolean success = lichKhamDAO.deleteLichKham(maLichKham);
        return success
                ? new Response("success", "Xóa lịch khám thành công", null)
                : new Response("fail", "Không thể xóa lịch khám", null);
    }

    private Response handleFindAll() {
        List<LichKham> list = lichKhamDAO.getAllLichKham();
        return new Response("success", "Lấy tất cả lịch khám", list);
    }

    private Response handleFindByDoctor(Map<String, Object> data) {
        if (data == null || !data.containsKey("maBacSi")) {
            return new Response("fail", "Thiếu mã bác sĩ", null);
        }

        int maBacSi = ((Double) data.get("maBacSi")).intValue();
        List<LichKham> list = lichKhamDAO.getLichKhamByBacSi(maBacSi);
        return new Response("success", "Lấy lịch khám theo bác sĩ", list);
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
