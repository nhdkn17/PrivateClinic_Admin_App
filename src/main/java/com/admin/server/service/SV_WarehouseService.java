package com.admin.server.service;

import com.admin.server.dao.ThuocDAO;
import com.admin.shared.model.Thuoc;
import com.admin.shared.protocol.Action;
import com.admin.shared.protocol.Response;
import com.admin.shared.util.JsonUtils;

import java.util.List;
import java.util.Map;

public class SV_WarehouseService {
    private final ThuocDAO thuocDAO = new ThuocDAO();

    public Response handle(String action, Map<String, Object> data) {
        return switch (action) {
            case Action.WAREHOUSE_FIND_ALL -> handleFindAll();
            case Action.WAREHOUSE_ADD -> handleAdd(data);
            case Action.WAREHOUSE_UPDATE -> handleUpdate(data);
            case Action.WAREHOUSE_DELETE -> handleDelete(data);
            case Action.WAREHOUSE_LOW_STOCK -> handleLowStock();
            default -> new Response("error", "Yêu cầu không hợp lệ", null);
        };
    }

    private Response handleFindAll() {
        List<Thuoc> list = thuocDAO.findAll();
        return new Response("success", "Lấy danh sách thuốc thành công", list);
    }

    private Response handleAdd(Map<String, Object> data) {
        Object raw = data.get("thuoc");
        Thuoc thuoc = JsonUtils.convertObject(raw, Thuoc.class);
        if (thuoc == null) {
            return new Response("fail", "Dữ liệu thuốc không hợp lệ", null);
        }

        boolean success = thuocDAO.insert(thuoc);
        return success
                ? new Response("success", "Thêm thuốc thành công", null)
                : new Response("fail", "Thêm thuốc thất bại", null);
    }

    private Response handleUpdate(Map<String, Object> data) {
        Thuoc thuoc = JsonUtils.convertObject(data, Thuoc.class);
        if (thuoc == null) {
            return new Response("fail", "Dữ liệu thuốc không hợp lệ", null);
        }

        boolean success = thuocDAO.update(thuoc);
        return success
                ? new Response("success", "Cập nhật thuốc thành công", null)
                : new Response("fail", "Cập nhật thuốc thất bại", null);
    }

    private Response handleDelete(Map<String, Object> data) {
        Integer id = data.containsKey("id") ? ((Number) data.get("id")).intValue() : null;
        if (id == null) {
            return new Response("fail", "Thiếu mã thuốc để xóa", null);
        }

        boolean success = thuocDAO.delete(id);
        return success
                ? new Response("success", "Xóa thuốc thành công", null)
                : new Response("fail", "Xóa thuốc thất bại", null);
    }

    private Response handleLowStock() {
        List<Thuoc> list = thuocDAO.getThuocSapHet();
        return new Response("success", "Lấy danh sách thuốc sắp hết thành công", list);
    }
}
