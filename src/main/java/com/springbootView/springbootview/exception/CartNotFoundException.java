package com.springbootView.springbootview.exception;

public class CartNotFoundException extends RuntimeException{

    public CartNotFoundException(String errorMessage){
        super(errorMessage);
    }
}
