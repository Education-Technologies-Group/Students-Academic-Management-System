package repositories.user;

import models.user.StudentAffairsModel;
import models.user.StudentModel;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.stream.Collectors;

import static repositories.user.UserRepository.last_pk;


public class StudentAffairsRepository {
    String DB_PATH = "data/student_affairs.csv";
    LinkedList<StudentAffairsModel> student_affairs;
    boolean db_changed = false;

    public StudentAffairsRepository() throws FileNotFoundException {
        loadFromCSV();
        if (!student_affairs.isEmpty()) {
            last_pk = Math.max(last_pk, student_affairs.getLast().getId());
        }
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            if (db_changed) saveToCSV();
        }));

    }

    public StudentAffairsModel getUserByID(int id) {
        for (StudentAffairsModel student : student_affairs) {
            if (student.getId() == id) {
                return student;
            }
        }
        return null;
    }

    public StudentAffairsModel getUserByEmail(String email) {
        for (StudentAffairsModel student_affair : student_affairs) {
            if (student_affair.getEmail().equals(email)) {
                return student_affair;
            }
        }
        return null;
    }

    public void addStudentAffair(StudentAffairsModel student) {
        student_affairs.add(student);
        db_changed = true;
    }

    public void removeStudentAffair(StudentAffairsModel student) {
        student_affairs.remove(student);
        db_changed = true;
    }

    void loadFromCSV() throws FileNotFoundException {
        student_affairs = new LinkedList<>();
        Scanner sc = new Scanner(new File(DB_PATH));
        String line;
        String[] data;
        while (sc.hasNextLine()) {
            line = sc.nextLine();
            data = line.split(",(?=(?:[^\"]\"[^\"]\")[^\"]$)");
            // Create initial Student
            student_affairs.add(new StudentAffairsModel(Integer.parseInt(data[0]), // Primary Key
                    data[1], // Hashed Password
                    data[2], // Full Name
                    data[3], // Email
                    data[4], // Phone Number
                    data[5], // Department
                    Arrays.stream(data[6]. // Issued Tickets
                            split(",")). // Split Given Line
                            filter(s -> !s.isEmpty()). // Handle Empty Lists by Removing Them
                            map(Integer::parseInt). // Convert Strings into Integers
                            collect(Collectors.toCollection(LinkedList::new))// Convert into Linked List
            ));
        }
    }

    void saveToCSV() {
        System.out.println("Saving Students Affairs into Database");
        try (FileWriter writer = new FileWriter(DB_PATH)) {
            for (StudentAffairsModel student_affair : student_affairs) {
                writer.write(student_affair.getId() + ",");
                writer.write(student_affair.getHashedPassword() + ",");
                writer.write(student_affair.getFullName() + ",");
                writer.write(student_affair.getEmail() + ",");
                writer.write(student_affair.getDepartment() + ",");
                writer.write("\"" + student_affair.getIssuedTickets().stream() // Convert Linked List Into CSV Format
                        .map(String::valueOf)
                        .collect(Collectors.joining(",")) + "\"\n");
            }
        } catch (IOException e) {
            System.out.println("An Error Occurred While Saving Changes to the Database");
        }
    }
}
