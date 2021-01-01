package com.cursor.bugtracker.controller;

import com.cursor.bugtracker.model.Ticket;
import com.cursor.bugtracker.service.TicketService;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class DisplayUtils {

    private static TicketService ticketService = TicketService.getInstance();
    private static int count = 0;

    public static void clearScreen() {
        //Clears Screen in java
        try {
            if (System.getProperty("os.name").contains("Windows"))
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            else
                Runtime.getRuntime().exec("clear");
        } catch (IOException | InterruptedException ex) {
        }
    }

    public static void showUserMessage(final String message) {
        if (!message.equals("")) {
            System.out.println(message);
        }
    }


    public static void displayTickets() {
        List<Ticket> allTickets = ticketService.getAllTickets();
        while (count < allTickets.size()) {
            System.out.println("Ticket №" + (count + 1) +
                    System.lineSeparator() + "name - " + allTickets.get(count).getName() +
                    System.lineSeparator() + "description - " + allTickets.get(count).getDescription() +
                    System.lineSeparator() + "estimated time - " + allTickets.get(count).getEstimatedTime());
            System.out.println();
            count++;
        }
    }

    // displayTickets(ticket)

    //
}