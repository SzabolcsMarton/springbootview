package com.springbootView.springbootview.exception;

public class UserNotFoundException extends RuntimeException{

    public UserNotFoundException(String errorMessage){
        super(errorMessage);
    }
}
