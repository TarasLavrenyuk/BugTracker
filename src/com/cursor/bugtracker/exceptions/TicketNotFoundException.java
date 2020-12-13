package com.cursor.bugtracker.exceptions;

public class TicketNotFoundException extends Exception {

    private String ticketId;

    public TicketNotFoundException(String ticketId) {
        this.ticketId = ticketId;
    }

    @Override
    public String getMessage() {
        return "Ticket with Id: " + ticketId + " was not found.";
    }
}
