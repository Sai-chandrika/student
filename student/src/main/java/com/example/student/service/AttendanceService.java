package com.example.student.service;

import com.example.student.dto.AttendenceRequest;
import com.example.student.dto.GenericResponse;

import java.time.LocalDate;

public interface AttendanceService {
    GenericResponse save(AttendenceRequest attendenceRequest);

    GenericResponse weeklyAttendance(LocalDate startDate, LocalDate endDate);

    GenericResponse getMonthlyAttendance(int month, int year);

    GenericResponse getYearlyAttendance(Integer year);

    GenericResponse durationCalculate(LocalDate localDate);
}
