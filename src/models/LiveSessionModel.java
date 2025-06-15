package models;

import java.time.LocalDateTime;
import java.util.LinkedList;

public class LiveSessionModel {
    private int id;
    private int hostID;
    private String title;
    private String description;
    private LocalDateTime creation_date;
    private LocalDateTime scheduled_date;
    private String record_path;
    // Foreign Keys
    private LinkedList<Integer> invited_participants;
    private LinkedList<Integer> active_participants;


    public LiveSessionModel(int id, int hostID, String title, String description,
                            LocalDateTime creation_date, LocalDateTime scheduled_date, String record_path,
                            LinkedList<Integer> invited_participants, LinkedList<Integer> active_participants) {
        this.id = id;
        this.hostID = hostID;
        this.title = title;
        this.description = description;
        this.creation_date = creation_date;
        this.scheduled_date = scheduled_date;
        this.record_path = record_path;
        this.invited_participants = invited_participants;
        this.active_participants = active_participants;
    }

    public int getId() {
        return id;
    }

    public int getHostID() {
        return hostID;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getCreation_date() {
        return creation_date;
    }

    public LocalDateTime getScheduled_date() {
        return scheduled_date;
    }

    public String getRecord_path() {
        return record_path;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setHostID(int hostID) {
        this.hostID = hostID;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCreation_date(LocalDateTime creation_date) {
        this.creation_date = creation_date;
    }

    public void setScheduled_date(LocalDateTime scheduled_date) {
        this.scheduled_date = scheduled_date;
    }

    public void setRecord_path(String record_path) {
        this.record_path = record_path;
    }

    public LinkedList<Integer> getActiveParticipants() {
        return active_participants;
    }

    public void setActiveParticipants(LinkedList<Integer> active_participants) {
        this.active_participants = active_participants;
    }

    public LinkedList<Integer> getInvitedParticipants() {
        return invited_participants;
    }

    public void setInvitedParticipants(LinkedList<Integer> invited_participants) {
        this.invited_participants = invited_participants;
    }
}
