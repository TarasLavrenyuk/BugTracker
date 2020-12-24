package com.cursor.bugtracker.controller;

import com.cursor.bugtracker.service.TicketService;
import com.cursor.bugtracker.service.UserService;

public class MainMenu {

    private static UserService userService = UserService.getInstance();
    private static TicketService ticketService = TicketService.getInstance();

    public static void displayMainMenu(final String message) {
        // TODO ...
    }
}
