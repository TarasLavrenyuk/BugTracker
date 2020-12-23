package com.cursor.bugtracker.controller;

import com.cursor.bugtracker.exceptions.BadCredentialsException;
import com.cursor.bugtracker.service.TicketService;
import com.cursor.bugtracker.service.UserService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

// every screen (window, ui block) should be separate function

public class Program {

    private static UserService userService ;//= UserService.getInstance();
    private static TicketService ticketService ;//= TicketService.getInstance();

    public static void main(String[] args) throws IOException {
        // login

        // show all tickets
        // remove ticket

        System.out.println(
                "1. Sign in\n" +
                        "2. Sign up\n" +
                        "\n" +
                        "Choose option..."
        );

        BufferedReader reader =
                new BufferedReader(new InputStreamReader(System.in));

        // Reading data using readLine
        String name = reader.readLine();

        if (name.equals("1")) {
            clearScreen();
            System.out.println("display all tickets");
            showSignInScreen();
        } else if (name.equals("2")) {
            clearScreen();
            System.out.println("create new ticket stuff");
        }
    }

    private static void showSignInScreen() throws IOException {
        System.out.println("Type login: ");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String username = reader.readLine();

        System.out.println("Type password: ");
        String password = reader.readLine();

        userService.login(username, password);
    }

    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

}
