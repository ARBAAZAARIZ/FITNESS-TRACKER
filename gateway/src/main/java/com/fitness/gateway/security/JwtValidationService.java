package com.fitness.gateway.security;


import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.PublicKey;

@Service
@RequiredArgsConstructor
public class JwtValidationService {

    private final PublicKey publicKey;

    public Jws<Claims> validateToken(String token){
        return Jwts.parser()
                .verifyWith(publicKey)
                .build()
                .parseSignedClaims(token);
    }

    public boolean isExpired(String token){
        try{

            validateToken(token);
            return false;

        } catch (ExpiredJwtException e) {
            return true;
        } catch (JwtException e) {
            return true;
        }
    }


}
