package com.cursor.bugtracker.controller;

import com.cursor.bugtracker.model.Ticket;
import com.cursor.bugtracker.model.User;
import com.cursor.bugtracker.service.TicketService;
import com.cursor.bugtracker.service.UserService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

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

       try( BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            String option = reader.readLine();
            if (option.equals("1")) {
                showAllTickets(" ");
            }else if (option.equals("2")){
                createNewTicket();
            }else if (option.equals("3")){
                logOut();
            }
        }catch ( IOException E ){
displayMainMenu("WRONG INPUT");
       }
    }


    public static void showAllTickets(final String message) {

        List<Ticket> allTickets = ticketService.getAllTickets();
    }

    public static  void createNewTicket(){ }

}
