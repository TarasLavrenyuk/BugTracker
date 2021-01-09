package com.cursor.bugtracker.dto;

import com.cursor.bugtracker.enums.Priority;
import com.cursor.bugtracker.enums.Status;
import com.cursor.bugtracker.model.Ticket;
import com.cursor.bugtracker.model.User;

import java.time.LocalDate;
import java.util.List;

public class TicketDto extends Ticket {

    private List<String> assigneesUsernames;
    private String reporterUsername;

    public TicketDto(String ticketId, String name, String description, List<String> assigneeList, String reporter, Status status, Priority priority, long estimatedTime, long spentTime, LocalDate creationDate) {
        super(ticketId, name, description, assigneeList, reporter, status, priority, estimatedTime, spentTime, creationDate);
    }

    public TicketDto(Ticket ticket, List<String> assigneesUsernames, String reporterUsername) {
        super(ticket.getTicketId(), ticket.getName(), ticket.getDescription(), ticket.getAssigneeList(), ticket.getReporter(), ticket.getStatus(), ticket.getPriority(), ticket.getEstimatedTime(), ticket.getSpentTime(), ticket.getCreationDate());
        this.assigneesUsernames = assigneesUsernames;
        this.reporterUsername = reporterUsername;
    }

    public List<String> getAssigneesUsernames() {
        return assigneesUsernames;
    }

    public String getReporterUsername() {
        return reporterUsername;
    }
}
