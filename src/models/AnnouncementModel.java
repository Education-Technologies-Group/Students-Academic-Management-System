package models;

import java.util.List;

public class AnnouncementModel {
    private String announcement_title;
    private String announcement_description;
    private List<String> attachedFiles;
    private String expirationDate;

    public AnnouncementModel(String announcement_description, String announcement_title,String expirationDate) {
        this.announcement_description = announcement_description;
        this.announcement_title = announcement_title;
        this.expirationDate = expirationDate;
    }

    public String getAnnouncement_title() {
        return announcement_title;
    }

    public void setAnnouncement_title(String announcement_title) {
        this.announcement_title = announcement_title;
    }

    public String getAnnouncement_description() {
        return announcement_description;
    }

    public void setAnnouncement_description(String announcement_description) {
        this.announcement_description = announcement_description;
    }

    public List<String> getAttachedFiles() {
        return attachedFiles;
    }

    public void setAttachedFiles(List<String> attachedFiles) {
        this.attachedFiles = attachedFiles;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }
}
