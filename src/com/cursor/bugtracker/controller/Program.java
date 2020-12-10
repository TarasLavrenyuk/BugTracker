package com.cursor.bugtracker.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Program {

    public static void main(String[] args) throws IOException {
        // login

        // show all tickets
        // remove ticket


        System.out.println(
                "1. Show all tickets\n" +
                        "2. Create new ticket \n" +
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
        } else if (name.equals("2")) {
            clearScreen();
            System.out.println("create new ticket stuff");
        }
    }

    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}
