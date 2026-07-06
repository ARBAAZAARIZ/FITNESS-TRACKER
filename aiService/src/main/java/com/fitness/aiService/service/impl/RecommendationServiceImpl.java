package com.fitness.aiService.service.impl;


import com.fitness.aiService.dto.RecommendationReqResDTO;
import com.fitness.aiService.dto.ResponseWrapper;
import com.fitness.aiService.model.Recommendation;
import com.fitness.aiService.repository.RecommendationRepository;

import com.fitness.aiService.service.RecommendationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RecommendationServiceImpl implements RecommendationService {


    @Autowired
    RecommendationRepository recommendationRepository;

    @Override
    public ResponseWrapper getUserRecommendations(String userId){

        ResponseWrapper responseWrapper = new ResponseWrapper();

        try{
            List<Recommendation> recommendations = recommendationRepository.findByUserId(userId);

            List<RecommendationReqResDTO> responseRecommendationData =
                    recommendations
                            .stream()
                            .map(recommendation->mapToResponse(recommendation))
                            .toList();

            responseWrapper.setData(responseRecommendationData);
            responseWrapper.setApiStatus(true);
            responseWrapper.setMessage("Recommendations fetched successfully");
            return responseWrapper;

        } catch (Exception e) {
            responseWrapper.setApiStatus(false);
            responseWrapper.setMessage("Failed to fetch recommendations");
            return responseWrapper;
        }
    }

    @Override
    public ResponseWrapper getActivityRecommendation(String activityId){

        ResponseWrapper responseWrapper = new ResponseWrapper();

        try{
            Recommendation recommendation = recommendationRepository.findByActivityId(activityId).orElseThrow(
                    () -> new RuntimeException("Recommendation not found for activityId: " + activityId)
            );

            RecommendationReqResDTO responseRecommendationData =mapToResponse(recommendation);

            responseWrapper.setData(responseRecommendationData);
            responseWrapper.setApiStatus(true);
            responseWrapper.setMessage("Recommendations fetched successfully");
            return responseWrapper;

        } catch (RuntimeException e) {
            responseWrapper.setApiStatus(false);
            responseWrapper.setMessage("Failed to fetch recommendations");
            return responseWrapper;
        }
    }

    private Recommendation mapToModel(RecommendationReqResDTO dto) {

        return Recommendation.builder()
                .id(dto.getId())
                .activityId(dto.getActivityId())
                .userId(dto.getUserId())
                .activityType(dto.getActivityType())
                .recommendation(dto.getRecommendation())
                .improvement(dto.getImprovement())
                .suggestions(dto.getSuggestions())
                .safety(dto.getSafety())
                .createdAt(dto.getCreatedAt())
                .build();
    }
    private RecommendationReqResDTO mapToResponse(Recommendation recommendation) {

        RecommendationReqResDTO response = new RecommendationReqResDTO();

        response.setId(recommendation.getId());
        response.setActivityId(recommendation.getActivityId());
        response.setUserId(recommendation.getUserId());
        response.setActivityType(recommendation.getActivityType());
        response.setRecommendation(recommendation.getRecommendation());
        response.setImprovement(recommendation.getImprovement());
        response.setSuggestions(recommendation.getSuggestions());
        response.setSafety(recommendation.getSafety());
        response.setCreatedAt(recommendation.getCreatedAt());

        return response;
    }



}
