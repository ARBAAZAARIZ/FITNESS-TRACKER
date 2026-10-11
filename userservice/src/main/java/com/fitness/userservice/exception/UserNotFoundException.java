package com.fitness.userservice.exception;

public class UserNotFoundException extends RuntimeException{

    public UserNotFoundException (String errMsg){
        super(errMsg);
    }

}
