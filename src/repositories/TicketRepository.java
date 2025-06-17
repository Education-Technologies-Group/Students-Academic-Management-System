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
    private int last_pk;

    public TicketRepository() throws IOException {
        loadFromCSV();
        if (!tickets.isEmpty()) {
            last_pk = Math.max(last_pk, tickets.getLast().getId());
        }
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

    public LinkedList<TicketModel> getTicketsByDepartment(String department) {
        LinkedList<TicketModel> result = new LinkedList<>();
        for (TicketModel ticket : tickets) {
            if (ticket.getDepartment().equals(department)) {
                result.add(ticket);
            }
        }
        return result;
    }

    public boolean addTicket(TicketModel ticket) {
        ticket.setId(last_pk);
        tickets.add(ticket);
        db_changed = true;
        return true;
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
            String[] data = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
            for (int i = 0; i < data.length; i++) {
                data[i] = data[i].replaceAll("^\"|\"$", ""); // Clean Quotes
            }
            TicketModel ticket = new TicketModel(
                    Integer.parseInt(data[0]),             // id
                    Integer.parseInt(data[1]),             // ownerId
                    data[2],                               // ownerRole
                    data[3],                               // Department
                    LocalDateTime.parse(data[4]),          // creation_date
                    data[5],                               // title
                    data[6],                               // description
                    data[7].isEmpty() ? null : LocalDateTime.parse(data[6]) // resolved_date
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
                writer.write(ticket.getDepartment() + ",");
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
