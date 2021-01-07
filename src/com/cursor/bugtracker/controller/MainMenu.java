package com.cursor.bugtracker.controller;

import com.cursor.bugtracker.enums.Priority;
import com.cursor.bugtracker.enums.Status;
import com.cursor.bugtracker.exceptions.InvalidTicketNameException;
import com.cursor.bugtracker.exceptions.TicketNotFoundException;
import com.cursor.bugtracker.exceptions.UserNotFoundException;
import com.cursor.bugtracker.model.Ticket;
import com.cursor.bugtracker.service.TicketService;
import com.cursor.bugtracker.service.UserService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
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
                + System.lineSeparator() + "3. Edit ticket");

        Scanner reader = new Scanner(System.in);
        // clear screen
        String option = reader.nextLine();
        if (option.equals("1")) {
            TicketOperationsScreen.createNewTicket();
        } else if (option.equals("2")) {
            try {
                deleteTicket();
            } catch (IOException e) {
                showTicketMenu("Oops, something went wrong. Please try again.");
            } catch (TicketNotFoundException e) {
                System.out.println(e.getMessage());
            }
        } else if (option.equals("3")) {
            editTicket();
        } else {
            showTicketMenu("Incorrect input");
        }
    }

    public static void deleteTicket() throws IOException, TicketNotFoundException {
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

    public static void editTicket() {
        editTicket("");
    }

    public static void editTicket(final String message) {
        final Scanner scanner = new Scanner(System.in);
        System.out.println("Select the ticket number you want to edit");
        List<Ticket> tickets = ticketService.getAllTickets();
        displayTickets(tickets);
        int numberTicket = scanner.nextInt() - 1;

        if (numberTicket < 0 || numberTicket > tickets.size() - 1) {
            editTicket("Incorrect input");
        }
        Ticket ticket = tickets.get(numberTicket);

        System.out.println("Enter new name of ticket, or skip the step: ");
        String name = ticket.getName();
        if (!scanner.nextLine().isBlank()) {
            name = scanner.nextLine();
        }

        System.out.println("Enter new description, or skip the step: ");
        String description = ticket.getDescription();
        if (!scanner.nextLine().isBlank()) {
            description = scanner.nextLine();
        }

        System.out.println
                ("Enter separated by space new list the names of registered users in the list of assignee" +
                        System.lineSeparator() + ", or skip the step: ");
        List<String> assigneeList = ticket.getAssigneeList();
        if (!scanner.nextLine().isBlank()) {
            String[] tempNameLine = scanner.nextLine().split(" ");
            assigneeList = userService.findByName(tempNameLine);
        }

        System.out.println("Enter new status of your ticket: TODO, IN_PROGRESS, IN_REVIEW, DONE" +
                ", or skip the step:");
        Status status = ticket.getStatus();
        if (!scanner.nextLine().isBlank()) {
            String selectStatus = scanner.nextLine().toUpperCase();
            status = switch (selectStatus) {
                case "TODO" -> Status.TODO;
                case "IN_PROGRESS" -> Status.IN_PROGRESS;
                case "IN_REVIEW" -> Status.IN_REVIEW;
                case "DONE" -> Status.DONE;
                default -> throw new IllegalStateException("Unexpected value: " + selectStatus);
            };
        }

        System.out.println("Enter new priority of ticket: LOW, MEDIUM, HIGH, or skip the step:");
        Priority priority = ticket.getPriority();
        if (!scanner.nextLine().isBlank()) {
            String selectPriority = scanner.nextLine().toUpperCase();
            priority = switch (selectPriority) {
                case "LOW" -> Priority.LOW;
                case "MEDIUM" -> Priority.MEDIUM;
                case "HIGH" -> Priority.HIGH;
                default -> throw new IllegalStateException("Unexpected value: " + selectPriority);
            };
        }

        try {
            ticket = ticketService.edit(
                    ticket.getTicketId(),
                    name,
                    description,
                    assigneeList,
                    status,
                    priority
            );
        } catch (InvalidTicketNameException | UserNotFoundException | TicketNotFoundException e) {
            e.printStackTrace();
        }
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
