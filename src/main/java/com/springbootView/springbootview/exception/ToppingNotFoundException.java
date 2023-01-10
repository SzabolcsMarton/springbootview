package com.springbootView.springbootview.exception;

public class ToppingNotFoundException extends RuntimeException{

    public ToppingNotFoundException(String errorMessage){
        super(errorMessage);
    }
}
