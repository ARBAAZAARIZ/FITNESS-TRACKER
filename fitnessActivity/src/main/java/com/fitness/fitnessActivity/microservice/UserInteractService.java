package com.fitness.fitnessActivity.microservice;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientException;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserInteractService {


    private final WebClient userServiceWebClient;

    public Map validateUser(String userId){

        try{
            return userServiceWebClient
                    .get()
                    .uri("api/users/validate/{userId}",userId)
                    .retrieve().bodyToMono(Map.class)
                    .block();
        } catch (WebClientResponseException e) {
            if(e.getStatusCode()== HttpStatus.NOT_FOUND){
                throw new RuntimeException(" User not found: "+ userId);
            } else if (e.getStatusCode()== HttpStatus.BAD_REQUEST) {
                throw new RuntimeException(" Invalid userId : "+ userId);
            }
        }


        return Map.of();
    }


}
