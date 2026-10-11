package com.fitness.auth_service.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fitness.auth_service.dto.LoginResponse;
import com.fitness.auth_service.dto.ResponseWrapper;
import com.fitness.auth_service.dto.UserResponse;
import com.fitness.auth_service.service.GoogleUserProvisioningService;
import com.fitness.auth_service.service.RefreshTokenService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class GoogleOAuth2SuccessHandler implements AuthenticationSuccessHandler {

    private final ObjectMapper objectMapper;

    private final GoogleUserProvisioningService googleUserProvisioningService;

    private final JwtService jwtService;

    private final RefreshTokenService refreshTokenService;


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication)
            throws IOException, ServletException {
        // 1. Get the authenticated Google user
        OidcUser oidcUser =
                (OidcUser) authentication.getPrincipal();

        // 2. Find existing local user or create a new one
        UserResponse user =
                googleUserProvisioningService.findOrCreateUser(
                        oidcUser
                );

        // 3. Convert our local user into CustomUserDetails

        CustomUserDetails userDetails =
                new CustomUserDetails(
                        user.getId(),
                        user.getEmail(),
                        user.getPassword(),
                        user.getRole(),
                        Boolean.TRUE.equals(user.getEnabled())
                );

        // 4. Generate YOUR access token
        String accessToken =
                jwtService.generateToken(userDetails);

        // 5. Generate YOUR refresh token
        String refreshToken =
                refreshTokenService.createRefreshToken(
                        user.getId()
                );

        // 6. Build the same login response structure
        LoginResponse loginResponse =
                new LoginResponse(
                        true,
                        "Google login successful",
                        accessToken,
                        refreshToken,
                        user.getId(),
                        user.getEmail()
                );

        ResponseWrapper responseWrapper =
                new ResponseWrapper();

        responseWrapper.setApiStatus(true);
        responseWrapper.setMessage(
                "Google login successful"
        );
        responseWrapper.setData(loginResponse);

        // 7. Send JSON response
        response.setStatus(
                HttpServletResponse.SC_OK
        );
        response.setContentType("application/json");


        response.getWriter().write(
                objectMapper.writeValueAsString(responseWrapper)
        );

    }
}
