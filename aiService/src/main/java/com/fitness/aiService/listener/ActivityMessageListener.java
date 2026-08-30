package com.fitness.aiService.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fitness.aiService.model.Activity;
import com.fitness.aiService.model.Recommendation;
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


    private final ObjectMapper objectMapper;

    @RabbitListener(queues = "${rabbitmq.queue.name}")
    public void onMessage(Activity activity) {

        log.info("Received message: {}", activity);

        try {

         String aiResponse =   aiService.generateRecommendation(activity);

         log.info("AI Response after processing the data {}",aiResponse);

            Recommendation recommendation = objectMapper.readValue(aiResponse, Recommendation.class);

            log.info("Recommendation generated: {}", recommendation);

        } catch (Exception e) {

            log.error("Error processing activity: {}", activity.getId(), e);

        }
    }




}
