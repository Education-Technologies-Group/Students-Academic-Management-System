package controllers;

import models.AnnouncementModel;
import models.user.StudentModel;
import services.AnnouncementService;
import services.LectureService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;

import static controllers.UserController.current_user;

public class AnnouncementController {
    private final AnnouncementService announcementService;
    private final LectureService lectureService;

    public AnnouncementController(AnnouncementService announcementService, LectureService lectureService) {
        this.announcementService = announcementService;
        this.lectureService = lectureService;
    }

    public LinkedList<String> sendStudentAnnouncements() {
        LinkedList<String> result = new LinkedList<>();
        LinkedList<AnnouncementModel> announcements = announcementService.sendStudentAnnouncements((StudentModel) current_user);
        for (AnnouncementModel announcement : announcements) {
            result.add(
                    announcement.geTitle() + " # " +
                    announcement.getDescription() + " - " +
                    announcement.getLectureCode());
        }
        return result;
    }

    public LinkedList<String> sendSentAnnouncementsByCurrentUser() {
        LinkedList<String> result = new LinkedList<>();
        LinkedList<AnnouncementModel> announcements = announcementService.sendSentAnnouncements(current_user);
        for (AnnouncementModel announcement : announcements) {
            result.add(
                    "ID: " + announcement.getID() + "|" +
                    announcement.geTitle() + " # " +
                            announcement.getDescription() + " - " +
                            announcement.getLectureCode());
        }
        return result;
    }

    public String createAnnouncement(String lecture_code, String department, String title,
                                     String description, LinkedList<String> attached_files, String expiration_date) {
        if (lecture_code.isEmpty() &&  department.isEmpty()) {
            return "You Need to Select a Target for Your Announcement!";
        }

        if (!lecture_code.isEmpty() && !lectureService.checkExistenceByLectureCode(lecture_code)) {
            return "Invalid Lecture Code";
        }
        LocalDateTime date;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        try {
            date= LocalDateTime.parse(expiration_date, formatter);
        } catch (Exception e) {
            return "Invalid Expiration Date";
        }
        int invalid_id = -1;
        AnnouncementModel announcement = new AnnouncementModel(
                invalid_id,
                current_user.getId(),
                lecture_code,
                department,
                title,
                description,
                attached_files,
                date
        );
        if (announcementService.addAnnouncement(announcement)){
            return "Success";
        } else {
            return "Something Went Wrong...";
        }
    }
    public String deleteAnnouncement(int announcement_id) {
        if (!announcementService.checkExistence(announcement_id)){
            return "Invalid Announcement ID";
        }
        if (!announcementService.checkOwnership(current_user, announcement_id)) {
            return "You are not allowed to delete this announcement!";
        }
        if (!announcementService.deleteAnnouncement(announcement_id)){
            return "Something Went Wrong...";
        }
        return "Success";
    }
}
