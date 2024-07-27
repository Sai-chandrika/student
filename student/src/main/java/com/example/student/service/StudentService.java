package com.example.student.service;

import com.example.student.dto.GenericResponse;
import com.example.student.dto.StudentDto;

public interface StudentService {
   GenericResponse save(StudentDto studentDto);

   GenericResponse delete(String id);

   GenericResponse getByStudent(String id);

   GenericResponse getAll();
}
