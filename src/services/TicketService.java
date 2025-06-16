package services;

import models.TicketModel;
import models.user.LecturerModel;
import models.user.StudentAffairsModel;
import models.user.StudentModel;
import models.user.UserModel;
import repositories.TicketRepository;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.Scanner;

public class TicketService {
    private final TicketRepository ticketRepository;
    private final Scanner sc = new Scanner(System.in);

    public TicketService(TicketRepository repo) {
        this.ticketRepository = repo;
    }

    public boolean markSolved(int ticket_id) {
        TicketModel ticket = ticketRepository.getTicketByID(ticket_id);
        ticket.setResolved_date(LocalDateTime.now());
        return true;
    }
    public LinkedList<TicketModel> sendTickets(UserModel user) {
        if (user instanceof StudentModel || user instanceof LecturerModel) {
            return ticketRepository.getTicketsByOwnerID(user.getId());
        }
        if (user instanceof StudentAffairsModel) {
            return ticketRepository.getTicketsByDepartment(((StudentAffairsModel) user).getDepartment());
        }
        return null;
    }

    public void callOffice() {
        System.out.print("Enter Ticket ID to call office about: ");
        int id = sc.nextInt();
        TicketModel ticket = ticketRepository.getTicketByID(id);

        if (ticket != null) {
            System.out.println("Calling support office for ticket: " + ticket.getTitle());
            // Simulate a call (you can later integrate phone/email action if needed)
        } else {
            System.out.println("Ticket not found.");
        }
    }

    public boolean createNewTicket(TicketModel ticket) {
            return ticketRepository.addTicket(ticket);
    }

    public void listTicketsByOwner(int ownerID) {
        LinkedList<TicketModel> tickets = ticketRepository.getTicketsByOwnerID(ownerID);

    }

    public boolean checkExistence(int ticketID) {
        return ticketRepository.getTicketByID(ticketID) != null;
    }
}
