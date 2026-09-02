package com.fitness.aiService.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class ResponseWrapper {

    String message;
    boolean apiStatus;
    Object data;

}
