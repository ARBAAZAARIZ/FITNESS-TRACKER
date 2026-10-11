package com.fitness.userservice.controller;

import com.fitness.userservice.dto.UserResponse;
import com.fitness.userservice.exception.UserNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<?> handleUserNotFoundException(UserNotFoundException ex){

        UserResponse response = new UserResponse();
        response.setApiStatus(false);
        response.setMessage("User Not Found");

        return  ResponseEntity.ok(response);

    }
}
