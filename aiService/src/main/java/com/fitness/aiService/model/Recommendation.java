package com.fitness.aiService.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;


@Document(collection = "recommendations")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Recommendation {

    @Id
    private String id;

    private String activityId;
    private String userId;
    private String activityType;

    private Analysis analysis;

    private List<Improvement> improvements;

    private List<Suggestion> suggestions;

    private List<String> safety;

    @CreatedDate
    private LocalDateTime createdAt;


    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Analysis {

        private String overall;
        private String pace;
        private String heartRate;
        private String caloriesBurned;
    }


    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Improvement {

        private String area;
        private String recommendation;
    }


    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Suggestion {

        private String workout;
        private String description;
    }

}
