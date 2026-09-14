package com.fitness.auth_service.security;


import io.jsonwebtoken.Jwts;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.security.PrivateKey;
import java.util.Date;

@Service
@RequiredArgsConstructor
@Slf4j
public class JwtService {

    private final PrivateKey privateKey;

    @PostConstruct
    public void init() {
        log.info(">>> JwtService initialized");
        log.info(">>> Private key injected: " + (privateKey != null));
    }

    public String generateToken(CustomUserDetails userDetails){

        Date issuedAt = new Date();

        Date expiration = new Date(
                issuedAt.getTime() + 15 * 60 * 1000
        );
        return Jwts.builder()
                .subject(userDetails.getUsername())
                .claim("userId",userDetails.getId())
                .claim("role",userDetails.getRole())
                .issuedAt(issuedAt)
                .expiration(expiration)
                .signWith(privateKey,Jwts.SIG.RS256)
                .compact();

    }


}
