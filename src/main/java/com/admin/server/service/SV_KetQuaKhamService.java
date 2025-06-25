package com.admin.server.service;

import com.admin.shared.model.KetQuaKham;
import com.admin.server.dao.KetQuaKhamDAO;
import com.admin.shared.protocol.Action;
import com.admin.shared.protocol.Response;
import com.admin.shared.util.JsonUtils;

import java.util.List;
import java.util.Map;

public class SV_KetQuaKhamService {
    private final KetQuaKhamDAO ketQuaKhamDAO = new KetQuaKhamDAO();

    public Response handle(String action, Map<String, Object> data) {
        return switch (action) {
            case Action.RESULT_ADD -> handleAdd(data);
            case Action.RESULT_UPDATE -> handleUpdate(data);
            case Action.RESULT_DELETE -> handleDelete(data);
            case Action.RESULT_FIND_ALL -> handleFindAll();
            case Action.RESULT_FIND_BY_PATIENT -> handleFindByPatient(data);
            default -> new Response("fail", "Không hỗ trợ action: " + action, null);
        };
    }

    private Response handleAdd(Map<String, Object> data) {
        KetQuaKham result = parseKetQuaKham(data.get("result"));
        if (result == null) return new Response("fail", "Dữ liệu không hợp lệ", null);

        boolean success = ketQuaKhamDAO.insert(result);
        return success
                ? new Response("success", "Thêm kết quả khám thành công", null)
                : new Response("fail", "Không thể thêm kết quả khám", null);
    }

    private Response handleUpdate(Map<String, Object> data) {
        KetQuaKham result = parseKetQuaKham(data.get("result"));
        if (result == null) return new Response("fail", "Dữ liệu không hợp lệ", null);

        boolean success = ketQuaKhamDAO.update(result);
        return success
                ? new Response("success", "Cập nhật kết quả khám thành công", null)
                : new Response("fail", "Không thể cập nhật kết quả khám", null);
    }

    private Response handleDelete(Map<String, Object> data) {
        if (data == null || !data.containsKey("maKetQuaKham")) {
            return new Response("fail", "Thiếu mã kết quả khám để xóa", null);
        }

        int id = ((Double) data.get("maKetQuaKham")).intValue();
        boolean success = ketQuaKhamDAO.delete(id);
        return success
                ? new Response("success", "Xóa kết quả khám thành công", null)
                : new Response("fail", "Không thể xóa kết quả khám", null);
    }

    private Response handleFindAll() {
        List<KetQuaKham> list = ketQuaKhamDAO.getAll();
        return new Response("success", "Tải danh sách kết quả khám", list);
    }

    private Response handleFindByPatient(Map<String, Object> data) {
        if (data == null || !data.containsKey("maBenhNhan")) {
            return new Response("fail", "Thiếu mã bệnh nhân", null);
        }

        int maBenhNhan = ((Double) data.get("maBenhNhan")).intValue();
        List<KetQuaKham> list = ketQuaKhamDAO.getKetQuaByBenhNhan(maBenhNhan);
        return new Response("success", "Tải kết quả khám theo bệnh nhân", list);
    }

    private KetQuaKham parseKetQuaKham(Object raw) {
        try {
            String json = JsonUtils.toJson(raw);
            return JsonUtils.fromJson(json, KetQuaKham.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
