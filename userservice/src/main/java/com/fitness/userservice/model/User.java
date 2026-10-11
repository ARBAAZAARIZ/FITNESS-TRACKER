package com.fitness.userservice.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;


    private String firstName;
    private String lastName;

    @Enumerated(EnumType.STRING)
    private UserRole role = UserRole.USER;

    private Boolean enabled = true;

    @CreationTimestamp
    private LocalDateTime createAt;

    @UpdateTimestamp
    private LocalDateTime updateAt;


    /*Determines whether the account is allowed to authenticate using email and password.*/
    @Column(name = "password_login_enabled", nullable = false)
    private Boolean passwordLoginEnabled = true;


    /*Stores Google's stable user identifier from the OIDC sub claim,
    allowing us to associate the account with its Google identity.*/
    @Column(name = "google_subject", unique = true)
    private String googleSubject;

}
