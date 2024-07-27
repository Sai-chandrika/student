package com.example.student.controller;

import com.example.student.dto.AttendenceRequest;
import com.example.student.dto.GenericResponse;
import com.example.student.service.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/v1/attendance/")
public class AttendanceController {
    @Autowired
    AttendanceService attendanceService;

    @PostMapping("save")
    public GenericResponse save(@RequestBody AttendenceRequest attendenceRequest){
        return attendanceService.save(attendenceRequest);
    }

    @GetMapping("weekly/{startDate}/{endDate}")
    public GenericResponse getWeeklyAttendance(@PathVariable LocalDate startDate, @PathVariable LocalDate endDate){
        return attendanceService.weeklyAttendance(startDate,endDate);
    }

    @GetMapping("monthly/{month}/{year}")
    public GenericResponse getMonthlyAttendance(@PathVariable int month,@PathVariable int year){
        return attendanceService.getMonthlyAttendance(month,year);
    }
    @GetMapping("yearly/{year}")
    public GenericResponse getYearlyAttendance(@PathVariable Integer year){
        return attendanceService.getYearlyAttendance(year);

    }

    @GetMapping("duration/{localDate}")
   public GenericResponse durationCalculate(@PathVariable LocalDate localDate){
        return attendanceService.durationCalculate(localDate);
    }


}
