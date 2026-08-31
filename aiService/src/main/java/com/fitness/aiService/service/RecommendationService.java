package com.fitness.aiService.service;

import com.fitness.aiService.dto.ResponseWrapper;
import com.fitness.aiService.model.Recommendation;

public interface RecommendationService {


    ResponseWrapper getUserRecommendations(String userId);

    ResponseWrapper getActivityRecommendation(String activityId);

    ResponseWrapper saveRecommendation(Recommendation  recommendation);

}
