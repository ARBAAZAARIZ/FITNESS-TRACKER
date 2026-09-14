package com.fitness.auth_service.config;


import com.fitness.auth_service.dto.CreateUserRequest;
import com.fitness.auth_service.dto.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "user-service")
public interface UserServiceClient {

    @PostMapping("/api/users/internal/register")
    UserResponse createUser(@RequestBody CreateUserRequest request);

    @GetMapping("/api/users/internal/email/{email}")
    UserResponse getUserByEmail( @PathVariable("email") String email);

    @GetMapping("/api/users/internal/{userId}")
    UserResponse getUserById( @PathVariable("userId") String id);

}
