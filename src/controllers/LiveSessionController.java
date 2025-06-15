package controllers;

import models.LiveSessionModel;
import services.LiveSessionService;

import java.util.LinkedList;

import static controllers.UserController.current_user;

public class LiveSessionController {
    private final LiveSessionService liveSessionService;

    public LiveSessionController(LiveSessionService liveSessionService1) {
        this.liveSessionService = liveSessionService1;
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
}
