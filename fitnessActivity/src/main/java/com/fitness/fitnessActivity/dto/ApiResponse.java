package com.fitness.fitnessActivity.dto;

import lombok.Data;

@Data
public class ApiResponse {

    Boolean apiStatus;
    String message;
    Object data;

}
