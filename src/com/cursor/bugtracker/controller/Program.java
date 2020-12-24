package com.cursor.bugtracker.controller;

import com.cursor.bugtracker.exceptions.BadCredentialsException;
import com.cursor.bugtracker.model.User;
import com.cursor.bugtracker.service.TicketService;
import com.cursor.bugtracker.service.UserService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Program {

    private static User currentUser = null;

    private static UserService userService = UserService.getInstance();
    private static TicketService ticketService = TicketService.getInstance();

    public static void main(String[] args) throws IOException {
        showLoginScreen();
    }

    private static void showLoginScreen() {
        DisplayUtils.clearScreen();
        System.out.println("1. Sign in" +
                System.lineSeparator() +
                "2. Sign up" +
                System.lineSeparator() +
                "Choose option..."

        );
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            try {
                String option = reader.readLine();
                if (option.equals("1")) {
                    showSignInScreen();
                } else if (option.equals("2")) {
                    showSignUpScreen();
                }
            } catch (IOException e) {
                showLoginScreen();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void showSignInScreen() throws IOException {
        DisplayUtils.clearScreen();
        System.out.println("Type login: ");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String username = reader.readLine();

        System.out.println("Type password: ");
        String password = reader.readLine();


        userService.login(username, password);
        try {login
            currentUser = userService.login(username, password);
            System.out.println("Your initialization is successful");
        } catch (BadCredentialsException e) {
            System.out.println("\nYour username or login is incorrect.\nPlease try again.\n");
            showSignInScreen();
        }
    }


    private static void showSignUpScreen() throws IOException {
        DisplayUtils.clearScreen();
        System.out.println("Create your Login: ");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String username = reader.readLine();

        System.out.println("Create your Password:");
        String password = reader.readLine();

        userService.login(username, password);
        try {
            currentUser = userService.login(username, password);
        } catch (BadCredentialsException e) {
            System.out.println("\nYour username or login is incorrect.\nPlease try again.\n");
            showSignUpScreen();
        }
    }

    private static void logOut() {
        System.out.println("Good bye");
         currentUser = null;
    }
}

