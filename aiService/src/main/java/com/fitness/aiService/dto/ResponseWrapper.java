package com.fitness.aiService.dto;


import lombok.Data;

@Data
public class ResponseWrapper {

    String message;
    boolean apiStatus;
    Object data;

}
