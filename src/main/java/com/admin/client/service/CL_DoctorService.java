package com.admin.client.service;

import com.admin.shared.model.BacSi;
import com.admin.shared.protocol.Action;
import com.admin.shared.protocol.Request;
import com.admin.shared.protocol.Response;
import com.admin.shared.util.JsonUtils;
import com.admin.client.network.SocketClient;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CL_DoctorService{

    public List<BacSi> getAllDoctors() throws IOException {
        Request request = new Request(Action.DOCTOR_FIND_ALL, null);
        Response response = SocketClient.getInstance().send(request);
        if (response != null && "success".equals(response.getStatus())) {
            return JsonUtils.convertToList(response.getData(), BacSi.class);
        }
        return null;
    }

    public boolean addDoctor(BacSi bacSi) throws IOException {
        Map<String, Object> data = new HashMap<>();
        data.put("doctor", bacSi);
        Request request = new Request(Action.DOCTOR_ADD, data);
        Response response = SocketClient.getInstance().send(request);
        return response != null && "success".equals(response.getStatus());
    }

    public boolean updateDoctor(BacSi bacSi) throws IOException {
        Map<String, Object> data = new HashMap<>();
        data.put("doctor", bacSi);
        Request request = new Request(Action.DOCTOR_UPDATE, data);
        Response response = SocketClient.getInstance().send(request);
        return response != null && "success".equals(response.getStatus());
    }

    public boolean deleteDoctor(int maBacSi) throws IOException {
        Map<String, Object> data = new HashMap<>();
        data.put("maBacSi", maBacSi);
        Request request = new Request(Action.DOCTOR_DELETE, data);
        Response response = SocketClient.getInstance().send(request);
        return response != null && "success".equals(response.getStatus());
    }

    public Map<String, Integer> getStats() throws IOException {
        Request request = new Request(Action.DOCTOR_COUNT_BY_SPECIALTY, null);
        Response response = SocketClient.getInstance().send(request);
        if (response != null && "success".equals(response.getStatus())) {
            return JsonUtils.fromJson(
                    JsonUtils.toJson(response.getData()),
                    new com.google.gson.reflect.TypeToken<Map<String, Integer>>(){}.getType()
            );
        }
        return new HashMap<>();
    }
}
