package com.fitness.auth_service.dto;

import jakarta.persistence.Transient;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserResponse {

    private String id;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String role ;
    private Boolean enabled ;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;

    private Boolean apiStatus;
    private String message;



}
