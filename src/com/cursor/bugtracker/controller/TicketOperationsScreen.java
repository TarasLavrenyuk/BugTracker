package com.cursor.bugtracker.controller;

import com.cursor.bugtracker.enums.Priority;
import com.cursor.bugtracker.enums.Status;
import com.cursor.bugtracker.exceptions.InvalidEstimatedTimeException;
import com.cursor.bugtracker.exceptions.InvalidTicketNameException;
import com.cursor.bugtracker.exceptions.UserNotFoundException;
import com.cursor.bugtracker.exceptions.WrongUsernameException;
import com.cursor.bugtracker.model.Ticket;
import com.cursor.bugtracker.model.User;
import com.cursor.bugtracker.service.TicketService;
import com.cursor.bugtracker.service.UserService;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.function.Predicate;

public class TicketOperationsScreen {

    private static UserService userService = UserService.getInstance();
    private static TicketService ticketService = TicketService.getInstance();

    public static void createNewTicket() {
        System.out.println("New ticket creation");

        String name = readTicketName();
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
        }
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

    private static Priority readPriority(String message) {
        System.out.println(message);
        System.out.println("Enter the priority of your ticket: " + Arrays.asList(Priority.values()));
        final Scanner scanner = new Scanner(System.in);
        String priorityString = scanner.nextLine().toUpperCase();
        try {
            return Priority.valueOf(priorityString);
        } catch (IllegalStateException exception) {
            readStatus("Unexpected value: " + priorityString);
        }
        return null;
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
        List<String> usernames = Arrays.asList(scanner.nextLine().split(" "));
        try {
            userService.checkIfUsersExist(usernames);
        } catch (WrongUsernameException e) {
            readAssignees(e.getMessage());
        }
        // get userids of usernames list (please no O(n^2))
        // create map(username -> user) from {users}
        // get userids from the map
        List<String> userIds = new LinkedList<>();
        return userIds;
    }

    private static String readTicketName() {
        final Scanner scanner = new Scanner(System.in);
        System.out.println("Enter name of the ticket: ");
        return scanner.nextLine();
    }

    private static String readDescription() {
        final Scanner scanner = new Scanner(System.in);
        System.out.println("Enter description of the ticket: ");
        return scanner.nextLine();
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
