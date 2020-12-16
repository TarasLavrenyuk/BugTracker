package com.cursor.bugtracker.exceptions;

public class InvalidTicketNameException extends Exception{

    public InvalidTicketNameException(String message) {
        super(message);
    }
}