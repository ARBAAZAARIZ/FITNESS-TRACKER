package com.fitness.gateway.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

@Configuration
@Slf4j
public class JwtPublicKeyConfig {

    @Bean
    public PublicKey publicKey() throws Exception{

        ClassPathResource resource =
                new ClassPathResource("keys/public.pem");

        String key = new String(
                resource.getInputStream().readAllBytes(),
                StandardCharsets.UTF_8
        );

        key = key
                .replace("-----BEGIN PUBLIC KEY-----","")
                .replace("-----END PUBLIC KEY-----","")
                .replaceAll("\\s+", "");


        byte[] decodedKey =
                Base64.getDecoder().decode(key);

        X509EncodedKeySpec keySpec =
                new X509EncodedKeySpec(decodedKey);

        KeyFactory keyFactory =
                KeyFactory.getInstance("RSA");

        log.info(">>> RSA Public Key Loaded Successfully");

        return keyFactory.generatePublic(keySpec);

    }

}
