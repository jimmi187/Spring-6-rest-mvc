package com.jimi.spring6restmvc.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class CustomErrorController {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity handleBindErrors(MethodArgumentNotValidException exception){

        List errorList = exception.getFieldErrors().stream()
                .map(FieldError ->{
                    Map<String, String> errorMap = new HashMap<>();
                    errorMap.put(FieldError.getField(), FieldError.getDefaultMessage());
                    return errorMap;
                }).collect(Collectors.toList());


        return ResponseEntity.badRequest().body(errorList);
    }
}
