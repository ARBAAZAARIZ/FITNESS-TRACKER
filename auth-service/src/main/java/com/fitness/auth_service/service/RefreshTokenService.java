package com.fitness.auth_service.service;

import com.fitness.auth_service.model.RefreshToken;
import com.fitness.auth_service.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Base64;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {


    private final RefreshTokenRepository refreshTokenRepository;

    private final SecureRandom secureRandom = new SecureRandom();


    public String createRefreshToken(String userId) {

        // 1. Generate a cryptographically secure random token
        byte[] randomBytes = new byte[64];
        secureRandom.nextBytes(randomBytes);
        String rawToken = Base64.getUrlEncoder()
                .encodeToString(randomBytes);

        // 2. Hash the token before storing it
        String tokenHash = hashToken(rawToken);

        // 3. Create database entity
        RefreshToken refreshToken = new RefreshToken();

        refreshToken.setUserId(userId);
        refreshToken.setTokenHash(tokenHash);
        refreshToken.setExpiryDate(
                LocalDateTime.now().plusDays(1)
        );
        refreshToken.setRevoked(false);
        refreshToken.setCreatedAt(LocalDateTime.now());

        // 4. Store only the hash
        refreshTokenRepository.save(refreshToken);

        // 5. Return raw token to frontend
        return rawToken;


    }

    private String hashToken(String token) {
        try{

            MessageDigest digest =
                    MessageDigest.getInstance("SHA-256");

            byte[] hash =
                    digest.digest(
                      token.getBytes(StandardCharsets.UTF_8)
                    );

            StringBuilder hexString =
                    new StringBuilder();

            for (byte b : hash){
                String hex =
                        Integer.toHexString(0xff & b);

                if (hex.length() == 1) {
                    hexString.append('0');
                }

                hexString.append(hex);

            }
            return hexString.toString();

        } catch (Exception e) {
            throw new IllegalStateException(
                    "Unable to hash refresh token", e
            );
        }

    }

    public RefreshToken validateRefreshToken(String rawRefreshToken) {

        String tokenHash = hashToken(rawRefreshToken);

        RefreshToken refreshToken =
                refreshTokenRepository.findByTokenHash(tokenHash)
                        .orElseThrow(() ->
                                new IllegalArgumentException(
                                        "Invalid refresh token"
                                )
                        );

        if (Boolean.TRUE.equals(refreshToken.getRevoked())) {
            throw new IllegalArgumentException(
                    "Refresh token has been revoked"
            );
        }

        if (refreshToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException(
                    "Refresh token has expired"
            );
        }

        return refreshToken;

    }

}
