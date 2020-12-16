package com.cursor.bugtracker.service;

import com.cursor.bugtracker.dao.TicketInMemoryDao;
import com.cursor.bugtracker.enums.Priority;
import com.cursor.bugtracker.enums.Status;
import com.cursor.bugtracker.exceptions.*;
import com.cursor.bugtracker.model.Ticket;
import com.cursor.bugtracker.model.User;

import java.util.List;
import java.util.UUID;

public class TicketService {

    private static TicketService instance;
    private final TicketInMemoryDao ticketDao = TicketInMemoryDao.getInstance();
    private final UserService userService = UserService.getInstance();

    private TicketService() { }

    public static TicketService getInstance() {
        if(instance == null)
            instance = new TicketService();
        return instance;
    }

    public Ticket create(
            String name,
            String description,
            List<String> assigneeList,
            String reporter,
            Status status,
            Priority priority,
            long estimatedTime
    ) throws InvalidTicketNameException,
             InvalidEstimatedTimeException {

        final String ticketId = generateId();

        final String validatedName = correctName(name);
        validateName(validatedName);

        validateAssigneeList(assigneeList);
        validateUser(reporter);
        validateTime(estimatedTime);

        Ticket ticket = new Ticket(
                ticketId,
                validatedName,
                description,
                assigneeList,
                reporter,
                status,
                priority,
                estimatedTime,
                0L
        );

        ticket = ticketDao.save(ticket);

        return ticket;
    }

    public void test(String name) throws InvalidTicketNameException {
        name = correctName(name);
        validateName(name);
    }

    private String generateId(){
        return UUID.randomUUID().toString();
    }

    private String correctName(String name){
        name = name.trim();

        return name;
    }

    private void validateName(String name) throws InvalidTicketNameException {

        if(name.length() < 5){
            throw new InvalidTicketNameException("The name of the ticket is too short.");
        }
        else if(name.length() > 101){
            throw new InvalidTicketNameException("The name of the ticket is too long.");
        }

        String[] words = name.split(" ");

        int wordsCounter = words.length;
        int letterCounter;
        char letter;

        for(String word : words){
            letterCounter = 0;

            for (int i = 0; i < word.length(); i++) {
                letter = word.charAt(i);

                if (Character.isLetter(letter)) {
                    letterCounter ++;
                }

                if(letterCounter == 2){
                    break;
                }
            }

            if(letterCounter < 2){
                wordsCounter--;
            }
        }

        if(wordsCounter < 2){
            throw new InvalidTicketNameException("The name of the ticket was entered incorrectly.");
        }
    }

    private void validateAssigneeList(List<String> assigneeList) {
        for(String userId : assigneeList){
            validateUser(userId);
        }
    }

    private void validateUser(String userId){
        User user = userService.getById(userId);
    }

    private void validateTime(long estimatedTime) throws InvalidEstimatedTimeException {
        if(estimatedTime < 60)
            throw new InvalidEstimatedTimeException("Estimated time \"" + estimatedTime + "\" of the ticket was entered incorrectly.");
    }

    public Ticket findById(String ticketId) throws TicketNotFoundException {
        Ticket ticket = ticketDao.getById(ticketId);
        if(ticket == null)
            throw new TicketNotFoundException(ticketId);
        return ticket;
    }

    public void delete(String ticketId) throws TicketNotFoundException {
        Ticket ticket = findById(ticketId);
        if(ticket == null){
            throw new TicketNotFoundException("The ticket with id \"" + ticketId + "\" was not found.");
        }
        ticketDao.removeById(ticketId);
    }

    public Ticket edit(
            String userId,
            String ticketId,
            String name,
            String description,
            List<String> assigneeList,
            Status status,
            Priority priority,
            long spentTime
    ) throws InvalidTicketNameException,
            InvalidEstimatedTimeException,
            TicketNotFoundException,
            NoAccessToEditException {

        validateUser(userId);

        Ticket ticket = getInstance().findById(ticketId);

        validateRights(userId, ticket);

        final String validatedName = correctName(name);
        validateName(validatedName);

        validateAssigneeList(assigneeList);
        validateTime(spentTime);

        ticket.setName(validatedName);
        ticket.setDescription(description);
        ticket.setAssigneeList(assigneeList);
        ticket.setStatus(status);
        ticket.setPriority(priority);

        return ticket;
    }

    private void validateRights(String userId, Ticket ticket) throws NoAccessToEditException {
        String reporterId = ticket.getReporter();
        List<String> fullAssigneeList = ticket.getAssigneeList();

        fullAssigneeList.add(reporterId);

        boolean hasRights = false;

        for (String user : fullAssigneeList){
            if(user.equals(userId)){
                hasRights = true;
                break;
            }
        }

        if(!hasRights){
            throw new NoAccessToEditException("You don't have permission to edit this ticket.");
        }
    }
}
