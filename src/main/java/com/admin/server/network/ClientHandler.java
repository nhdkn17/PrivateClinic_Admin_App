package com.admin.server.network;

import com.admin.server.service.*;

import com.admin.shared.protocol.Action;
import com.admin.shared.protocol.Request;
import com.admin.shared.protocol.Response;
import com.admin.shared.util.JsonUtils;

import java.io.*;
import java.net.Socket;
import java.sql.SQLException;

public class ClientHandler implements Runnable {
    private final Socket socket;
    private final SV_AuthService authService = new SV_AuthService();
    private final SV_DoctorService doctorService = new SV_DoctorService();
    private final SV_WarehouseService warehouseService = new SV_WarehouseService();
    private final SV_DashboardService dashboardService = new SV_DashboardService();
    private final SV_PatientService patientService = new SV_PatientService();
    private final SV_AppointmentService appointmentService = new SV_AppointmentService();
    private final SV_KetQuaKhamService ketQuaKhamService = new SV_KetQuaKhamService();
    private final SV_LichKhamTuanService lichKhamTuanService = new SV_LichKhamTuanService();
    private final SV_DashboardService_Recep dashboardServiceRecep = new SV_DashboardService_Recep();

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (
                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()))
        ) {
            String requestJson;
            while ((requestJson = reader.readLine()) != null) {
                if (requestJson.trim().isEmpty()) {
                    System.err.println("Dữ liệu nhận được rỗng.");
                    continue;
                }

                System.out.println(">> Client gửi: " + requestJson);
                Request request = JsonUtils.fromJson(requestJson, Request.class);
                if (request == null) {
                    System.err.println("Không thể phân tích JSON.");
                    continue;
                }

                Response response = routeRequest(request);

                String responseJson = JsonUtils.toJson(response);
                assert responseJson != null;
                writer.write(responseJson);
                writer.newLine();
                writer.flush();
                System.out.println("<< Server phản hồi: " + responseJson);
            }
        } catch (IOException e) {
            System.err.println("Lỗi khi xử lý client: " + e.getMessage());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Response routeRequest(Request request) throws SQLException {
        String action = request.getAction();

        switch (action) {
            // Auth
            case Action.LOGIN, Action.REGISTER:
                return authService.handle(action, request.getData());
            // Dashboard
            case Action.DASHBOARD_STATS, Action.GET_ALL_ACCOUNTS, Action.LOCK_ACCOUNT, Action.UNLOCK_ACCOUNT:
                return dashboardService.handle(action, request.getData());
            // Doctor
            case Action.DOCTOR_ADD, Action.DOCTOR_UPDATE, Action.DOCTOR_DELETE, Action.DOCTOR_FIND_ALL, Action.DOCTOR_COUNT_BY_SPECIALTY:
                return doctorService.handle(action, request.getData());
            // Warehouse
            case Action.WAREHOUSE_FIND_ALL, Action.WAREHOUSE_ADD, Action.WAREHOUSE_UPDATE, Action.WAREHOUSE_DELETE, Action.WAREHOUSE_LOW_STOCK:
                return warehouseService.handle(action, request.getData());
            // Patient
            case Action.PATIENT_FIND_ALL, Action.PATIENT_ADD, Action.PATIENT_UPDATE, Action.PATIENT_DELETE, Action.PATIENT_FIND_NAME:
                return patientService.handle(action, request.getData());
            // Appointment
            case Action.APPOINTMENT_ADD, Action.APPOINTMENT_UPDATE, Action.APPOINTMENT_DELETE, Action.APPOINTMENT_FIND_ALL, Action.APPOINTMENT_FIND_BY_DATE, Action.APPOINTMENT_ENTITY_BY_DATE:
                return appointmentService.handle(action, request.getData());
            case Action.SCHEDULE_FIND_ALL, Action.SCHEDULE_FIND_BY_DOCTOR, Action.SCHEDULE_ADD, Action.SCHEDULE_UPDATE, Action.SCHEDULE_DELETE:
                return lichKhamTuanService.handle(action, request.getData());
            case Action.RESULT_ADD, Action.RESULT_UPDATE, Action.RESULT_FIND_ALL, Action.RESULT_DELETE, Action.RESULT_FIND_BY_PATIENT:
                return ketQuaKhamService.handle(action, request.getData());
            case Action.DASHBOARD_COUNT_PATIENT_TODAY, Action.DASHBOARD_COUNT_APPOINTMENT_TODAY, Action.DASHBOARD_COUNT_PRESCRIPTION_TODAY, Action.DASHBOARD_REVENUE_TODAY, Action.DASHBOARD_REVENUE_MONTH:
                return dashboardServiceRecep.handle(action, request.getData());
            default:
                return new Response("error", "Action không được hỗ trợ: " + action, null);
        }
    }
}
