package com.example.student.service.impl;

import com.example.student.dto.AttendenceDto;
import com.example.student.dto.AttendenceRequest;
import com.example.student.dto.GenericResponse;
import com.example.student.entity.Attendence;
import com.example.student.entity.Student;
import com.example.student.enums.AttendanceStatus;
import com.example.student.repo.AttendanceRepo;
import com.example.student.repo.StudentRepo;
import com.example.student.service.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.time.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AttendanceServiceImpl implements AttendanceService {
    @Autowired
    StudentRepo studentRepo;
    @Autowired
    AttendanceRepo attendanceRepo;

    @Override
    public GenericResponse save(AttendenceRequest attendenceRequest) {
        Optional<Student> optionalStudent = studentRepo.findById(attendenceRequest.getStudent());
        if (optionalStudent.isEmpty()) {
            return new GenericResponse(HttpStatus.NOT_FOUND.value(), "Student not found", null);
        }

        Attendence attendance = new Attendence();

        if (attendenceRequest.getStatus() == AttendanceStatus.CHECK_IN) {
            Optional<Attendence> optionalAttendence = attendanceRepo.findByStudentIdAndDateAndStatus(attendenceRequest.getStudent(), attendenceRequest.getDate(), attendenceRequest.getStatus());
            if (optionalAttendence.isPresent()) {
                return new GenericResponse(HttpStatus.BAD_REQUEST.value(), "Already checked in", null);
            } else {
                attendance.setStatus(attendenceRequest.getStatus());
                attendance.setCheckInTime(LocalTime.now());
                attendance.setStudent(optionalStudent.get());
                attendance.setDate(attendenceRequest.getDate());
                attendanceRepo.save(attendance);
                return new GenericResponse(HttpStatus.OK.value(), "Checked in successfully", convertEntityToDto(attendance));
            }
        }

        if (attendenceRequest.getStatus() == AttendanceStatus.CHECK_OUT) {
            Optional<Attendence> checkin = attendanceRepo.findByStudentIdAndDateAndStatus(attendenceRequest.getStudent(), attendenceRequest.getDate(), AttendanceStatus.CHECK_IN);

            if (checkin.isEmpty()) {
                return new GenericResponse(HttpStatus.BAD_REQUEST.value(), "check in first", null);
            }
            Optional<Attendence> optionalAttendence = attendanceRepo.findByStudentIdAndDateAndStatus(attendenceRequest.getStudent(), attendenceRequest.getDate(), attendenceRequest.getStatus());
            {
                if (optionalAttendence.isPresent()) {
                    return new GenericResponse(HttpStatus.BAD_REQUEST.value(), "Already checked out", null);
                } else {
                    attendance.setStatus(attendenceRequest.getStatus());
                    attendance.setCheckOutTime(LocalTime.now());
                    attendance.setStudent(optionalStudent.get());
                    attendance.setDate(attendenceRequest.getDate());
                    attendanceRepo.save(attendance);
                    return new GenericResponse(HttpStatus.OK.value(), "Checked Out successfully", convertEntityToDto(attendance));
                }

            }

        }
        return new GenericResponse(HttpStatus.BAD_REQUEST.value(), "unvalid request");
    }

    @Override
    public GenericResponse weeklyAttendance(LocalDate startDate, LocalDate endDate) {
        List<Student> students = studentRepo.findAll();
        List<Attendence> checkIns = attendanceRepo.findByStatusAndDateBetween(AttendanceStatus.CHECK_IN, startDate, endDate);
        Map<String, Map<LocalDate, Attendence>> checkInsByStudentIdAndDate = checkIns.stream()
                .collect(Collectors.groupingBy(
                        attendance -> attendance.getStudent().getId(),
                        Collectors.toMap(Attendence::getDate, attendance -> attendance)
                ));
        List<AttendenceRequest> attendanceDtos = new ArrayList<>();

        for (Student student : students) {
            for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
                AttendenceRequest dto = new AttendenceRequest();
                dto.setStudent(student.getFirstName());
                dto.setDate(date);
                Map<LocalDate, Attendence> studentCheckInsByDate = checkInsByStudentIdAndDate.get(student.getId());
                if (studentCheckInsByDate != null && studentCheckInsByDate.containsKey(date)) {
                    dto.setStatus(AttendanceStatus.PRESENT);
                } else {
                    dto.setStatus(AttendanceStatus.ABSENT);

                }
                attendanceDtos.add(dto);
            }
        }

        return new GenericResponse(HttpStatus.OK.value(), "Weekly attendance fetched successfully", attendanceDtos);
    }




    @Override
    public GenericResponse getMonthlyAttendance(int month, int year) {
            YearMonth yearMonth = YearMonth.of(year, month);
        LocalDate startDate = yearMonth.atDay(1);
        LocalDate endDate = yearMonth.atEndOfMonth();
        System.out.println(startDate);
        System.out.println(endDate);
        List<Student> students = studentRepo.findAll();
        List<Attendence> checkIns = attendanceRepo.findByStatusAndDateBetween(AttendanceStatus.CHECK_IN, startDate, endDate);
        Map<String, Map<LocalDate, Attendence>> checkInsByStudentIdAndDate = checkIns.stream()
                .collect(Collectors.groupingBy(
                        attendance -> attendance.getStudent().getId(),
                        Collectors.toMap(Attendence::getDate, attendance -> attendance)
                ));
        List<AttendenceRequest> attendanceDtos = new ArrayList<>();

        for (Student student : students) {
            for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
                AttendenceRequest dto = new AttendenceRequest();
                dto.setStudent(student.getFirstName());
                dto.setDate(date);
                Map<LocalDate, Attendence> studentCheckInsByDate = checkInsByStudentIdAndDate.get(student.getId());
                if (studentCheckInsByDate != null && studentCheckInsByDate.containsKey(date)) {
                    dto.setStatus(AttendanceStatus.PRESENT);
                } else {
                    dto.setStatus(AttendanceStatus.ABSENT);

                }
                attendanceDtos.add(dto);
            }
        }
        return new GenericResponse(HttpStatus.OK.value(), "Monthly attendance fetched successfully", attendanceDtos);
    }



    @Override
    public GenericResponse getYearlyAttendance(Integer year) {
        LocalDate startDate = LocalDate.of(year, 1, 1);
        LocalDate endDate = LocalDate.of(year, 12, 31);

        List<Student> students = studentRepo.findAll();
        List<Attendence> checkIns = attendanceRepo.findByStatusAndDateBetween(AttendanceStatus.CHECK_IN, startDate, endDate);
        Map<String, Map<LocalDate, Attendence>> checkInsByStudentIdAndDate = checkIns.stream()
                .collect(Collectors.groupingBy(
                        attendance -> attendance.getStudent().getId(),
                        Collectors.toMap(Attendence::getDate, attendance -> attendance)
                ));
        List<AttendenceRequest> attendanceDtos = new ArrayList<>();

        for (Student student : students) {
            for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
                AttendenceRequest dto = new AttendenceRequest();
                dto.setStudent(student.getFirstName());
                dto.setDate(date);
                Map<LocalDate, Attendence> studentCheckInsByDate = checkInsByStudentIdAndDate.get(student.getId());
                if (studentCheckInsByDate != null && studentCheckInsByDate.containsKey(date)) {
                    dto.setStatus(AttendanceStatus.PRESENT);
                } else {
                    dto.setStatus(AttendanceStatus.ABSENT);

                }
                attendanceDtos.add(dto);
            }
        }

        return new GenericResponse(HttpStatus.OK.value(), "Yearly attendance fetched successfully", attendanceDtos);
    }

    @Override
    public GenericResponse durationCalculate(LocalDate localDate) {
        List<Attendence> attendances = attendanceRepo.findByDate(localDate);
        Map<Student, List<Attendence>> groupedByStudent = attendances.stream()
                .collect(Collectors.groupingBy(Attendence::getStudent));

        Map<String, String> studentWorkingHours = groupedByStudent.entrySet().stream()
                .collect(Collectors.toMap(
                        entry -> entry.getKey().getFirstName(),
                        entry -> {
                            Duration totalDuration = calculateTotalWorkingHoursForStudent(entry.getValue());
                            long hours = totalDuration.toHours();
                            long minutes = totalDuration.toMinutesPart();
                            return hours + " hours and " + minutes + " minutes";
                        }
                ));

        return new GenericResponse(HttpStatus.OK.value(),"Success", studentWorkingHours);
    }

    private Duration calculateTotalWorkingHoursForStudent(List<Attendence> attendances) {
        List<Attendence> checkIns = attendances.stream()
                .filter(attendance -> attendance.getCheckInTime() != null)
                .collect(Collectors.toList());
        List<Attendence> checkOuts = attendances.stream()
                .filter(attendance -> attendance.getCheckOutTime() != null)
                .collect(Collectors.toList());
        Duration totalDuration = Duration.ZERO;
        for (int i = 0; i < checkIns.size() && i < checkOuts.size(); i++) {
            LocalTime checkInTime = checkIns.get(i).getCheckInTime();
            LocalTime checkOutTime = checkOuts.get(i).getCheckOutTime();
            if (checkInTime != null && checkOutTime != null) {
                Duration duration = Duration.between(checkInTime, checkOutTime);
                totalDuration = totalDuration.plus(duration);
            }
        }
        return totalDuration;
    }




    public AttendenceDto convertEntityToDto(Attendence attendence){
        AttendenceDto attendenceDto=new AttendenceDto();
        attendenceDto.setStudent(attendenceDto.getStudent());
        attendenceDto.setDate(attendence.getDate());
        attendenceDto.setCheckOutTime(attendence.getCheckOutTime());
        attendenceDto.setCheckInTime(attendence.getCheckInTime());
        attendenceDto.setStatus(attendence.getStatus());
        return attendenceDto;
    }

    public AttendenceRequest convertEntityToRequest(Attendence attendence){
        AttendenceRequest attendenceDto=new AttendenceRequest();
        attendenceDto.setStudent(attendenceDto.getStudent());
        attendenceDto.setDate(attendence.getDate());
        if(attendence.getCheckInTime()!=null) {
            attendenceDto.setCheckInTime(attendence.getCheckInTime());
        }
        attendenceDto.setStatus(attendence.getStatus());
        return attendenceDto;
    }
}
