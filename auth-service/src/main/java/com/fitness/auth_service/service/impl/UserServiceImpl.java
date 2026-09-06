package com.fitness.auth_service.service.impl;

import com.fitness.auth_service.config.UserServiceClient;
import com.fitness.auth_service.dto.CreateUserRequest;
import com.fitness.auth_service.dto.ResponseWrapper;
import com.fitness.auth_service.dto.UserResponse;
import com.fitness.auth_service.exception.CustomException;
import com.fitness.auth_service.model.User;
import com.fitness.auth_service.repository.UserRepository;
import com.fitness.auth_service.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;



@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final UserServiceClient  userServiceClient;


    @Override
    public ResponseWrapper createUser(CreateUserRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new CustomException("Email already registered");
        }

        UserResponse userResponse =  userServiceClient.createUser(request);

        ResponseWrapper apiResponse = new ResponseWrapper();

        if(userResponse.getId() != null && userResponse.getApiStatus()){
            apiResponse.setMessage(userResponse.getMessage() + " \n User ID: " + userResponse.getId());
            apiResponse.setApiStatus(true);
            apiResponse.setData(userResponse);
        }else {
            apiResponse.setMessage(userResponse.getMessage());
            apiResponse.setApiStatus(false);
            apiResponse.setData(null);
        }
        return apiResponse;
    }
}
