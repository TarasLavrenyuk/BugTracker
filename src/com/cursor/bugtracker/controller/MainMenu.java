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


    private static UserService userService = UserService.getInstance();
    private static TicketService ticketService = TicketService.getInstance();


    public static void displayMainMenu(final String message) throws IOException {
        System.out.println("Hello" + "UserNAME");

        System.out.println("Please choose option\n" +
                "1. Show all tickets\n" +
                "2. Create new ticket\n" +
                "3. Logout");

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            String option = reader.readLine();
            if (option.equals("1")) {
                showAllTickets(" ");
            } else if (option.equals("2")) {
                createNewTicket();
            } else if (option.equals("3")) {
                logOut();
            }
        } catch (IOException E) {
            displayMainMenu("WRONG INPUT");
        }
    }


    public static void showAllTickets(final String message) {

        List<Ticket> allTickets = ticketService.getAllTickets();
    }


    public static void optionSelectionScreen()
            throws IOException, TicketNotFoundException {
        optionSelectionScreen("");
    }

    public static void optionSelectionScreen(final String message)
            throws IOException, TicketNotFoundException {
        DisplayUtils.displayTickets();
        System.out.println("Choose your option:"
                + System.lineSeparator() + "1. Create new ticket"
                + System.lineSeparator() + "2. Delete ticket"
                + System.lineSeparator() + "3. Edit ticket");

        Scanner reader = new Scanner(System.in);
        DisplayUtils.clearScreen();
        String option = reader.nextLine();
        if (option.equals("1")) {
            createNewTicket();
        } else if (option.equals("2")) {
            ticketDelete();
        } else if (option.equals("3")) {
            ticketEdit();
        } else {
            optionSelectionScreen("Incorrect input");
        }
    }

    public static void createNewTicket() {
    }

    public static void ticketDelete() throws IOException, TicketNotFoundException {
        ticketDelete("");
    }

    public static void ticketDelete(final String message)
            throws IOException, TicketNotFoundException {
        DisplayUtils.displayTickets();

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter ticket number");
        int option = scanner.nextInt() - 1;

        if (ticketService.getAllTickets().get(option) != null) {
            String deleteID = ticketService.getAllTickets().get(option).getTicketId();
            ticketService.delete(deleteID);
        } else {
            throw new IOException();
        }
        ticketDelete("Incorrect input");
    }

    public static void ticketEdit() {

    }
}
