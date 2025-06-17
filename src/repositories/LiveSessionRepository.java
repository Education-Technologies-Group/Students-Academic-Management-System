package repositories;

import models.LiveSessionModel;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.stream.Collectors;

public class LiveSessionRepository {
    private final String DB_PATH = "data/liveSessions.csv";
    private LinkedList<LiveSessionModel> sessions;
    private boolean db_changed = false;
    int last_pk = 0;

    public LiveSessionRepository() throws IOException {
        loadFromCSV();
        if (!sessions.isEmpty()) {
            last_pk = Math.max(last_pk, sessions.getLast().getId());
        }
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            if (db_changed) saveToCSV();
        }));
    }

    // Select Operations
    public LiveSessionModel getSessionByID(int id) {
        for (LiveSessionModel session : sessions) {
            if (session.getId() == id) {
                return session;
            }
        }
        return null;
    }

    public LinkedList<LiveSessionModel> getSessionsByHostID(int hostId) {
        LinkedList<LiveSessionModel> result = new LinkedList<>();
        for (LiveSessionModel session : sessions) {
            if (session.getHostID() == hostId) {
                result.add(session);
            }
        }
        return result;
    }

    public LinkedList<LiveSessionModel> getSessionsByUserID(int userId) {
        LinkedList<LiveSessionModel> result = new LinkedList<>();
        for (LiveSessionModel session : sessions) {
            if (session.getInvitedParticipants().contains(userId)) {
                result.add(session);
            }
        }
        return result;
    }

    // Edit Operations
    public void addSession(LiveSessionModel session) {
        last_pk++;
        session.setId(last_pk);
        sessions.add(session);
        db_changed = true;

    }

    public void removeSession(LiveSessionModel session) {
        sessions.remove(session);
        db_changed = true;
    }

    // File Operations
    private void loadFromCSV() throws IOException {
        sessions = new LinkedList<>();
        Scanner sc = new Scanner(new File(DB_PATH));
        String line;
        String[] data;
        while (sc.hasNextLine()) {
            line = sc.nextLine();
            data = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
            for (int i = 0; i < data.length; i++) {
                data[i] = data[i].replaceAll("\"", ""); // Clean Quotes
            }
            LiveSessionModel session = new LiveSessionModel(
                    Integer.parseInt(data[0]),            // ID
                    Integer.parseInt(data[1]),            // HostID
                    data[2],                              // Title
                    data[3],                              // Description
                    LocalDateTime.parse(data[4]),         // Creation Date
                    LocalDateTime.parse(data[5]),         // Scheduled Date
                    data[6],                               // Video Path
                    Arrays.stream(data[7]. // Invited Participants Foreign Keys
                                    split(",")).
                            filter(s -> !s.isEmpty()).
                            map(Integer::parseInt).
                            collect(Collectors.toCollection(LinkedList::new)),
                    Arrays.stream(data[7]. // Active Participants Foreign Keys
                                    split(",")).
                            filter(s -> !s.isEmpty()).
                            map(Integer::parseInt).
                            collect(Collectors.toCollection(LinkedList::new))
            );
            sessions.add(session);
        }
        sc.close();
    }

    private void saveToCSV() {
        try (FileWriter writer = new FileWriter(DB_PATH)) {
            for (LiveSessionModel session : sessions) {
                writer.write(session.getId() + ",");
                writer.write(session.getHostID() + ",");
                writer.write(session.getTitle() + ",");
                writer.write(session.getDescription() + ",");
                writer.write(session.getCreation_date().toString() + ",");
                writer.write(session.getScheduled_date().toString() + ",");
                writer.write(session.getRecord_path());
                writer.write("\"" + session.getInvitedParticipants().stream() // Convert Linked List Into CSV Format
                        .map(String::valueOf)
                        .collect(Collectors.joining(",")) + "\",");
                writer.write(session.getRecord_path());
                writer.write("\"" + session.getActiveParticipants().stream() // Convert Linked List Into CSV Format
                        .map(String::valueOf)
                        .collect(Collectors.joining(",")) + "\"\n");
            }
        } catch (IOException e) {
            System.out.println("Error while saving live sessions: " + e.getMessage());
        }
    }
}
