package repositories;

import models.LiveSessionModel;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.Scanner;

public class LiveSessionRepository {
    private final String DB_PATH = "data/live_sessions.csv";
    private LinkedList<LiveSessionModel> sessions;
    private boolean db_changed = false;

    public LiveSessionRepository() throws IOException {
        loadFromCSV();
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            if (db_changed) saveToCSV();
        }));
    }

    public LiveSessionModel getSessionByID(int id) {
        for (LiveSessionModel session : sessions) {
            if (session.getId() == id) {
                return session;
            }
        }
        return null;
    }

    public LinkedList<LiveSessionModel> getSessionsByLecturerID(int lecturerId) {
        LinkedList<LiveSessionModel> result = new LinkedList<>();
        for (LiveSessionModel session : sessions) {
            if (session.getLecturerId() == lecturerId) {
                result.add(session);
            }
        }
        return result;
    }

    public void addSession(LiveSessionModel session) {
        sessions.add(session);
        db_changed = true;
    }

    public void removeSession(LiveSessionModel session) {
        sessions.remove(session);
        db_changed = true;
    }

    private void loadFromCSV() throws IOException {
        sessions = new LinkedList<>();
        File file = new File(DB_PATH);
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            file.createNewFile();
            return;
        }

        Scanner sc = new Scanner(file);
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            String[] data = line.split(",", -1);
            LiveSessionModel session = new LiveSessionModel(
                    Integer.parseInt(data[0]),            // id
                    Integer.parseInt(data[1]),            // lecturerId
                    data[2],                              // title
                    data[3],                              // description
                    LocalDateTime.parse(data[4]),         // creation_date
                    LocalDateTime.parse(data[5]),         // scheduled_date
                    data[6]                               // video
            );
            sessions.add(session);
        }
        sc.close();
    }

    private void saveToCSV() {
        System.out.println("Saving Live Sessions into Database");
        try (FileWriter writer = new FileWriter(DB_PATH)) {
            for (LiveSessionModel session : sessions) {
                writer.write(session.getId() + ",");
                writer.write(session.getLecturerId() + ",");
                writer.write(session.getTitle() + ",");
                writer.write(session.getDescription() + ",");
                writer.write(session.getCreation_date().toString() + ",");
                writer.write(session.getScheduled_date().toString() + ",");
                writer.write(session.getVideo());
                writer.write("\n");
            }
        } catch (IOException e) {
            System.out.println("Error while saving live sessions: " + e.getMessage());
        }
    }
}
