package com.admin.server.service;

import com.admin.server.dao.BenhNhanDAO;
import com.admin.shared.model.BenhNhan;
import com.admin.shared.protocol.Action;
import com.admin.shared.protocol.Response;
import com.admin.shared.util.JsonUtils;

import java.util.List;
import java.util.Map;

public class SV_PatientService {
    private final BenhNhanDAO benhNhanDAO = new BenhNhanDAO();

    public Response handle(String action, Map<String, Object> data) {
        return switch (action) {
            case Action.PATIENT_ADD -> handleAdd(data);
            case Action.PATIENT_UPDATE -> handleUpdate(data);
            case Action.PATIENT_DELETE -> handleDelete(data);
            case Action.PATIENT_FIND_ALL -> handleFindAll();
            case Action.PATIENT_FIND_NAME -> handleFindIdByName(data);
            default -> new Response("fail", "Không hỗ trợ action: " + action, null);
        };
    }

    private Response handleAdd(Map<String, Object> data) {
        BenhNhan bn = parseBenhnhan(data.get("patient"));
        if (bn == null) return new Response("fail", "Dữ liệu không hợp lệ", null);

        boolean success = benhNhanDAO.add(bn);
        return success
                ? new Response("success", "Thêm bác sĩ thành công", null)
                : new Response("fail", "Lỗi khi thêm bác sĩ", null);
    }

    private Response handleUpdate(Map<String, Object> data) {
        BenhNhan bs = parseBenhnhan(data.get("doctor"));
        if (bs == null) return new Response("fail", "Dữ liệu không hợp lệ", null);

        boolean success = benhNhanDAO.update(bs);
        return success
                ? new Response("success", "Cập nhật bác sĩ thành công", null)
                : new Response("fail", "Không thể cập nhật bác sĩ", null);
    }

    private Response handleDelete(Map<String, Object> data) {
        if (data == null || !data.containsKey("maBacSi")) {
            return new Response("fail", "Thiếu mã bác sĩ để xóa", null);
        }

        int maBacSi = (int) ((Double) data.get("maBacSi")).doubleValue();
        boolean success = benhNhanDAO.delete(maBacSi);
        return success
                ? new Response("success", "Xóa bác sĩ thành công", null)
                : new Response("fail", "Không thể xóa bác sĩ", null);
    }

    private Response handleFindAll() {
        List<BenhNhan> list = benhNhanDAO.getAll();
        return new Response("success", "Tải danh sách bác sĩ", list);
    }

    private Response handleFindIdByName(Map<String, Object> data) {
        if (data == null || !data.containsKey("tenBenhNhan")) {
            return new Response("fail", "Thiếu tên bệnh nhân", null);
        }

        String tenBenhNhan = (String) data.get("tenBenhNhan");
        Integer maBenhNhan = benhNhanDAO.getMaBenhNhanByTen(tenBenhNhan);

        if (maBenhNhan != null) {
            return new Response("success", "Tìm thấy mã bệnh nhân", maBenhNhan);
        } else {
            return new Response("fail", "Không tìm thấy bệnh nhân với tên: " + tenBenhNhan, null);
        }
    }

    private BenhNhan parseBenhnhan(Object raw) {
        try {
            String json = JsonUtils.toJson(raw);
            return JsonUtils.fromJson(json, BenhNhan.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
