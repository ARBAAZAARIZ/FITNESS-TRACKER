package com.fitness.auth_service.dto;

import lombok.Data;

@Data
public class ResponseWrapper {

    private String message;
    private Boolean apiStatus;
    private Object data;

}
