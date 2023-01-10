package com.springbootView.springbootview.exception;

public class HamburgerNotFoundException extends RuntimeException{

    public HamburgerNotFoundException(String errorMessage){
        super(errorMessage);
    }
}
