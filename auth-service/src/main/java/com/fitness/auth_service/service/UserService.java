package com.fitness.auth_service.service;

import com.fitness.auth_service.dto.CreateUserRequest;
import com.fitness.auth_service.dto.ResponseWrapper;
import com.fitness.auth_service.dto.UserResponse;

public interface UserService {

    ResponseWrapper createUser(CreateUserRequest request);

}
