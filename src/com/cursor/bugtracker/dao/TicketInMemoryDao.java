package com.cursor.bugtracker.dao;

import com.cursor.bugtracker.enums.Priority;
import com.cursor.bugtracker.enums.Status;
import com.cursor.bugtracker.interfaces.Singleton;
import com.cursor.bugtracker.model.Ticket;
import com.cursor.bugtracker.service.TicketService;

import java.util.*;

public class TicketInMemoryDao implements TicketDao, Singleton {
    private static TicketInMemoryDao instance;

    public static TicketInMemoryDao getInstance() {
        if(instance == null)
            instance = new TicketInMemoryDao();
        return instance;
    }

    private Map<String, Ticket> tickets = new HashMap<>();

    public TicketInMemoryDao() {
        String id1 = UUID.randomUUID().toString();
        Ticket ticket1 = new Ticket(
                id1,
                "Create app",
                "Description of the god damn task",
                new ArrayList<>(),
                "5a817bdd-8294-4b05-a8f6-dc0e631563e5",
                Status.TODO,
                Priority.MEDIUM,
                18000, // 5 hours
                5000
                );

        tickets.put(id1, ticket1);
    }

    @Override
    public Ticket save(Ticket ticket) {
        tickets.put(ticket.getTicketId(), ticket);
        return ticket;
    }

    @Override
    public Ticket getById(String ticketId) {
        return tickets.get(ticketId);
    }

    @Override
    public boolean removeById(String ticketId) {

    }

    public List<Ticket> findByName(String query) {
        final List<Ticket> result = new LinkedList<>();

        //// find tickets

        return result;
    }
}
