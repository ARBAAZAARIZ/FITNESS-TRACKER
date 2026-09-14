package com.fitness.auth_service.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

@Configuration
@Slf4j
public class JwtKeyConfig {


    @Bean
    public PrivateKey privateKey() throws Exception{
        ClassPathResource resource =
                new ClassPathResource("keys/private.pem");

        String key = new String (
                resource.getInputStream().readAllBytes(),
                StandardCharsets.UTF_8
        );

        key = key
                .replace("-----BEGIN PRIVATE KEY-----","")
                .replace("-----END PRIVATE KEY-----","")
                .replaceAll("\\s+","");

        System.out.println(key);

        byte[] decodedKey = Base64.getDecoder().decode(key);

        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(decodedKey);

        KeyFactory keyFactory =
                KeyFactory.getInstance("RSA");

        System.out.println(">>> RSA Private Key Loaded Successfully");

        return keyFactory.generatePrivate(keySpec);

    }

}
