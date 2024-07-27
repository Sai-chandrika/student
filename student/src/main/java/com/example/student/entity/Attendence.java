package com.example.student.entity;

import com.example.student.dto.BaseDoc;
import com.example.student.enums.AttendanceStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Attendence extends BaseDoc {
    private LocalDate date;
    @Enumerated(EnumType.STRING)
    private AttendanceStatus status;
    private LocalTime checkInTime;
    private LocalTime checkOutTime;
    @ManyToOne(targetEntity = Student.class,fetch = FetchType.LAZY, cascade = {CascadeType.MERGE})
    @JoinColumn(name = "student")
    private Student student;
}
