package models;

import java.time.LocalDateTime;

public class LiveSessionModel {
    private int id;
    private int lecturerId;
    private String title;
    private String description;
    private LocalDateTime creation_date;
    private LocalDateTime scheduled_date;
    private String video;

    public LiveSessionModel(int id, int lecturerId, String title, String description,
                            LocalDateTime creation_date, LocalDateTime scheduled_date, String video) {
        this.id = id;
        this.lecturerId = lecturerId;
        this.title = title;
        this.description = description;
        this.creation_date = creation_date;
        this.scheduled_date = scheduled_date;
        this.video = video;
    }

    public int getId() {
        return id;
    }

    public int getLecturerId() {
        return lecturerId;
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

    public String getVideo() {
        return video;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setLecturerId(int lecturerId) {
        this.lecturerId = lecturerId;
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

    public void setVideo(String video) {
        this.video = video;
    }
}
