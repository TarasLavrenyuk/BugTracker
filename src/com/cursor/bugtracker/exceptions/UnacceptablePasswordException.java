package com.cursor.bugtracker.exceptions;

public class UnacceptablePasswordException extends Exception{
    public UnacceptablePasswordException(String description) {
        super(description);
    }
}
