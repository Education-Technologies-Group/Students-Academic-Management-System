package repositories;

import models.TicketModel;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.Scanner;

public class TicketRepository {
    private final String DB_PATH = "data/tickets.csv";
    private LinkedList<TicketModel> tickets;
    private boolean db_changed = false;

    public TicketRepository() throws IOException {
        loadFromCSV();
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            if (db_changed) saveToCSV();
        }));
    }

    public TicketModel getTicketByID(int id) {
        for (TicketModel ticket : tickets) {
            if (ticket.getId() == id) {
                return ticket;
            }
        }
        return null;
    }

    public LinkedList<TicketModel> getTicketsByOwnerID(int ownerId) {
        LinkedList<TicketModel> result = new LinkedList<>();
        for (TicketModel ticket : tickets) {
            if (ticket.getOwnerId() == ownerId) {
                result.add(ticket);
            }
        }
        return result;
    }

    public void addTicket(TicketModel ticket) {
        tickets.add(ticket);
        db_changed = true;
    }

    public void removeTicket(TicketModel ticket) {
        tickets.remove(ticket);
        db_changed = true;
    }

    private void loadFromCSV() throws IOException {
        tickets = new LinkedList<>();
        File file = new File(DB_PATH);
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            file.createNewFile();
            return;
        }

        Scanner sc = new Scanner(file);
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            String[] data = line.split(",", -1); // boş alanları da al
            TicketModel ticket = new TicketModel(
                    Integer.parseInt(data[0]),             // id
                    Integer.parseInt(data[1]),             // ownerId
                    data[2],                               // ownerRole
                    LocalDateTime.parse(data[3]),          // creation_date
                    data[4],                               // title
                    data[5],                               // description
                    data[6].isEmpty() ? null : LocalDateTime.parse(data[6]) // resolved_date
            );
            tickets.add(ticket);
        }
        sc.close();
    }

    private void saveToCSV() {
        System.out.println("Saving Tickets into Database");
        try (FileWriter writer = new FileWriter(DB_PATH)) {
            for (TicketModel ticket : tickets) {
                writer.write(ticket.getId() + ",");
                writer.write(ticket.getOwnerId() + ",");
                writer.write(ticket.getOwnerRole() + ",");
                writer.write(ticket.getCreation_date().toString() + ",");
                writer.write(ticket.getTitle() + ",");
                writer.write(ticket.getDescription() + ",");
                writer.write(ticket.getResolved_date() == null ? "" : ticket.getResolved_date().toString());
                writer.write("\n");
            }
        } catch (IOException e) {
            System.out.println("Error while saving tickets: " + e.getMessage());
        }
    }
}
