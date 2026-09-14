package com.fitness.auth_service.service.impl;

import com.fitness.auth_service.config.UserServiceClient;
import com.fitness.auth_service.dto.*;
import com.fitness.auth_service.exception.CustomException;
import com.fitness.auth_service.repository.UserRepository;
import com.fitness.auth_service.security.CustomUserDetails;
import com.fitness.auth_service.security.JwtService;
import com.fitness.auth_service.service.RefreshTokenService;
import com.fitness.auth_service.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;



@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final UserServiceClient  userServiceClient;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private final JwtService jwtService;

    private final RefreshTokenService refreshTokenService;


    @Override
    public ResponseWrapper createUser(CreateUserRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new CustomException("Email already registered");
        }

        String encodedPassword = passwordEncoder.encode(request.getPassword());
        request.setPassword(encodedPassword);

        UserResponse userResponse =  userServiceClient.createUser(request);

        ResponseWrapper apiResponse = new ResponseWrapper();

        if(userResponse.getId() != null && userResponse.getApiStatus()){
            apiResponse.setMessage(userResponse.getMessage()
                    + " \n User ID: "
                    + userResponse.getId());
            apiResponse.setApiStatus(true);
            apiResponse.setData(userResponse);
        }else {
            apiResponse.setMessage(userResponse.getMessage());
            apiResponse.setApiStatus(false);
            apiResponse.setData(null);
        }
        return apiResponse;
    }

    @Override
    public ResponseWrapper login(LoginRequest request) {

        System.out.println(">>> Login request received for: " + request.getEmail());

        try {

            Authentication authentication =
                    authenticationManager.authenticate(
                            new UsernamePasswordAuthenticationToken(
                                    request.getEmail(),
                                    request.getPassword()
                            )
                    );

            System.out.println(">>> Authentication successful");

            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

            String accessToken = jwtService.generateToken(userDetails);

            String refreshToken =
                    refreshTokenService.createRefreshToken(
                            userDetails.getId()
                    );

            LoginResponse loginResponse =
                    new LoginResponse(
                            true,
                            "Login successful",
                            accessToken,
                            refreshToken,
                            userDetails.getId(),
                            userDetails.getEmail()
                    );



            ResponseWrapper response = new ResponseWrapper();
            response.setApiStatus(true);
            response.setMessage("Login successful");
            response.setData(loginResponse);

            return response;

        } catch (Exception e) {

            System.out.println(">>> Authentication failed: "
                    + e.getClass().getName());

            System.out.println(">>> Message: " + e.getMessage());

            throw e;
        }
    }
}
