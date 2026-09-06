package com.fitness.auth_service.dto;


import com.fitness.auth_service.model.UserRole;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Data
public class CreateUserRequest {

    private String id;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private Boolean enabled ;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;

}
