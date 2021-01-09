package com.cursor.bugtracker.service;

import com.cursor.bugtracker.dao.TicketDao;
import com.cursor.bugtracker.dao.TicketInMemoryDao;
import com.cursor.bugtracker.dao.UserDao;
import com.cursor.bugtracker.dao.UserInMemoryDao;
import com.cursor.bugtracker.dto.TicketDto;
import com.cursor.bugtracker.enums.Priority;
import com.cursor.bugtracker.enums.Status;
import com.cursor.bugtracker.exceptions.*;
import com.cursor.bugtracker.interfaces.Singleton;
import com.cursor.bugtracker.model.Ticket;
import com.cursor.bugtracker.model.User;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class TicketService implements Singleton {

    private static TicketService instance;
    private final TicketDao ticketDao = TicketInMemoryDao.getInstance();
    private final UserDao userDao = UserInMemoryDao.getInstance();

    private TicketService() {
    }

    public static TicketService getInstance() {
        if (instance == null)
            instance = new TicketService();
        return instance;
    }

    public Ticket create(
            final String name,
            final String description,
            final List<String> assigneeList,
            final String reporter,
            final Status status,
            final Priority priority,
            final long estimatedTime
    ) throws InvalidTicketNameException,
            InvalidEstimatedTimeException,
            UserNotFoundException {
        final String ticketId = generateId();

        final String validatedName = changeName(name);
        validateName(validatedName);

        validateAssigneeList(assigneeList);
        validateTime(estimatedTime);

        final Ticket ticket = new Ticket(
                ticketId,
                validatedName,
                description,
                assigneeList,
                reporter,
                status,
                priority,
                estimatedTime,
                0L,
                LocalDate.now()
        );
        ticket.setTotalEstimatedTime();

        return ticketDao.save(ticket);
    }

    private String generateId() {
        return UUID.randomUUID().toString();
    }

    private String changeName(final String name) {
        return name.trim();
    }

    private void validateName(final String name) throws InvalidTicketNameException {
        if (name.length() < 5) {
            throw new InvalidTicketNameException("The name of the ticket is too short.");
        } else if (name.length() > 101) {
            throw new InvalidTicketNameException("The name of the ticket is too long.");
        }

        String[] words = name.split(" ");

        int wordsCounter = words.length;
        int letterCounter;
        char letter;

        for (String word : words) {
            letterCounter = 0;

            for (int i = 0; i < word.length(); i++) {
                letter = word.charAt(i);

                if (Character.isLetter(letter)) {
                    letterCounter++;
                }

                if (letterCounter == 2) {
                    break;
                }
            }

            if (letterCounter < 2) {
                wordsCounter--;
            }
        }

        if (wordsCounter < 2) {
            throw new InvalidTicketNameException("The name of the ticket was entered incorrectly.");
        }
    }

    private void validateAssigneeList(final List<String> assigneeList) throws UserNotFoundException {
        for (String userId : assigneeList) {
            if (!checkIfExistUser(userId)) {
                throw new UserNotFoundException("User with id \"" + userId + "\" not found.");
            }
        }
    }

    private boolean checkIfExistUser(final String userId) {
        User user = userDao.getUserById(userId);
        return user != null;
    }

    private void validateTime(final long estimatedTime) throws InvalidEstimatedTimeException {
        if (estimatedTime < 20)
            throw new InvalidEstimatedTimeException("Estimated time \"" + estimatedTime + "\" of the ticket was entered incorrectly.");
    }

    public Ticket findById(final String ticketId) throws TicketNotFoundException {
        Ticket ticket = ticketDao.getById(ticketId);
        if (ticket == null)
            throw new TicketNotFoundException(ticketId);
        return ticket;
    }

    /**
     * @return all tickets sorted by
     * 1. priority and
     * 2. status
     */
    public List<TicketDto> getAllTickets() {
        return ticketDao.getAllTickets().stream()
                .map(ticket -> new TicketDto(
                                ticket,
                                userDao.getUsernamesByIds(ticket.getAssigneeList()),
                                userDao.getUserById(ticket.getReporter()).getName()
                        )
                ).collect(Collectors.toList());
    }

    public void delete(final String ticketId) throws TicketNotFoundException {
        if (ticketDao.removeById(ticketId) == null) {
            throw new TicketNotFoundException("The ticket with id \"" + ticketId + "\" was not found.");
        }
    }

    public Ticket edit(
            final String ticketId,
            final String name,
            final String description,
            final List<String> assigneeList,
            final Status status,
            final Priority priority
    ) throws InvalidTicketNameException,
            TicketNotFoundException,
            UserNotFoundException {
        Ticket ticket = findById(ticketId);

        if (name != null) {
            final String validatedName = changeName(name);
            validateName(validatedName);
            ticket.setName(validatedName);
        }

        if (description != null) {
            ticket.setDescription(description);
        }

        if (assigneeList != null) {
            validateAssigneeList(assigneeList);
            ticket.setAssigneeList(assigneeList);
        }

        if (status != null) {
            ticket.setStatus(status);
            if (status.equals(Status.IN_PROGRESS)) {
                ticket.setStartDate(LocalDateTime.now());
            } else if (status.equals(Status.DONE)) {
                ticket.setSpentTime(calculateSpentTime(ticket));
            }
        }

        if (priority != null) {
            ticket.setPriority(priority);
        }

        return ticket;
    }

    public long calculateSpentTime(Ticket ticket) {
        long startDate = ticket.getStartDate().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        long currentDate = LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();

        return (startDate - currentDate) / 60_000 + 1;
    }
}
