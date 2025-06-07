package models;

import java.time.LocalDateTime;

public class TicketModel {
    private LocalDateTime creation_date;
    private String title;
    private String description;
    private LocalDateTime resolved_date;

    public TicketModel(LocalDateTime creation_date, String title, String description, LocalDateTime resolved_date) {
        this.creation_date = creation_date;
        this.title = title;
        this.description = description;
        this.resolved_date = resolved_date;
    }

    public LocalDateTime getCreation_date() {
        return creation_date;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getResolved_date() {
        return resolved_date;
    }

    // Setters
    public void setCreation_date(LocalDateTime creation_date) {
        this.creation_date = creation_date;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setResolved_date(LocalDateTime resolved_date) {
        this.resolved_date = resolved_date;
    }
}
