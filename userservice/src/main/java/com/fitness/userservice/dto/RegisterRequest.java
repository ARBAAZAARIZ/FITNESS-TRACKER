package com.fitness.userservice.dto;


import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterRequest {

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters long")
    private String password;


    private String firstName;
    private String lastName;

    /*Determines whether the account is allowed to authenticate using email and password.*/
    private Boolean passwordLoginEnabled = true;


    /*Stores Google's stable user identifier from the OIDC sub claim,
    allowing us to associate the account with its Google identity.*/
    private String googleSubject;

}
