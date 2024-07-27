package com.example.student.service.impl;

import com.example.student.dto.GenericResponse;
import com.example.student.dto.StudentDto;
import com.example.student.entity.Student;
import com.example.student.repo.StudentRepo;
import com.example.student.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService {
@Autowired
    StudentRepo studentRepo;
    @Override
    public GenericResponse save(StudentDto studentDto) {
        Student student=new Student();
        if(studentDto.getId()!=null){
            Optional<Student> studentOptional=studentRepo.findById(studentDto.getId());
            if(studentOptional.isPresent()){
                student=studentOptional.get();
                if(studentDto.getFirstName()!=null) {
                    student.setFirstName(studentDto.getFirstName());
                }
                if(studentDto.getLastName()!=null) {
                    student.setLastName(studentDto.getLastName());
                }
                if(studentDto.getAge()!=0){
                student.setAge(studentDto.getAge());
                }
                if(studentDto.getGender()!=null) {
                    student.setGender(studentDto.getGender());
                }
                if(studentDto.getEmail()!=null) {
                    Student student1=studentRepo.getByEmail(studentDto.getEmail());
                    if(student1!=null && !student1.getId().equals(studentDto.getId())){
                        return new GenericResponse(HttpStatus.BAD_REQUEST.value(), "Email is already exists");
                    }else {
                        student.setEmail(studentDto.getEmail());
                    }
                }
                if(studentDto.getPhoneNumber()!=null) {
                    student.setPhoneNumber(studentDto.getPhoneNumber());
                }
                if(studentDto.getAddress()!=null) {
                    student.setAddress(studentDto.getAddress());
                }
                if(studentDto.getDateOfBirth()!=null) {
                    student.setDateOfBirth(studentDto.getDateOfBirth());
                }
                studentRepo.save(student);
                return new GenericResponse(HttpStatus.OK.value(), "student update successfully", entityToDto(student));

            }else{
                return new GenericResponse("student id is not found");
            }

        }else{
            if (studentDto.getFirstName() == null) {
                return new GenericResponse(HttpStatus.BAD_REQUEST.value(), "First Name is mandatory");
            }
            if (studentDto.getLastName() == null) {
                return new GenericResponse(HttpStatus.BAD_REQUEST.value(), "Last Name is mandatory");
            }
            if (studentDto.getAge() == 0) {
                return new GenericResponse(HttpStatus.BAD_REQUEST.value(), "Age is mandatory and should be greater than 0");
            }
            if (studentDto.getGender() == null) {
                return new GenericResponse(HttpStatus.BAD_REQUEST.value(), "Gender is mandatory");
            }
            if (studentDto.getEmail() == null) {
                return new GenericResponse(HttpStatus.BAD_REQUEST.value(), "Email is mandatory");
            }else{
                Student student1=studentRepo.getByEmail(studentDto.getEmail());
                if(student1!=null){
                    return new GenericResponse(HttpStatus.BAD_REQUEST.value(), "Email is already exists");

                }
            }
            if (studentDto.getPhoneNumber() == null) {
                return new GenericResponse(HttpStatus.BAD_REQUEST.value(), "Phone Number is mandatory");
            }
            if (studentDto.getAddress() == null) {
                return new GenericResponse(HttpStatus.BAD_REQUEST.value(), "Address is mandatory");
            }
            if (studentDto.getDateOfBirth() == null) {
                return new GenericResponse(HttpStatus.BAD_REQUEST.value(), "Date of Birth is mandatory");
            }

            student.setFirstName(studentDto.getFirstName());
            student.setLastName(studentDto.getLastName());
            student.setAge(studentDto.getAge());
            student.setGender(studentDto.getGender());
            student.setEmail(studentDto.getEmail());
            student.setPhoneNumber(studentDto.getPhoneNumber());
            student.setAddress(studentDto.getAddress());
            student.setDateOfBirth(studentDto.getDateOfBirth());
            studentRepo.save(student);
            return new GenericResponse(HttpStatus.OK.value(), "student saved successfully", entityToDto(student));
        }

    }

    @Override
    public GenericResponse delete(String id) {
        Optional<Student> studentOptional=studentRepo.findById(id);
        if(studentOptional.isPresent()) {
            Student student = studentOptional.get();
            studentRepo.delete(student);
            return new GenericResponse("Student deleted successfully");
        }else{
            return new GenericResponse("student id is not found");
        }
    }

    @Override
    public GenericResponse getByStudent(String id) {
        Optional<Student> studentOptional=studentRepo.findById(id);
        if(studentOptional.isPresent()) {
            Student student = studentOptional.get();
            return new GenericResponse(HttpStatus.OK.value(),"Student  details fetch successfully", entityToDto(student));
        }else{
            return new GenericResponse("student id is not found");
        }
    }

    @Override
    public GenericResponse getAll() {
        List<Student> studentList=studentRepo.findAll();
        return new GenericResponse(HttpStatus.OK.value(), "All Student Details", entitiesToDtos(studentList));
    }


    private StudentDto entityToDto(Student student) {
        StudentDto studentDto=new StudentDto();
        studentDto.setId(student.getId());
        studentDto.setFirstName(student.getFirstName());
        studentDto.setLastName(student.getLastName());
        studentDto.setAge(student.getAge());
        studentDto.setGender(student.getGender());
        studentDto.setEmail(student.getEmail());
        studentDto.setPhoneNumber(student.getPhoneNumber());
        studentDto.setAddress(student.getAddress());
        studentDto.setDateOfBirth(student.getDateOfBirth());
        return studentDto;
    }

    public List<StudentDto> entitiesToDtos(List<Student> students) {
        List<StudentDto> studentDtos = new ArrayList<>();
        for (Student student : students) {
            studentDtos.add(entityToDto(student));
        }
        return studentDtos;
    }
}
