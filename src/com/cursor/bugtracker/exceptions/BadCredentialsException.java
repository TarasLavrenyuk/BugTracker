package com.cursor.bugtracker.exceptions;

public class BadCredentialsException extends Exception {
    public BadCredentialsException (String message){
        super(message);
    }
}
