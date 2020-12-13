package com.cursor.bugtracker.service;

import com.cursor.bugtracker.enums.Priority;
import com.cursor.bugtracker.enums.Status;
import com.cursor.bugtracker.exceptions.TicketNotFoundException;
import com.cursor.bugtracker.model.Ticket;

import java.util.List;

// make it singleton
public class TicketService {

        public Ticket create(
            String name,
            String description,
            List<String> assigneeList,
            String reporter,
            Status status,
            Priority priority,
            long estimatedTime
    ) {
        // check name parameter: has at least 2 words
        //        "    Create user dao           "
        final String validatedName = validateName(name);

    }

    private String validateName(String name) {
        return name.trim();
    }

    public Ticket findTicket(String ticketId) throws TicketNotFoundException {
        final Ticket ticket = ticketDao.getTicketById(ticketId);
        if (ticket == null) {
            throw new TicketNotFoundException(ticketId);
        }
    }
}
