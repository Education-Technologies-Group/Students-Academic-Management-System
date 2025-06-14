package services;

import models.LiveSessionModel;
import repositories.LiveSessionRepository;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.Scanner;

public class LiveSessionService {
    private final Scanner sc = new Scanner(System.in);
    private final LiveSessionRepository liveSessionRepo;

    public LiveSessionService(LiveSessionRepository repo) {
        this.liveSessionRepo = repo;
    }

    public void joinSession() {
        System.out.print("Enter Session ID to Join: ");
        int id = sc.nextInt();
        LiveSessionModel session = liveSessionRepo.getSessionByID(id);
        if (session != null) {
            System.out.println("You joined the session titled: " + session.getTitle());
        } else {
            System.out.println("Session not found.");
        }
    }

    public void recordSession() {
        System.out.print("Enter Session ID to Record: ");
        int id = sc.nextInt();
        sc.nextLine(); // clear newline
        LiveSessionModel session = liveSessionRepo.getSessionByID(id);
        if (session != null) {
            System.out.print("Enter recording filename (e.g., lecture1.mp4): ");
            String video = sc.nextLine();
            session.setVideo(video);
            System.out.println("Recording set successfully.");
        } else {
            System.out.println("Session not found.");
        }
    }

    public void leaveSession() {
        System.out.print("Enter Session ID to Leave: ");
        int id = sc.nextInt();
        LiveSessionModel session = liveSessionRepo.getSessionByID(id);
        if (session != null) {
            System.out.println("You left the session titled: " + session.getTitle());
        } else {
            System.out.println("Session not found.");
        }
    }

    public void inviteSession() {
        System.out.print("Enter Session ID to Invite Users: ");
        int id = sc.nextInt();
        sc.nextLine(); // consume newline
        LiveSessionModel session = liveSessionRepo.getSessionByID(id);
        if (session != null) {
            System.out.print("Enter email addresses to invite (comma-separated): ");
            String emails = sc.nextLine();
            String[] emailList = emails.split(",");
            for (String email : emailList) {
                System.out.println("Invitation sent to: " + email.trim());
            }
        } else {
            System.out.println("Session not found.");
        }
    }

    public void listSessionsByLecturer(int lecturerId) {
        LinkedList<LiveSessionModel> sessions = liveSessionRepo.getSessionsByLecturerID(lecturerId);
        if (sessions.isEmpty()) {
            System.out.println("No sessions found for lecturer ID: " + lecturerId);
        } else {
            for (LiveSessionModel session : sessions) {
                System.out.println(session.getId() + " | " + session.getTitle() + " | " + session.getScheduled_date());
            }
        }
    }

    public void createNewSession() {
        try {
            System.out.print("Enter Lecturer ID: ");
            int lecturerId = sc.nextInt();
            sc.nextLine(); // clear newline

            System.out.print("Enter Title: ");
            String title = sc.nextLine();

            System.out.print("Enter Description: ");
            String description = sc.nextLine();

            LocalDateTime creationDate = LocalDateTime.now();

            System.out.print("Enter Scheduled Date (yyyy-MM-ddTHH:mm:ss): ");
            String input = sc.nextLine();
            LocalDateTime scheduledDate = LocalDateTime.parse(input);

            LiveSessionModel session = new LiveSessionModel(
                    generateSessionID(),
                    lecturerId,
                    title,
                    description,
                    creationDate,
                    scheduledDate,
                    ""
            );

            liveSessionRepo.addSession(session);
            System.out.println("Session created successfully.");

        } catch (Exception e) {
            System.out.println("Error creating session: " + e.getMessage());
        }
    }

    private int generateSessionID() {
        // Basic auto-incrementing logic
        int maxId = 0;
        for (LiveSessionModel session : liveSessionRepo.getSessionsByLecturerID(0)) {
            maxId = Math.max(maxId, session.getId());
        }
        return maxId + 1;
    }
}
