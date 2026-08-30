package com.fitness.aiService.microservice;

import com.fitness.aiService.dto.gemini.GeminiRequestDTO;
import com.fitness.aiService.dto.gemini.GeminiResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(
        name = "gemini-ai-client",
        url = "${gemini.api.url}"
)
public interface GeminiFeignClient {

    @PostMapping(
            "/v1beta/models/gemini-3.1-flash-lite:generateContent"
    )
    GeminiResponseDTO generateContent(
            @RequestHeader("X-goog-api-key") String apiKey,
            @RequestBody GeminiRequestDTO request
    );

}
