package com.geektrust.backend.exceptions;

public class MetroCardNotFoundException extends RuntimeException {
    
    public MetroCardNotFoundException(){
        super();
    }

    public MetroCardNotFoundException(String Message){
        super(Message);
    }
}
