package com.fitness.aiService.dto;


import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class RecommendationResponse {

    private String id;
    private String activityId;
    private String userId;
    private String activityType;

    private AnalysisDTO analysis;

    private List<ImprovementDTO> improvements;

    private List<SuggestionDTO> suggestions;

    private List<String> safety;

    private LocalDateTime createdAt;

    @Data
    public static class AnalysisDTO {

        private String overall;
        private String pace;
        private String heartRate;
        private String caloriesBurned;
    }

    @Data
    public static class ImprovementDTO {

        private String area;
        private String recommendation;
    }

    @Data
   public static class SuggestionDTO {

        private String workout;
        private String description;
    }


}
