package models;

import java.time.LocalDateTime;
import java.util.List;

public class AnnouncementModel {
    private int id;
    private int sender_id;
    private String lecture_code; // For Sending to a Section
    private String department; // For Sending to a Department
    private String title;
    private String description;
    private List<String> attachedFiles;
    private LocalDateTime expirationDate;

    public AnnouncementModel(int id, int sender_id, String lecture, String department, String title, String description,
                             List<String> attachedFiles, LocalDateTime expirationDate) {
        this.id = id;
        this.sender_id = sender_id;
        this.lecture_code = lecture;
        this.department = department;
        this.title = title;
        this.description = description;
        this.expirationDate = expirationDate;
        this.attachedFiles = attachedFiles;
    }

    public int getID() {
        return id;
    }

    public void setID(int id) {
        this.id = id;
    }

    public int getSenderId() {
        return sender_id;
    }

    public void setSenderId(int sender_id) {
        this.sender_id = sender_id;
    }

    public String getLectureCode() {
        return lecture_code;
    }

    public void setLectureCode(String lecture_code) {
        this.lecture_code = lecture_code;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String geTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getAttachedFiles() {
        return attachedFiles;
    }

    public void setAttachedFiles(List<String> attachedFiles) {
        this.attachedFiles = attachedFiles;
    }

    public LocalDateTime getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDateTime expirationDate) {
        this.expirationDate = expirationDate;
    }
}
