package models;

import java.time.LocalDateTime;

public class TicketModel {
    private int id;
    private int ownerId;         // Ticket’ı açan kullanıcının ID’si
    private String ownerRole;    // "student", "lecturer", "affairs"
    private LocalDateTime creation_date;
    private String title;
    private String description;
    private LocalDateTime resolved_date;

    public TicketModel(int id, int ownerId, String ownerRole, LocalDateTime creation_date,
                       String title, String description, LocalDateTime resolved_date) {
        this.id = id;
        this.ownerId = ownerId;
        this.ownerRole = ownerRole;
        this.creation_date = creation_date;
        this.title = title;
        this.description = description;
        this.resolved_date = resolved_date;
    }

    // Getters
    public int getId() { return id; }
    public int getOwnerId() { return ownerId; }
    public String getOwnerRole() { return ownerRole; }
    public LocalDateTime getCreation_date() { return creation_date; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public LocalDateTime getResolved_date() { return resolved_date; }

    // Setters
    public void setId(int id) { this.id = id; }
    public void setOwnerId(int ownerId) { this.ownerId = ownerId; }
    public void setOwnerRole(String ownerRole) { this.ownerRole = ownerRole; }
    public void setCreation_date(LocalDateTime creation_date) { this.creation_date = creation_date; }
    public void setTitle(String title) { this.title = title; }
    public void setDescription(String description) { this.description = description; }
    public void setResolved_date(LocalDateTime resolved_date) { this.resolved_date = resolved_date; }
}
