package com.fitness.aiService.service;

import com.fitness.aiService.dto.ResponseWrapper;

public interface RecommendationService {


    ResponseWrapper getUserRecommendations(String userId);

    ResponseWrapper getActivityRecommendation(String activityId);

}
