package com.fitness.aiService.service.impl;


import com.fitness.aiService.dto.RecommendationResponse;
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
    public ResponseWrapper saveRecommendation(Recommendation recommendation) {

        Recommendation savedRecommendation = recommendationRepository.save(recommendation);

        ResponseWrapper responseWrapper = new ResponseWrapper();

        if( savedRecommendation.getId() != null ){

            responseWrapper.setApiStatus(true);
            responseWrapper.setMessage("Recommendation saved successfully");
            return responseWrapper;
        }

        responseWrapper.setApiStatus(false);
        responseWrapper.setMessage("Failed to save recommendation");
        return responseWrapper;
    }

    @Override
    public ResponseWrapper getUserRecommendations(String userId) {

        List<Recommendation> recommendations = recommendationRepository.findByUserId(userId);

        List<RecommendationResponse> responseRecommendationData = recommendations
                .stream()
                .map(this::mapToResponse)
                .toList();

        return new ResponseWrapper(
                "Recommendations fetched successfully",
                true,
                responseRecommendationData
        );
    }

    private RecommendationResponse mapToResponse(Recommendation recommendation){

        RecommendationResponse response = new RecommendationResponse();

        // Basic Information
        response.setId(recommendation.getId());
        response.setActivityId(recommendation.getActivityId());
        response.setUserId(recommendation.getUserId());
        response.setActivityType(recommendation.getActivityType());
        response.setCreatedAt(recommendation.getCreatedAt());

        // Analysis
        if(recommendation.getAnalysis() != null){

            RecommendationResponse.AnalysisDTO analysis = new RecommendationResponse.AnalysisDTO();
            analysis.setOverall(recommendation.getAnalysis().getOverall());
            analysis.setPace(recommendation.getAnalysis().getPace());
            analysis.setHeartRate(recommendation.getAnalysis().getHeartRate());
            analysis.setCaloriesBurned(recommendation.getAnalysis().getCaloriesBurned());
            response.setAnalysis(analysis);
        }

        // Improvements
        if(recommendation.getImprovements() != null){
            List<RecommendationResponse.ImprovementDTO> improvements =
                    recommendation.getImprovements()
                            .stream().map(imp->{
                                RecommendationResponse.ImprovementDTO  improvement =
                                        new RecommendationResponse.ImprovementDTO();
                                improvement.setRecommendation(imp.getRecommendation());
                                improvement.setArea(imp.getArea());
                                return improvement;

                            }).toList();
            response.setImprovements(improvements);
        }

        // Suggestions
        if(recommendation.getSuggestions() != null){

            List<RecommendationResponse.SuggestionDTO> suggestions = recommendation.getSuggestions()
                    .stream()
                    .map(sugg->{

                        RecommendationResponse.SuggestionDTO  suggestion =
                                new RecommendationResponse.SuggestionDTO();

                        suggestion.setWorkout(sugg.getWorkout());
                        suggestion.setDescription(sugg.getDescription());
                        return suggestion;

                    }).toList();
            response.setSuggestions(suggestions);
        }

        // Safety
        if(recommendation.getSafety() != null){
            List<String> safety = recommendation.getSafety();
            response.setSafety(safety);
        }

        return response;

    }


    @Override
    public ResponseWrapper getActivityRecommendation(String activityId) {

       Optional <Recommendation> recommendationOptional =  recommendationRepository.findByActivityId(activityId);

        ResponseWrapper responseWrapper = new ResponseWrapper();

        if (recommendationOptional.isEmpty()) {
            responseWrapper.setApiStatus(false);
            responseWrapper.setMessage(
                    "No recommendation found for activity: " + activityId
            );
            return responseWrapper;
        }
        RecommendationResponse response =
                mapToResponse(recommendationOptional.get());

        responseWrapper.setApiStatus(true);
        responseWrapper.setMessage("Recommendation fetched successfully");
        responseWrapper.setData(response);

        return responseWrapper;

    }


}
