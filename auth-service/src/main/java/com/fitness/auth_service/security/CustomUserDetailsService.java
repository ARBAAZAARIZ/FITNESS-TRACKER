package com.fitness.auth_service.security;

import com.fitness.auth_service.config.UserServiceClient;
import com.fitness.auth_service.dto.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserServiceClient userServiceClient;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserResponse user = userServiceClient.getUserByEmail(username);

        if (user == null || !user.getApiStatus()) {
            throw new UsernameNotFoundException(
                    "User not found with email: " + username
            );
        }

        return new CustomUserDetails(
                user.getId(),
                user.getEmail(),
                user.getPassword(),
                user.getRole(),
                user.getEnabled()
        );
    }
}
