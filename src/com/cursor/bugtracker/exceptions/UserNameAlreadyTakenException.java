package com.cursor.bugtracker.exceptions;

public class UserNameAlreadyTakenException extends Exception{
    public UserNameAlreadyTakenException(String description) {
        super(description);
    }
}
