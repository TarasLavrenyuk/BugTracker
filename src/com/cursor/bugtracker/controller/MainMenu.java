package com.cursor.bugtracker.controller;
import com.cursor.bugtracker.exceptions.TicketNotFoundException;
import com.cursor.bugtracker.model.Ticket;
import com.cursor.bugtracker.service.TicketService;
import com.cursor.bugtracker.service.UserService;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Scanner;

import static com.cursor.bugtracker.controller.LoginScreen.logOut;


public class MainMenu {

    private static TicketService ticketService = TicketService.getInstance();
    private static UserService userService = UserService.getInstance();

    public static void displayMainMenu(final String message) {
        System.out.println(message);
        System.out.println("Hello " + Session.currentUser.getName());

        System.out.println("""
                Please choose option
                1. Show all tickets
                2. Create new ticket
                3. Logout""");

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            String option = reader.readLine();
            if (option.equals("1")) {
                showTicketMenu();
            } else if (option.equals("2")) {
                TicketOperationsScreen.createNewTicket();
            } else if (option.equals("3")) {
                logOut();
            }
        } catch (IOException E) {
            displayMainMenu("WRONG INPUT");
        }
    }

    public static void showTicketMenu() {
        showTicketMenu("");
    }

    public static void showTicketMenu(final String message) {
        // clearScreen
        displayTickets();
        System.out.println(message);
        System.out.println("Choose your option:"
                + System.lineSeparator() + "1. Create new ticket"
                + System.lineSeparator() + "2. Delete ticket"
                + System.lineSeparator() + "3. Edit ticket"
                + System.lineSeparator() + "4. Back to the previous menu");

        Scanner reader = new Scanner(System.in);
        // clear screen
        String option = reader.nextLine();
        if (option.equals("1")) {
            TicketOperationsScreen.createNewTicket();
        } else if (option.equals("2")) {
            deleteTicket();
        } else if (option.equals("3")) {
            TicketOperationsScreen.editTicket();
        } else if (option.equals("4")) {
            displayMainMenu("");
        } else {
            showTicketMenu("Incorrect input");
        }
    }

    public static void deleteTicket() {
        deleteTicket("");
    }

    public static void deleteTicket(final String message) {
        System.out.println(message);
        List<Ticket> tickets = ticketService.getAllTickets();
        displayTickets(tickets);

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter ticket number to delete");
        int ticketIndex = scanner.nextInt() - 1;
        if (ticketIndex < 0 || ticketIndex > tickets.size() - 1) {
            deleteTicket("A ticket with this number does not exist. Enter the number again");
        }

        Ticket ticketToDelete = tickets.get(ticketIndex);
        if (ticketToDelete != null) {
            try {
                ticketService.delete(ticketToDelete.getTicketId());
            } catch (TicketNotFoundException e) {
                deleteTicket("Something went wrong. Ticket was not deleted. Try again.");
            }
        } else {
            deleteTicket("Incorrect input");
        }
        showTicketMenu();
    }


    /**
     * Show all tickets
     */
    public static void displayTickets(List<Ticket> tickets) {
        for (int index = 1; index <= tickets.size(); index++) {
            DisplayUtils.displayTicket(tickets.get(index - 1), index);
        }
    }

    /**
     * Show all tickets
     */
    public static void displayTickets() {
        displayTickets(ticketService.getAllTickets());
    }
}
