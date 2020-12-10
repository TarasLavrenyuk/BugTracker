package com.cursor.bugtracker.model;

import java.util.List;
import java.util.UUID;

public class Ticket {

    private String ticketId;
    private String name;
    private String description;
    private List<String> assigneeList;
    private String reporter;
    private Status status;
    private Priority priority;
    private long estimatedTime;
    private long spentTime;

    public Ticket(String name) {
        this.ticketId = UUID.randomUUID().toString();
        this.name = name;
    }
}
