package com.jimi.spring6restmvc.controllers;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionSystemException;
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

    @ExceptionHandler
    ResponseEntity handleJPAViolations(TransactionSystemException exception){
        ResponseEntity.BodyBuilder responseEntity = ResponseEntity.badRequest();

        if(exception.getCause().getCause() instanceof ConstraintViolationException){
            ConstraintViolationException ve = (ConstraintViolationException) exception.getCause().getCause();

            List errors = ve.getConstraintViolations().stream()
                    .map(constraintViolation -> {
                        Map<String,String> errmap = new HashMap<>();
                        errmap.put(constraintViolation.getPropertyPath().toString(),
                                constraintViolation.getMessage());
                        return errmap;
                    }).collect(Collectors.toList());
            return responseEntity.body(errors);
        }
        return responseEntity.build();
    }


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
