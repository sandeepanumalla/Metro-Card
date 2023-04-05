package com.geektrust.backend.exceptions;

public class NoSuchCommandException extends RuntimeException {

    public NoSuchCommandException(){
        super();
    }

    public NoSuchCommandException(String Message){
        super(Message);
    }
}
