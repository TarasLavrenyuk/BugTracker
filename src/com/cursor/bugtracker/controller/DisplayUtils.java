package com.cursor.bugtracker.controller;

import java.io.IOException;

public class DisplayUtils {

    public static void clearScreen() {
        //Clears Screen in java
        try {
            if (System.getProperty("os.name").contains("Windows"))
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            else
                Runtime.getRuntime().exec("clear");
        } catch (IOException | InterruptedException ex) {
        }
    }

    // displayTicket(ticket)

    // displayTickets(ticket)

    //
}
