package com.admin.server.service;

import com.admin.server.dao.BacSiDAO;
import com.admin.shared.model.BacSi;
import com.admin.shared.protocol.Action;
import com.admin.shared.protocol.Response;
import com.admin.shared.util.JsonUtils;

import java.util.List;
import java.util.Map;

public class SV_DoctorService {
    private final BacSiDAO bacSiDAO = new BacSiDAO();

    public Response handle(String action, Map<String, Object> data) {
        return switch (action) {
            case Action.DOCTOR_ADD -> handleAdd(data);
            case Action.DOCTOR_UPDATE -> handleUpdate(data);
            case Action.DOCTOR_DELETE -> handleDelete(data);
            case Action.DOCTOR_FIND_ALL -> handleFindAll();
            case Action.DOCTOR_COUNT_BY_SPECIALTY -> handleCountBySpecialty();
            default -> new Response("fail", "Không hỗ trợ action: " + action, null);
        };
    }

    private Response handleAdd(Map<String, Object> data) {
        BacSi bs = parseBacSi(data.get("doctor"));
        if (bs == null) return new Response("fail", "Dữ liệu không hợp lệ", null);

        boolean success = bacSiDAO.insert(bs);
        return success
                ? new Response("success", "Thêm bác sĩ thành công", null)
                : new Response("fail", "Lỗi khi thêm bác sĩ", null);
    }

    private Response handleUpdate(Map<String, Object> data) {
        BacSi bs = parseBacSi(data.get("doctor"));
        if (bs == null) return new Response("fail", "Dữ liệu không hợp lệ", null);

        boolean success = bacSiDAO.update(bs);
        return success
                ? new Response("success", "Cập nhật bác sĩ thành công", null)
                : new Response("fail", "Không thể cập nhật bác sĩ", null);
    }

    private Response handleDelete(Map<String, Object> data) {
        if (data == null || !data.containsKey("maBacSi")) {
            return new Response("fail", "Thiếu mã bác sĩ để xóa", null);
        }

        int maBacSi = (int) ((Double) data.get("maBacSi")).doubleValue();
        boolean success = bacSiDAO.delete(maBacSi);
        return success
                ? new Response("success", "Xóa bác sĩ thành công", null)
                : new Response("fail", "Không thể xóa bác sĩ", null);
    }

    private Response handleFindAll() {
        List<BacSi> list = bacSiDAO.findAll();
        return new Response("success", "Tải danh sách bác sĩ", list);
    }

    private Response handleCountBySpecialty() {
        try {
            Map<String, Integer> stats = bacSiDAO.laySoBacSiTheoChuyenKhoa();
            return new Response("success", "Thống kê thành công", stats);
        } catch (Exception e) {
            return new Response("fail", "Lỗi khi thống kê: " + e.getMessage(), null);
        }
    }

    private BacSi parseBacSi(Object raw) {
        try {
            String json = JsonUtils.toJson(raw);
            return JsonUtils.fromJson(json, BacSi.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
