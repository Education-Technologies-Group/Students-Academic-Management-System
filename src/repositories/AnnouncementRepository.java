package repositories;

import models.AnnouncementModel;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.stream.Collectors;

public class AnnouncementRepository {
    String DB_Path = "data/announcements.csv";
    LinkedList<AnnouncementModel> announcements;
    boolean db_changed = false;
    private int last_pk = 0;

    public AnnouncementRepository() throws FileNotFoundException {
        loadFromCsv();
        if (!announcements.isEmpty()) {
            last_pk = Math.max(last_pk, announcements.getLast().getID());
        }
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            if (db_changed) {
                saveToCsv();
            }
        }));
    }

    // Select Operations
    public AnnouncementModel getAnnouncementByID(int id) {
        for (AnnouncementModel announcement : announcements) {
            if (announcement.getID() == id) {
                return announcement;
            }
        }
        return null;
    }

    public LinkedList<AnnouncementModel> getAnnouncementsByID(LinkedList<Integer> id_list) {
        LinkedList<AnnouncementModel> result = new LinkedList<>();
        for (int id : id_list) {
            result.add(getAnnouncementByID(id));
        }
        return result;
    }

    public LinkedList<AnnouncementModel> getAnnouncementsBySenderID(int id) {
        LinkedList<AnnouncementModel> result = new LinkedList<>();
        for (AnnouncementModel announcement : announcements) {
            if (announcement.getSenderId() == id) {
                result.add(announcement);
            }
        }
        return result;
    }

    public LinkedList<AnnouncementModel> getAnnouncementsByDepartment(String department) {
        LinkedList<AnnouncementModel> result = new LinkedList<>();
        for (AnnouncementModel announcement : announcements) {
            if (announcement.getDepartment().equals(department) || announcement.getDepartment().equals("all")) {
                result.add(announcement);
            }
        }
        return result;
    }

    public LinkedList<AnnouncementModel> getAnnouncementsByLectureCode(String lecture_code) {
        LinkedList<AnnouncementModel> result = new LinkedList<>();
        for (AnnouncementModel announcement : announcements) {
            if (announcement.getLectureCode().equals(lecture_code)) {
                result.add(announcement);
            }
        }
        return result;
    }

    // Edit Operations
    public boolean addAnnouncement(AnnouncementModel announcement) {
        last_pk++;
        announcement.setID(last_pk);
        announcements.add(announcement);
        db_changed = true;
        return true;
    }

    public boolean removeAnnouncement(int announcement_id) {
        for (AnnouncementModel announcement : announcements) {
            if (announcement.getID() == announcement_id) {
                announcements.remove(announcement);
                db_changed = true;
                return true;
            }
        }
        return false;
    }

    // File Operations
    private void loadFromCsv() throws FileNotFoundException {
        announcements = new LinkedList<>();
        Scanner sc = new Scanner(new File(DB_Path));
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            String[] data = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
            for (int i = 0; i < data.length; i++) {
                data[i] = data[i].replaceAll("^\"|\"$", ""); // Clean Quotes
            }

            announcements.add(new AnnouncementModel(Integer.parseInt(data[0]), // ID
                    Integer.parseInt(data[1]), // Sender Foreign Key
                    data[2], // Lesson Code
                    data[3], // Department Name
                    data[4], // Title
                    data[5], // Description
                    Arrays.stream(data[6]. // attached files
                            split(",")). // Split Given Line
                            filter(s -> !s.isEmpty()). // Handle Empty Lists by Removing Them
                            collect(Collectors.toCollection(LinkedList::new)),
                    LocalDateTime.parse(data[7]))); // Expiration Date
        }
    }

    private void saveToCsv() {
        try (FileWriter writer = new FileWriter(DB_Path)) {
            for (AnnouncementModel announcement : announcements) {
                writer.write(announcement.getID() + ",");
                writer.write(announcement.getSenderId() + ",");
                writer.write(announcement.getLectureCode() + ",");
                writer.write(announcement.getDepartment() + ",");
                writer.write(announcement.geTitle() + ",");
                writer.write(announcement.getDescription() + ",");
                writer.write("\"" + announcement.getAttachedFiles().stream() // Convert Linked List Into CSV Format
                        .map(String::valueOf)
                        .collect(Collectors.joining(",")) + "\",");
                writer.write(announcement.getExpirationDate() + "\n");
            }
        } catch (IOException e) {
            System.out.println("An Error Occurred While Saving Changes to the Database");
        }
    }
}
