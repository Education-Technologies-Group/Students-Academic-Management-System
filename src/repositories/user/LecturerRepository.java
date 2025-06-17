package repositories.user;

import models.user.LecturerModel;
import models.user.StudentModel;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.stream.Collectors;

import static repositories.user.UserRepository.last_pk;

public class LecturerRepository implements  repositories.user.UserRepositoryInterface{
    String DB_PATH = "data/lecturers.csv";
    LinkedList<LecturerModel> lecturers;
    boolean db_changed = false;

    public LecturerRepository() throws FileNotFoundException {
        loadFromCSV();
        if (!lecturers.isEmpty()) {
            last_pk = Math.max(last_pk, lecturers.getLast().getId());
        }
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            if (db_changed) saveToCSV();
        }));

    }

    public LecturerModel getUserByID(int id) {
        for (LecturerModel lecturer : lecturers) {
            if (lecturer.getId() == id) {
                return lecturer;
            }
        }
        return null;
    }

    public LecturerModel getUserByEmail(String email) {
        for (LecturerModel lecturer : lecturers) {
            if (lecturer.getEmail().equals(email)) {
                return lecturer;
            }
        }
        return null;
    }

    public void addLecturer(LecturerModel lecturer) {
        last_pk++;
        lecturer.setId(last_pk);
        lecturers.add(lecturer);
        db_changed = true;
    }

    void removeLecturer(LecturerModel lecturer) {
        lecturers.remove(lecturer);
        db_changed = true;
    }

    void loadFromCSV() throws FileNotFoundException {
        lecturers = new LinkedList<>();
        Scanner sc = new Scanner(new File(DB_PATH));
        String line;
        String[] data;
        while (sc.hasNextLine()) {
            line = sc.nextLine();
            data = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
            for (int i = 0; i < data.length; i++) {
                data[i] = data[i].replaceAll("^\"|\"$", ""); // Clean Quotes
            }
            // Create initial Student
            lecturers.add(new LecturerModel(Integer.parseInt(data[0]), // Primary Key
                    data[1], // Hashed Password
                    data[2], // Full Name
                    data[3], // Email
                    data[4], // Phone Number
                    data[5], // Department
                    Arrays.stream(data[6]. // Office Hours
                            split(",")). // Split Given Line
                            filter(s -> !s.isEmpty()). // Handle Empty Lists by Removing Them
                            map(LocalDateTime::parse). // Convert Strings into Integers
                            collect(Collectors.toCollection(LinkedList::new)), // Convert into Linked List
                    Arrays.stream(data[7]. // Lectures Foreign Keys
                                    split(",")).
                            filter(s -> !s.isEmpty()).
                            map(Integer::parseInt).
                            collect(Collectors.toCollection(LinkedList::new)),
                    Arrays.stream(data[8]. // Given Assignment Foreign Keys
                                    split(",")).
                            filter(s -> !s.isEmpty()).
                            map(Integer::parseInt).
                            collect(Collectors.toCollection(LinkedList::new)),
                    Arrays.stream(data[9]. // Announcement Foreign Keys
                                    split(",")).
                            filter(s -> !s.isEmpty()).
                            map(Integer::parseInt).
                            collect(Collectors.toCollection(LinkedList::new)),
                    Arrays.stream(data[10]. // Tickets Foreign Keys
                                    split(",")).
                            filter(s -> !s.isEmpty()).
                            map(Integer::parseInt).
                            collect(Collectors.toCollection(LinkedList::new))
            ));
        }
    }

    void saveToCSV() {
        System.out.println("Saving Lecturers into Database");
        try (FileWriter writer = new FileWriter(DB_PATH)) {
            for (LecturerModel lecturer : lecturers) {
                writer.write(lecturer.getId() + ",");
                writer.write(lecturer.getHashedPassword() + ",");
                writer.write(lecturer.getFullName() + ",");
                writer.write(lecturer.getEmail() + ",");
                writer.write(lecturer.getDepartment() + ",");
                writer.write("\"" + lecturer.getOfficeHours().stream() // Convert Linked List Into CSV Format
                        .map(String::valueOf)
                        .collect(Collectors.joining(",")) + "\",");
                writer.write("\"" + lecturer.getGivenAssignments().stream() // Convert Linked List Into CSV Format
                        .map(String::valueOf)
                        .collect(Collectors.joining(",")) + "\",");
                writer.write("\"" + lecturer.getAnnouncements().stream() // Convert Linked List Into CSV Format
                        .map(String::valueOf)
                        .collect(Collectors.joining(",")) + "\",");
                writer.write("\"" + lecturer.getTickets().stream() // Convert Linked List Into CSV Format
                        .map(String::valueOf)
                        .collect(Collectors.joining(",")) + "\"\n");
            }
        } catch (IOException e) {
            System.out.println("An Error Occurred While Saving Changes to the Database");
        }
    }
}
