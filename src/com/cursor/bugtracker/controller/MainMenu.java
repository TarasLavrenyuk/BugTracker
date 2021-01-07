package com.cursor.bugtracker.controller;

import com.cursor.bugtracker.enums.Priority;
import com.cursor.bugtracker.enums.Status;
import com.cursor.bugtracker.exceptions.InvalidEstimatedTimeException;
import com.cursor.bugtracker.exceptions.InvalidTicketNameException;
import com.cursor.bugtracker.exceptions.TicketNotFoundException;
import com.cursor.bugtracker.exceptions.UserNotFoundException;
import com.cursor.bugtracker.model.Ticket;
import com.cursor.bugtracker.service.TicketService;
import com.cursor.bugtracker.service.UserService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static com.cursor.bugtracker.controller.LoginScreen.logOut;


public class MainMenu {

    private static TicketService ticketService = TicketService.getInstance();
    private static UserService userService = UserService.getInstance();

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
        final Scanner scanner = new Scanner(System.in);
        System.out.println("New ticket creation");

        System.out.println("Enter name of ticket: ");
        String name = scanner.nextLine();

        System.out.println("Enter a description, or skip the step ");
        String description = scanner.nextLine();

        System.out.println
                ("Enter separated by space the names of registered users in the list of assignee");
        String[] tempNameLine = scanner.nextLine().split(" ");
        List<String> assigneeList = userService.findByName(tempNameLine); //определиться с тикетСервис

        System.out.println("Enter the name of reporter");
        String reporter = scanner.nextLine();

        System.out.println("Enter the status of your ticket: TODO, IN_PROGRESS, IN_REVIEW, DONE;");
        String selectStatus = scanner.nextLine().toUpperCase();
        Status status = switch (selectStatus) {
            case "TODO" -> Status.TODO;
            case "IN_PROGRESS" -> Status.IN_PROGRESS;
            case "IN_REVIEW" -> Status.IN_REVIEW;
            case "DONE" -> Status.DONE;
            default -> throw new IllegalStateException("Unexpected value: " + selectStatus);
        };

        System.out.println("Enter priority of ticket: LOW, MEDIUM, HIGH");
        String selectPriority = scanner.nextLine().toUpperCase();
        Priority priority = switch (selectPriority) {
            case "LOW" -> Priority.LOW;
            case "MEDIUM" -> Priority.MEDIUM;
            case "HIGH" -> Priority.HIGH;
            default -> throw new IllegalStateException("Unexpected value: " + selectPriority);
        };

        System.out.println("Enter estimated time");
        long estimatedTime = scanner.nextLong();

        try{
            Ticket newTicket = ticketService.create(
                    name,
                    description,
                    assigneeList,
                    reporter,
                    status,
                    priority,
                    estimatedTime);
        } catch (InvalidTicketNameException | UserNotFoundException | InvalidEstimatedTimeException e) {
            e.printStackTrace();
        }
    }

    public static void deleteTicket() throws IOException, TicketNotFoundException {
        deleteTicket("");
    }

    public static void deleteTicket(final String message)
            throws IOException, TicketNotFoundException {
        System.out.println(message);
        DisplayUtils.displayTickets();

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter ticket number");
        int ticketIndex = scanner.nextInt() - 1;
        if (ticketIndex >= ticketService.getAllTickets().size() || ticketIndex < 0) {
            deleteTicket("A ticket with this number does not exist. Enter the number again");
        }

        if (ticketService.getAllTickets().get(ticketIndex) != null) {
            String deleteID = ticketService.getAllTickets().get(ticketIndex).getTicketId();
            ticketService.delete(deleteID);
        } else {
            deleteTicket("Incorrect input");
        }
        optionSelectionScreen();
    }

    public static void editTicket() {
        editTicket("");
    }

    public static void editTicket(final String message) {
        final Scanner scanner = new Scanner(System.in);
        System.out.println("Select the ticket number you want to edit");
        DisplayUtils.displayTickets();
        int numberTicket = scanner.nextInt();

        if (numberTicket < 1 || numberTicket > ticketService.getAllTickets().size()){
            editTicket("Incorrect input");
        }
            Ticket ticket = ticketService.getAllTickets().get(numberTicket - 1);

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
            assigneeList = userService.findByName(tempNameLine); //определиться с тикетСервис
        }

        System.out.println("Enter new name of reporter, or skip the step:");
        String reporter = ticket.getReporter();
        if (!scanner.nextLine().isBlank()) {
            reporter = scanner.nextLine();
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
}
