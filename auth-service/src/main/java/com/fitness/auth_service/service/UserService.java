package com.fitness.auth_service.service;

import com.fitness.auth_service.dto.*;

public interface UserService {

    ResponseWrapper createUser(CreateUserRequest request);

    ResponseWrapper login(LoginRequest request);

    LoginResponse refreshAccessToken(String rawRefreshToken);

}
