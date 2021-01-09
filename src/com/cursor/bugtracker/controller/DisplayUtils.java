package com.cursor.bugtracker.controller;

import com.cursor.bugtracker.dto.TicketDto;
import com.cursor.bugtracker.model.Ticket;
import com.cursor.bugtracker.model.User;
import com.cursor.bugtracker.service.TicketService;

import java.io.IOException;
import java.util.List;


public class DisplayUtils {

    public static void clearScreen() {
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

    /**
     * Show one ticket
     */
    public static void displayTicket(final TicketDto ticket, final int index) {
        System.out.println("Ticket №" + (index) +
                System.lineSeparator() + "name - " + ticket.getName() +
                System.lineSeparator() + "description - " + ticket.getDescription() +
                System.lineSeparator() + "status - " + ticket.getStatus() +
                System.lineSeparator() + "priority - " + ticket.getPriority() +
                System.lineSeparator() + "reporter - " + ticket.getReporterUsername() +
                System.lineSeparator() + "assignees - " + (ticket.getAssigneesUsernames().isEmpty() ? "no assignees yet" : ticket.getAssigneesUsernames()) +
                System.lineSeparator() + "spent time - " + ticket.getSpentTime() + " minutes" +
                System.lineSeparator() + "estimated time - " + ticket.getEstimatedTime() + " minutes" +
                System.lineSeparator());
    }

    public static void displayUsernames(List<User> users) {
        System.out.println("Users: ");
        users.forEach(user -> System.out.print(user.getName() + " "));
        System.out.println();
    }
}