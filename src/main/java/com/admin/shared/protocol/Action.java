package com.admin.shared.protocol;

public interface Action {
    // Auth Action
    String LOGIN = "login";
    String REGISTER = "register";

    // Dashboard Action
    String GET_ALL_ACCOUNTS = "getAllAccounts";
    String LOCK_ACCOUNT = "lockAccount";
    String UNLOCK_ACCOUNT = "unlockAccount";
    String DASHBOARD_STATS = "dashboard_stats";

    // Doctor Action
    String DOCTOR_ADD = "addDoctor";
    String DOCTOR_UPDATE = "updateDoctor";
    String DOCTOR_DELETE = "deleteDoctor";
    String DOCTOR_FIND_ALL = "findAllDoctors";
    String DOCTOR_COUNT_BY_SPECIALTY = "countDoctorsBySpecialty";

    // Warehouse Action
    String WAREHOUSE_FIND_ALL = "findAllDrugs";
    String WAREHOUSE_ADD = "addDrug";
    String WAREHOUSE_UPDATE = "updateDrug";
    String WAREHOUSE_DELETE = "deleteDrug";
    String WAREHOUSE_LOW_STOCK = "getLowStockDrugs";

    // Patient Action
    String PATIENT_FIND_ALL = "findAllPatient";
    String PATIENT_ADD = "addPatient";
    String PATIENT_UPDATE = "updatePatient";
    String PATIENT_DELETE = "deletePatient";
    String PATIENT_FIND_NAME = "findPatientIDByName";

    // Appointment
    String APPOINTMENT_ADD = "addAppointment";
    String APPOINTMENT_UPDATE = "updateAppointment";
    String APPOINTMENT_DELETE = "deleteAppointment";
    String APPOINTMENT_FIND_ALL = "findAllAppointment";
    String APPOINTMENT_FIND_BY_DATE = "findAppointmentByDate";
    String APPOINTMENT_ENTITY_BY_DATE = "findAppointmentEntitiesByDate";

    // Lich Kham Action
    String SCHEDULE_FIND_ALL = "findAllSchedules";
    String SCHEDULE_FIND_BY_DOCTOR = "findSchedulesByDoctor";
    String SCHEDULE_ADD = "addSchedule";
    String SCHEDULE_UPDATE = "updateSchedule";
    String SCHEDULE_DELETE = "deleteSchedule";

    // Kết quả khám
    String RESULT_ADD = "addKetQuaKham";
    String RESULT_UPDATE = "updateKetQuaKham";
    String RESULT_DELETE = "deleteKetQuaKham";
    String RESULT_FIND_ALL = "findAllKetQuaKham";
    String RESULT_FIND_BY_PATIENT = "findKetQuaByBenhNhan";

    // Dashboard Statistics Action
    String DASHBOARD_COUNT_PATIENT_TODAY = "dashboard_count_patient_today";
    String DASHBOARD_COUNT_APPOINTMENT_TODAY = "dashboard_count_appointment_today";
    String DASHBOARD_COUNT_PRESCRIPTION_TODAY = "dashboard_count_prescription_today";
    String DASHBOARD_REVENUE_TODAY = "dashboard_revenue_today";
    String DASHBOARD_REVENUE_MONTH = "dashboard_revenue_by_day_in_month";
}

