package com.fitness.auth_service.exception;

public class LoginPasswordNotEnabledException extends RuntimeException{

    public LoginPasswordNotEnabledException(String message){
        super(message);
    }

}
