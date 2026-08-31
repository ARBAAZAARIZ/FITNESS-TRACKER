package com.fitness.aiService.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fitness.aiService.dto.ResponseWrapper;
import com.fitness.aiService.model.Activity;
import com.fitness.aiService.model.Recommendation;
import com.fitness.aiService.service.RecommendationService;
import com.fitness.aiService.service.impl.ActivityAI_Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ActivityMessageListener {

    @Value("${rabbitmq.queue.name}")
    private String queueName;

    private final ActivityAI_Service aiService;

    private final RecommendationService recommendationService;


    private final ObjectMapper objectMapper;

    @RabbitListener(queues = "${rabbitmq.queue.name}")
    public void onMessage(Activity activity) {

        log.info("Received message: {}", activity);

        try {

         String aiResponse =   aiService.generateRecommendation(activity);

         log.info("AI Response after processing the data {}",aiResponse);

            Recommendation recommendation = objectMapper.readValue(aiResponse, Recommendation.class);

            recommendation.setActivityId(activity.getId());
            recommendation.setUserId(activity.getUserId());
            recommendation.setActivityType(activity.getType().toString());

            log.info("Recommendation generated: {}", recommendation);

            ResponseWrapper responseWrapper = recommendationService.saveRecommendation(recommendation);

            if(responseWrapper.isApiStatus()){
                log.info("Recommendation saved successfully");
            }else{
                log.error("Failed to save recommendation");
            }

        } catch (Exception e) {

            log.error("Error processing activity: {}", activity.getId(), e);

        }
    }




}
