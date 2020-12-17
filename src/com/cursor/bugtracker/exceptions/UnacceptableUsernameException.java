package com.cursor.bugtracker.exceptions;

public class UnacceptableUsernameException extends Exception {
    public UnacceptableUsernameException(String description) {
        super(description);
    }
}
