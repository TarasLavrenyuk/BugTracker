package com.cursor.bugtracker.exceptions;

public class WrongUsernameException extends Exception {

    public WrongUsernameException(String description) {
        super(description);
    }
}
