package com.fitness.aiService.dto;


import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class RecommendationReqResDTO {

    private String id;
    private String activityId;
    private String userId;
    private String activityType;
    private String recommendation;
    private List<String> improvement;
    private List<String> suggestions;
    private List<String> safety;
    private LocalDateTime createdAt;


}
