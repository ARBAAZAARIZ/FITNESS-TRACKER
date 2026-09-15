package com.fitness.auth_service.controller;

import com.fitness.auth_service.dto.*;
import com.fitness.auth_service.service.RefreshTokenService;
import com.fitness.auth_service.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class Authcontroller {

    @Autowired
    private AuthService authService;

    @Autowired
    private RefreshTokenService refreshTokenService;




    @PostMapping("/signup")
    public ResponseEntity<?> createUser(@RequestBody CreateUserRequest request) {
        System.out.println("creating user");

        return  ResponseEntity.ok( authService.createUser(request));
    }


    @PostMapping("/login")
    public ResponseEntity<?> login(
            @RequestBody LoginRequest request) {

        return ResponseEntity.ok(authService.login(request));
    }

        @PostMapping("/refresh")
    public LoginResponse refreshAccessToken(@RequestBody RefreshTokenRequest request) {
        return authService.refreshAccessToken(request.getRefreshToken());
    }

    @PostMapping("/logout")
    public ResponseWrapper logout(
            @RequestBody LogoutRequest request) {

        authService.logout(request.getRefreshToken());

        ResponseWrapper response = new ResponseWrapper();
        response.setApiStatus(true);
        response.setMessage("Logout successful");
        response.setData(null);

        return response;
    }

}
