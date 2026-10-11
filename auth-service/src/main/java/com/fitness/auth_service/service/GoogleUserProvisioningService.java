package com.fitness.auth_service.service;

import com.fitness.auth_service.config.UserServiceClient;
import com.fitness.auth_service.dto.CreateUserRequest;
import com.fitness.auth_service.dto.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GoogleUserProvisioningService {

    private final UserServiceClient userServiceClient;

    private final PasswordEncoder passwordEncoder;

    public UserResponse findOrCreateUser(OidcUser oidcUser){

        String email = oidcUser.getEmail();

//        1. check weather user is already registerd

        UserResponse existingUser = userServiceClient.getUserByEmail(email);

        if(existingUser!=null && Boolean.TRUE.equals(existingUser.getApiStatus()) && existingUser.getId() !=null ){

            return existingUser;

        }

        // 2. User  don't exist create new user
        String firstName = oidcUser.getGivenName();
        String lastName = oidcUser.getFamilyName();

        /*
         * Google users don't provide a password to our application.
         * Our current User entity requires a non-null password,
         * so generate a random unusable-by-the-user password.
         */
        String randomPassword =
                UUID.randomUUID().toString();

        String encodedPassword = passwordEncoder.encode(randomPassword);

        CreateUserRequest createUserRequest = new CreateUserRequest();

        createUserRequest.setEmail(email);
        createUserRequest.setPassword(encodedPassword);
        createUserRequest.setFirstName(firstName);
        createUserRequest.setLastName(lastName);

        return userServiceClient.createUser(
                createUserRequest
        );
    }

}
