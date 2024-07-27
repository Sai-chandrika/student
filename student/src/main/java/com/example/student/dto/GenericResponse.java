package com.example.student.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
public class GenericResponse {
    private int code;
    private String message;
    private Object payLoad;



    public GenericResponse(int code, String message, Object payLoad) {
        this.code = code;
        this.message = message;
        this.payLoad = payLoad;
    }
    public GenericResponse(int code, String message) {
        this.code = code;
        this.message = message;
    }
    public GenericResponse( Object payLoad) {

        this.payLoad = payLoad;
    }
    public GenericResponse( String message) {

        this.message = message  ;
    }



}
