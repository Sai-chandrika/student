package com.example.student.controller;

import com.example.student.dto.GenericResponse;
import com.example.student.dto.StudentDto;
import com.example.student.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/student/")
public class StudentController {
    @Autowired
    StudentService studentService;

    @PostMapping("save")
    public GenericResponse saveStudent(@RequestBody StudentDto studentDto){
        return studentService.save(studentDto);
    }

    @DeleteMapping("delete/{id}")
    public GenericResponse deleteStudent(@PathVariable String id){

        return studentService.delete(id);
    }

    @GetMapping("get-student-by-id/{id}")
    public GenericResponse getByStudent(@PathVariable String id){
        return studentService.getByStudent(id);
    }
    @GetMapping("get-all")
    public GenericResponse getAll(){
        return studentService.getAll();
    }


}
