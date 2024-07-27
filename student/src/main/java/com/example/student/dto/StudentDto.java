package com.example.student.dto;

import com.example.student.enums.Gender;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class StudentDto {
    private String id;
    private String firstName;
    private String lastName;
    private int age;
    private Gender gender;
    private String email;
    private String phoneNumber;
    private String address;
    private LocalDate dateOfBirth;
}
//{
//    "id":"8d797ff7-ceb1-4678-8f35-49d71c62b2e0",
//    "firstName":"chandrika sai",
//    "lastName":"ganugapenta",
//    "age":22,
//    "gender":"FEMALE",
//    "email":"saichandrika1662@gmail.com",
//    "phoneNumber":8019271662,
//    "address": "porumamilla",
//    "dateOfBirth":"2002-12-30"
//
//}