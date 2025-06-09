package models;

import java.util.List;

public class AnnouncementModel {
    private String announcement_title;
    private String announcement_description;
    private List<String> attachedFiles;
    private String expirationDate;
    private int announcement_id;

    public int getAnnouncement_id() {
        return announcement_id;
    }

    public void setAnnouncement_id(int announcement_id) {
        this.announcement_id = announcement_id;
    }

    public AnnouncementModel(int announcement_id, String announcement_description, String announcement_title,String expirationDate,List<String> attachedFiles) {
        this.announcement_description = announcement_description;
        this.announcement_title = announcement_title;
        this.expirationDate = expirationDate;
        this.attachedFiles = attachedFiles;
        this.announcement_id = announcement_id;
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
