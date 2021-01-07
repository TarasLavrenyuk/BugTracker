package com.cursor.bugtracker.dao;

import com.cursor.bugtracker.model.Ticket;

import java.util.List;

public interface TicketDao {

    Ticket save(Ticket ticket);

    Ticket getById(String tickedId);

    List<Ticket> getAllTickets();

    Ticket removeById(String tickedId);
}
