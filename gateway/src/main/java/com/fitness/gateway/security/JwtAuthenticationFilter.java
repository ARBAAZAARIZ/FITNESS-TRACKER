package com.fitness.gateway.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;


@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter implements GlobalFilter, Ordered {

    private final JwtValidationService jwtValidationService;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        String path =
                exchange.getRequest()
                        .getPath()
                        .value();

        // Public authentication endpoints
        if (isPublicEndpoint(path)){
            return chain.filter(exchange);
        }

        String authHeader =  exchange.getRequest()
                .getHeaders()
                .getFirst(HttpHeaders.AUTHORIZATION);

        // Authorization header missing
        if (authHeader == null ||
                !authHeader.startsWith("Bearer ")) {

            return unauthorized(
                    exchange,
                    "TOKEN_MISSING",
                    "Authorization token is required"
            );
        }

        String token =
                authHeader.substring(7);

        try{

            jwtValidationService.validateToken(token);

            // JWT is valid
            return chain.filter(exchange);
        }catch (ExpiredJwtException e) {

            // Access token has expired
            return unauthorized(
                    exchange,
                    "TOKEN_EXPIRED",
                    "Access token has expired"
            );

        } catch (JwtException e) {

            // Invalid / malformed / tampered token
            return unauthorized(
                    exchange,
                    "TOKEN_INVALID",
                    "Invalid access token"
            );
        }

    }

    @Override
    public int getOrder() {
        return -100;
    }

    private boolean isPublicEndpoint(String path){
        return path.equals("/api/auth/login")
                || path.equals("/api/auth/signup")
                || path.equals("/api/auth/refresh");
    }

    private Mono<Void> unauthorized(
            ServerWebExchange exchange,
            String code,
            String message) {

        exchange.getResponse()
                .setStatusCode(HttpStatus.UNAUTHORIZED);

        exchange.getResponse()
                .getHeaders()
                .set(HttpHeaders.CONTENT_TYPE,
                        "application/json");

        return exchange.getResponse()
                .setComplete();
    }

}
