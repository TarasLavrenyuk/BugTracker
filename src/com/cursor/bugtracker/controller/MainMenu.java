package com.cursor.bugtracker.controller;

import com.cursor.bugtracker.model.Ticket;
import com.cursor.bugtracker.service.TicketService;
import com.cursor.bugtracker.service.UserService;

import java.util.List;

public class MainMenu {

    private static UserService userService = UserService.getInstance();
    private static TicketService ticketService = TicketService.getInstance();

    public static void displayMainMenu(final String message) {
        // TODO ...
    }

    public static void showAllTickets(final String message) {
        List<Ticket> allTickets = ticketService.getAllTickets();
    }
}
