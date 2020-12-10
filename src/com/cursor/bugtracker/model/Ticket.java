package com.cursor.bugtracker.model;

import java.util.UUID;

public class Ticket {

    private String ticketId = UUID.randomUUID().toString();
    private String name;

    public Ticket(String ticketId, String name) {
        this.ticketId = ticketId;
        this.name = name;
    }
}
