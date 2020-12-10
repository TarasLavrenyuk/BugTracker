package com.cursor.bugtracker.dao;

import com.cursor.bugtracker.model.Ticket;

public interface TicketDao {

    Ticket save(String tickedId);

    Ticket getById(String tickedId);

    Ticket removeById(String tickedId);
}
