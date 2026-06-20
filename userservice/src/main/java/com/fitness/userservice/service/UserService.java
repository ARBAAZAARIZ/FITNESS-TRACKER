package com.fitness.userservice.service;


import com.fitness.userservice.dto.RegisterRequest;
import com.fitness.userservice.dto.UserResponse;
import org.springframework.stereotype.Service;


public interface UserService {


    public UserResponse register(RegisterRequest request);

    public UserResponse getUserProfile(String userId);

}
