package controllers;

import models.AnnouncementModel;
import models.LiveSessionModel;
import services.LectureService;
import services.LiveSessionService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;

import static controllers.UserController.current_user;

public class LiveSessionController {
    private final LiveSessionService liveSessionService;
    private final LectureService lectureService;

    public LiveSessionController(LiveSessionService liveSessionService1, LectureService lectureService) {
        this.liveSessionService = liveSessionService1;
        this.lectureService = lectureService;
    }

    public LinkedList<String> sendSessions() {
        LinkedList<LiveSessionModel> user_sessions = liveSessionService.listUserSessions(current_user);
        LinkedList<String> result = new LinkedList<>();
        for (LiveSessionModel session : user_sessions) {
            result.add(
                    session.getId() + " - " +
                            session.getTitle() + " - " +
                            session.getScheduled_date() + " # " +
                            session.getDescription());
        }
        return result;
    }

    public boolean joinSession(int session_id) {
        return liveSessionService.joinSession(current_user, session_id);

    }

    public String createLiveSession(String title, String description, String scheduledDate, String recordPath, String targetLecture) {
        if (targetLecture.isEmpty()) {
            return "You Need to Select a Target for Your Announcement!";
        }

        if (!targetLecture.isEmpty() && !lectureService.checkExistenceByLectureCode(targetLecture)) {
            return "Invalid Lecture Code";
        }
        LocalDateTime date;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        try {
            date = LocalDateTime.parse(scheduledDate, formatter);
        } catch (Exception e) {
            return "Invalid Schedule Date";
        }
        int invalid_id = -1;
        LiveSessionModel session = new LiveSessionModel(
                invalid_id,
                current_user.getId(),
                title,
                description,
                LocalDateTime.now(),
                date,
                recordPath,
                new LinkedList<>(),
                new LinkedList<>()
        );
        if (liveSessionService.createNewSession(session, targetLecture)) {
            return "Success";
        } else {
            return "Something Went Wrong...";
        }
    }
}
