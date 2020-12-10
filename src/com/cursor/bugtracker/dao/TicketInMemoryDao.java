package com.cursor.bugtracker.dao;

import com.cursor.bugtracker.model.Ticket;

import java.util.Arrays;
import java.util.List;

public class TicketInMemoryDao implements TicketDao {

    private List<Ticket> db = Arrays.asList(

    );

    @Override
    public Ticket save(String tickedId) {
        return null;
    }

    @Override
    public Ticket getById(String tickedId) {
        return null;
    }

    @Override
    public Ticket removeById(String tickedId) {
        return null;
    }
}
