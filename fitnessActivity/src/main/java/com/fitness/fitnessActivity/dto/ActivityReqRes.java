package com.fitness.fitnessActivity.dto;


import com.fitness.fitnessActivity.model.ActivityType;
import lombok.Builder;
import lombok.*;
import lombok.RequiredArgsConstructor;


import java.time.LocalDateTime;
import java.util.Map;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ActivityReqRes {


    private String id;
    private String userId;
    private ActivityType type;
    private Integer duration;
    private Integer caloriesBurned;
    private LocalDateTime startTime;
    private Map<String, Object> additionalMetrics;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private Boolean apiStatus;
    private String message;

}

