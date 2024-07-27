package com.example.student.repo;

import com.example.student.entity.Attendence;
import com.example.student.entity.Student;
import com.example.student.enums.AttendanceStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface AttendanceRepo extends JpaRepository<Attendence,String> {


    List<Attendence> findByStudentIdAndDate(String studentId, LocalDate currentDate);

    Optional<Attendence> findByStudentIdAndDateAndStatus(String student, LocalDate date, AttendanceStatus status);

    List<Attendence> findByDateBetween(LocalDate startDate, LocalDate endDate);

    List<Attendence> findByDate(LocalDate localDate);

    List<Attendence> findByStatusAndDateBetween(AttendanceStatus attendanceStatus, LocalDate startDate, LocalDate endDate);
}
