package com.fitness.auth_service.service;

import com.fitness.auth_service.dto.*;

public interface AuthService {

    ResponseWrapper createUser(CreateUserRequest request);

    ResponseWrapper login(LoginRequest request);

    LoginResponse refreshAccessToken(String rawRefreshToken);

    void logout(String rawRefreshToken);

}
