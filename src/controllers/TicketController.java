package controllers;

import models.TicketModel;
import models.user.LecturerModel;
import models.user.StudentModel;
import services.TicketService;

import java.time.LocalDateTime;
import java.util.LinkedList;

import static controllers.UserController.current_user;

public class TicketController {
    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }


    public String createTicket(String title, String description) {
        if (title.isEmpty() || description.isEmpty()) {
            return "Title or Description Could not be Empty";
        }
        int invalid_id = -1;
        TicketModel ticket = new TicketModel(
                invalid_id,
                current_user.getId(),
                current_user instanceof StudentModel ? "Student" : "Lecturer",
                current_user instanceof StudentModel ? ((StudentModel) current_user).getDepartment() : ((LecturerModel) current_user).getDepartment(),
                LocalDateTime.now(),
                title,
                description,
                null

        );
        if (ticketService.createNewTicket(ticket)) {
            return "Success";
        } else {
            return "Something went wrong...";
        }
    }

    public LinkedList<String> getTickets() {
        LinkedList<String> result = new LinkedList<>();
        LinkedList<TicketModel> tickets = ticketService.sendTickets(current_user);
        if (tickets == null) {
            return null;
        }
        for (TicketModel ticket : tickets) {
            result.add("=== Ticket Status ===\n" +
                    "Title       : " + ticket.getTitle() + "\n" +
                    "Description : " + ticket.getDescription() + "\n" +
                    "Created On  : " + ticket.getCreation_date() + "\n" +
                    "Status      : " +
                    (ticket.getResolved_date() == null ? "Open" : "Resolved on " + ticket.getResolved_date()));
        }
        return result;
    }

    public String markTicketSolved(int ticketID) {
        if (!ticketService.checkExistence(ticketID)){
            return "Invalid Ticket ID";
        }
        if (ticketService.markSolved(ticketID)){
            return "Success";
        }else {
            return "Something went wrong...";
        }
    }

}
