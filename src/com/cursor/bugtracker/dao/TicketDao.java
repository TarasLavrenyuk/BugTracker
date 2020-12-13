package com.cursor.bugtracker.dao;

import com.cursor.bugtracker.model.Ticket;

public interface TicketDao {

    Ticket save(Ticket ticket);

    Ticket getById(String tickedId);

    boolean removeById(String tickedId);
}
