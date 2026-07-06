package com.fitness.aiService.controller;


import com.fitness.aiService.service.RecommendationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/recommendations")
public class RecommendationController {

    @Autowired
    RecommendationService recommendationService;


    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getUserRecommendations(String userId){
         return ResponseEntity.ok(recommendationService.getUserRecommendations(userId));
    }

    @GetMapping("/activity/{activityId}")
    public ResponseEntity<?> getActivityRecommendation(String activityId){
        return ResponseEntity.ok(recommendationService.getActivityRecommendation(activityId));
    }



}
