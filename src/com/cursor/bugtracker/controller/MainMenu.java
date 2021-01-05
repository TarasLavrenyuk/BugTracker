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

    public static void displayMainMenu(final String message) throws IOException {
        System.out.println("Hello" + "UserNAME");

        System.out.println("""
                Please choose option
                1. Show all tickets
                2. Create new ticket
                3. Logout""");

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
        // todo
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
            deleteTicket();
        } else if (option.equals("3")) {
            editTicket();
        } else {
            optionSelectionScreen("Incorrect input");
        }
    }

    public static void createNewTicket() {
        // sout(New ticket creation)

        // sout(Enter name: )
        // scan(name)

        // sout(Enter description: )
        // scan(description)

        // sout(Enter ......: )
        // scan(.....)

        // try {
        // Ticket newTicket = ticketService(name, description, .......)
        // } catch (Exception 1) {
        // process exception
        // } catch (Exception 2) {
        // process exception
        // }

        // return
    }

    public static void deleteTicket() throws IOException, TicketNotFoundException {
        deleteTicket("");
    }

    public static void deleteTicket(final String message)
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
        deleteTicket("Incorrect input");
    }

    public static void editTicket() {
        // sout(New ticket creation)

        // sout(Edit name: )
        // sout(name): user should be able to edit this text
        // scan(name)

        // sout(Enter description: )
        // scan(description)

        // sout(Enter ......: )
        // scan(.....)

        // try {
        // Ticket newTicket = ticketService(name, description, .......)
        // } catch (Exception 1) {
        // process exception
        // } catch (Exception 2) {
        // process exception
        // }

        // return

    }
}
