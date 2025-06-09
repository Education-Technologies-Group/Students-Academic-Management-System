package repositories;

import models.AnnouncementModel;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.stream.Collectors;

public class AnnouncementRepository {
    String DB_Path = "data/announcements.csv";
    LinkedList<AnnouncementModel> announcements;
    boolean db_changed = false;

    AnnouncementRepository() throws FileNotFoundException {
        loadFromCsv();
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            if (db_changed) {
                saveToCsv();
            }
        }));
    }

    AnnouncementModel getAnnouncementByID(int id) {
        for (AnnouncementModel announcement : announcements) {
            if (announcement.getAnnouncement_id() == id) {
                return announcement;
            }
        }
        return null;
    }

    void addAnnouncement(AnnouncementModel announcement) {
        announcements.add(announcement);
        db_changed = true;
    }

    void removeAnnouncement(AnnouncementModel announcement) {
        announcements.remove(announcement);
        db_changed = true;
    }

    void loadFromCsv() throws FileNotFoundException {
        announcements = new LinkedList<>();
        Scanner sc = new Scanner(new File(DB_Path));
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            String[] tokens = line.split(",(?=(?:[^\"]\"[^\"]\")[^\"]$)");
            //initialize announcement
            announcements.add(new AnnouncementModel(Integer.parseInt(tokens[0]),
                    tokens[1],
                    tokens[2],
                    tokens[3],
                    Arrays.stream(tokens[4]. // attached files
                            split(",")). // Split Given Line
                            filter(s -> !s.isEmpty()). // Handle Empty Lists by Removing Them
                            collect(Collectors.toCollection(LinkedList::new))));

        }
    }

    void saveToCsv() {
        System.out.println("Saving announcements into database...");
        try (FileWriter writer = new FileWriter(DB_Path)) {
            for (AnnouncementModel announcement : announcements) {
                writer.write(announcement.getAnnouncement_id() + ",");
                writer.write(announcement.getAnnouncement_description() + ",");
                writer.write(announcement.getAnnouncement_title() + ",");
                writer.write(announcement.getExpirationDate() + ",");
                writer.write("\"" + announcement.getAttachedFiles().stream() // Convert Linked List Into CSV Format
                        .map(String::valueOf)
                        .collect(Collectors.joining(",")) + "\",");
            }
        } catch (IOException e) {
            System.out.println("An Error Occurred While Saving Changes to the Database");
        }
    }
}
