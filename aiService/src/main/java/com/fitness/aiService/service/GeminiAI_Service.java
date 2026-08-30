package com.fitness.aiService.service;

import com.fitness.aiService.dto.gemini.GeminiRequestDTO;
import com.fitness.aiService.dto.gemini.GeminiResponseDTO;
import com.fitness.aiService.microservice.GeminiFeignClient;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class GeminiAI_Service {

    private final GeminiFeignClient geminiFeignClient;


    @Value("${gemini.api.key}")
    private String apiKey;

    public String generateRecommendation(String prompt){

        GeminiRequestDTO.Part part =
                new GeminiRequestDTO.Part(prompt);


        GeminiRequestDTO.Content content =
                new GeminiRequestDTO.Content(List.of(part));

        GeminiRequestDTO request =
                new GeminiRequestDTO(List.of(content));

        GeminiResponseDTO response =
                geminiFeignClient.generateContent(
                        apiKey,
                        request
                );

        return response
                .getCandidates()
                .get(0)
                .getContent()
                .getParts()
                .get(0)
                .getText();

    }

}
