package services;

import models.TicketModel;
import repositories.TicketRepository;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.Scanner;

public class TicketService {
    private final TicketRepository ticketRepo;
    private final Scanner sc = new Scanner(System.in);

    public TicketService(TicketRepository repo) {
        this.ticketRepo = repo;
    }

    public void markSolved() {
        System.out.print("Enter Ticket ID to mark as solved: ");
        int id = sc.nextInt();
        TicketModel ticket = ticketRepo.getTicketByID(id);

        if (ticket != null) {
            ticket.setResolved_date(LocalDateTime.now());
            System.out.println("Ticket marked as solved.");
        } else {
            System.out.println("Ticket not found.");
        }
    }

    public void callOffice() {
        System.out.print("Enter Ticket ID to call office about: ");
        int id = sc.nextInt();
        TicketModel ticket = ticketRepo.getTicketByID(id);

        if (ticket != null) {
            System.out.println("Calling support office for ticket: " + ticket.getTitle());
            // Simulate a call (you can later integrate phone/email action if needed)
        } else {
            System.out.println("Ticket not found.");
        }
    }

    public void displayStatus() {
        System.out.print("Enter Ticket ID to view status: ");
        int id = sc.nextInt();
        TicketModel ticket = ticketRepo.getTicketByID(id);

        if (ticket != null) {
            System.out.println("=== Ticket Status ===");
            System.out.println("Title       : " + ticket.getTitle());
            System.out.println("Description : " + ticket.getDescription());
            System.out.println("Created On  : " + ticket.getCreation_date());
            System.out.println("Status      : " +
                    (ticket.getResolved_date() == null ? "Open" : "Resolved on " + ticket.getResolved_date()));
        } else {
            System.out.println("Ticket not found.");
        }
    }

    public void createNewTicket() {
        try {
            System.out.print("Enter Owner ID: ");
            int ownerId = sc.nextInt();
            sc.nextLine(); // Clear newline

            System.out.print("Enter Owner Role (e.g., student, lecturer): ");
            String role = sc.nextLine();

            System.out.print("Enter Ticket Title: ");
            String title = sc.nextLine();

            System.out.print("Enter Ticket Description: ");
            String description = sc.nextLine();

            LocalDateTime creationDate = LocalDateTime.now();

            TicketModel ticket = new TicketModel(
                    generateTicketID(),
                    ownerId,
                    role,
                    creationDate,
                    title,
                    description,
                    null
            );

            ticketRepo.addTicket(ticket);
            System.out.println("Ticket created successfully.");

        } catch (Exception e) {
            System.out.println("Error creating ticket: " + e.getMessage());
        }
    }

    public void listTicketsByOwner() {
        System.out.print("Enter your Owner ID: ");
        int ownerId = sc.nextInt();
        LinkedList<TicketModel> tickets = ticketRepo.getTicketsByOwnerID(ownerId);

        if (tickets.isEmpty()) {
            System.out.println("No tickets found for this user.");
            return;
        }

        for (TicketModel t : tickets) {
            System.out.println("ID: " + t.getId() + " | Title: " + t.getTitle() +
                    " | Status: " + (t.getResolved_date() == null ? "Open" : "Resolved"));
        }
    }

    private int generateTicketID() {
        int maxId = 0;
        for (TicketModel t : ticketRepo.getTicketsByOwnerID(0)) {
            if (t.getId() > maxId) maxId = t.getId();
        }
        return maxId + 1;
    }
}
