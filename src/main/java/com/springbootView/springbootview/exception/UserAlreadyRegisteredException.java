package com.springbootView.springbootview.exception;

public class UserAlreadyRegisteredException extends RuntimeException{

    public UserAlreadyRegisteredException(String errorMessage){
        super(errorMessage);
    }
}
