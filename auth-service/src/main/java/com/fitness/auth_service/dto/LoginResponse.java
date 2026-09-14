package com.fitness.auth_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {

    private Boolean apiStatus;
    private String message;
    private String accessToken;
    private String refreshToken;
    private String userId;
    private String email;

}
