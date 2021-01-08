package com.cursor.bugtracker.controller;

import com.cursor.bugtracker.enums.Priority;
import com.cursor.bugtracker.enums.Status;
import com.cursor.bugtracker.exceptions.*;
import com.cursor.bugtracker.model.Ticket;
import com.cursor.bugtracker.model.User;
import com.cursor.bugtracker.service.TicketService;
import com.cursor.bugtracker.service.UserService;

import java.util.*;
import java.util.stream.Collectors;

import static com.cursor.bugtracker.controller.MainMenu.displayTickets;

public class TicketOperationsScreen {

    private static UserService userService = UserService.getInstance();
    private static TicketService ticketService = TicketService.getInstance();

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

        String editedTicketName = readEditedTicketName(ticket.getName());
        String description = readEditedDescription(ticket.getDescription());
        List<String> assigneeList = readEditedAssigneeList(ticket.getAssigneeList());
        Status status = readEditedStatus(ticket.getStatus());
        Priority priority = readEditedPriority(ticket.getPriority());

        try {
            ticketService.edit(
                    ticket.getTicketId(),
                    editedTicketName,
                    description,
                    assigneeList,
                    status,
                    priority
            );
        } catch (InvalidTicketNameException | UserNotFoundException | TicketNotFoundException e) {
            e.printStackTrace();
        } finally {
            MainMenu.showTicketMenu("You have successfully edited the ticket");
        }
    }

    public static void createNewTicket() {
        System.out.println("New ticket creation");

        String name = readTicketName("");
        String description = readDescription();
        List<String> assignees = readAssignees("");
        Status status = readStatus("");
        Priority priority = readPriority("");
        long estimatedTime = readEstimatedTime("");

        try {
            ticketService.create(
                    name,
                    description,
                    assignees,
                    Session.currentUser.getName(),
                    status,
                    priority,
                    estimatedTime);
        } catch (InvalidTicketNameException | UserNotFoundException | InvalidEstimatedTimeException e) {
            e.printStackTrace();
        } finally {
            MainMenu.showTicketMenu("You have successfully created a new ticket");
        }
    }

    private static String readTicketName(final String message) {
        final Scanner scanner = new Scanner(System.in);
        System.out.println("Enter name of the ticket: ");
        String readName = scanner.nextLine();
        if (readName.isBlank()) {
            return readTicketName("The string cannot be less than 5 characters");
        }
        return readName;
    }

    private static String readEditedTicketName(String defaultTicketName) {
        final Scanner scanner = new Scanner(System.in);
        System.out.println("Enter new name of ticket, or skip the step: ");
        String name = defaultTicketName;
        if (!scanner.nextLine().isBlank()) {
            name = scanner.nextLine();
        }
        return name;
    }

    private static String readDescription() {
        final Scanner scanner = new Scanner(System.in);
        System.out.println("Enter description of the ticket: ");
        return scanner.nextLine();
    }

    private static String readEditedDescription(String defaultTicketDescription) {
        final Scanner scanner = new Scanner(System.in);
        System.out.println("Enter new description, or skip the step: ");
        String description = defaultTicketDescription;
        if (!scanner.nextLine().isBlank()) {
            description = scanner.nextLine();
        }
        return description;
    }

    /**
     * @return userids of assignees
     */
    private static List<String> readAssignees(String message) {
        System.out.println(message);
        System.out.println
                ("Enter separated by space the names of registered users in the list of assignee");
        List<User> users = userService.getAllUsers();
        DisplayUtils.displayUsernames(users);
        final Scanner scanner = new Scanner(System.in);
        List<String> assigneesUsernames = Arrays.asList(scanner.nextLine().split(" "));
        try {
            userService.checkIfUsersExist(assigneesUsernames);
        } catch (WrongUsernameException e) {
            readAssignees(e.getMessage());
        }
        Map<String, String> usernamesAndIds = users
                .stream()
                .collect(Collectors.toMap(User::getName, User::getUserId));

        List<String> assigneesIds = new LinkedList<>();
        assigneesUsernames.forEach(
                username -> assigneesIds.add(usernamesAndIds.get(username))
        );
        return assigneesIds;
    }

    private static List<String> readEditedAssigneeList(final List<String> defaultAssigneeList) {
        final Scanner scanner = new Scanner(System.in);
        System.out.println
                ("Enter separated by space new list the names of registered users in the list of assignee" +
                        System.lineSeparator() + ", or skip the step: ");
        List<String> assigneeList = defaultAssigneeList;
        if (!scanner.nextLine().isBlank()) {
            List<User> users = userService.getAllUsers();
            DisplayUtils.displayUsernames(users);
            List<String> assigneesUsernames = Arrays.asList(scanner.nextLine().split(" "));
            try {
                userService.checkIfUsersExist(assigneesUsernames);
            } catch (WrongUsernameException e) {
                readAssignees(e.getMessage());
            }
            Map<String, String> usernamesAndIds = users
                    .stream()
                    .collect(Collectors.toMap(User::getName, User::getUserId));

            List<String> assigneesIds = new LinkedList<>();
            assigneesUsernames.forEach(
                    username -> assigneesIds.add(usernamesAndIds.get(username))
            );
            return assigneesIds;
        }
        return assigneeList;
    }

    private static Status readStatus(String message) {
        System.out.println(message);
        System.out.println("Enter the status of your ticket: " + Arrays.asList(Status.values()));
        final Scanner scanner = new Scanner(System.in);
        String statusString = scanner.nextLine().toUpperCase();
        try {
            return Status.valueOf(statusString);
        } catch (IllegalStateException exception) {
            readStatus("Unexpected value: " + statusString);
        }
        return null;
    }

    private static Status readEditedStatus(final Status defaultStatus) {
        final Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the status of your ticket: " + Arrays.asList(Status.values()) +
                ", or skip the step:");
        Status status = defaultStatus;
        if (!scanner.nextLine().isBlank()) {
            String statusString = scanner.nextLine().toUpperCase();
            try {
                return Status.valueOf(statusString);
            } catch (IllegalStateException exception) {
                readStatus("Unexpected value: " + statusString);
            }
        }
        return status;
    }

    private static Priority readPriority(String message) {
        System.out.println(message);
        System.out.println("Enter the priority of your ticket: " + Arrays.asList(Priority.values()));
        final Scanner scanner = new Scanner(System.in);
        String priorityString = scanner.nextLine().toUpperCase();
        try {
            return Priority.valueOf(priorityString);
        } catch (IllegalStateException exception) {
            readPriority("Unexpected value: " + priorityString);
        }
        return null;
    }

    private static Priority readEditedPriority(final Priority defaultPriority) {
        final Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the priority of your ticket: "
                + Arrays.asList(Priority.values()) + ", or skip the step:");
        Priority priority = defaultPriority;
        if (!scanner.nextLine().isBlank()) {
            String priorityString = scanner.nextLine().toUpperCase();
            try {
                return Priority.valueOf(priorityString);
            } catch (IllegalStateException exception) {
                readPriority("Unexpected value: " + priorityString);
            }
        }
        return priority;
    }

    private static long readEstimatedTime(String message) {
        System.out.println("Enter estimated time in minutes: ");
        final Scanner scanner = new Scanner(System.in);
        try {
            return scanner.nextLong();
        } catch (IllegalStateException exception) {
            readEstimatedTime("Unexpected value. Please try again.");
        }
        return 0;
    }
}
