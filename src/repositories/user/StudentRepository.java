package repositories.user;

import models.AssigmentModel;
import models.user.StudentModel;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.stream.Collectors;

public class StudentRepository {
    String DB_PATH = "data/students.csv";
    LinkedList<StudentModel> students;
    LinkedList<AssigmentModel> assigments;
    boolean db_changed = false;

    StudentRepository() throws FileNotFoundException {
        loadFromCSV();
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            if (db_changed) saveToCSV();
        }));

    }

    StudentModel getStudentByID(int id) {
        for (StudentModel student : students) {
            if (student.getId() == id) {
                return student;
            }
        }
        return null;
    }

    StudentModel getStudentByStudentID(int studentID) {
        for (StudentModel student : students) {
            if (student.getStudentID() == studentID) {
                return student;
            }
        }
        return null;
    }

    void addStudent(StudentModel student) {
        students.add(student);
        db_changed = true;
    }

    void removeStudent(StudentModel student) {
        students.remove(student);
        db_changed = true;
    }

    void loadFromCSV() throws FileNotFoundException {
        students = new LinkedList<>();
        Scanner sc = new Scanner(new File(DB_PATH));
        String line;
        String[] data;
        while (sc.hasNextLine()) {
            line = sc.nextLine();
            data = line.split(",(?=(?:[^\"]\"[^\"]\")[^\"]$)");
            // Create initial Student
            students.add(new StudentModel(Integer.parseInt(data[0]), // Primary Key
                    data[1], // Hashed Password
                    data[2], // Full Name
                    data[3], // Email
                    data[4], // Phone Number
                    Integer.parseInt(data[5]), // StudentID
                    data[6], // Department
                    Arrays.stream(data[7]. // Grades
                            split(",")). // Split Given Line
                            filter(s -> !s.isEmpty()). // Handle Empty Lists by Removing Them
                            map(Integer::parseInt). // Convert Strings into Integers
                            collect(Collectors.toCollection(LinkedList::new)), // Convert into Linked List
                    Arrays.stream(data[8]. // Signed Lectures Foreign Keys
                                    split(",")).
                            filter(s -> !s.isEmpty()).
                            map(Integer::parseInt).
                            collect(Collectors.toCollection(LinkedList::new)),
                    Arrays.stream(data[9]. // Assignment Foreign Keys
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
        System.out.println("Saving Students into Database");
        try (FileWriter writer = new FileWriter(DB_PATH)) {
            for (StudentModel student : students) {
                writer.write(student.getId() + ",");
                writer.write(student.getHashedPassword() + ",");
                writer.write(student.getFullName() + ",");
                writer.write(student.getEmail() + ",");
                writer.write(student.getStudentID() + ",");
                writer.write(student.getDepartment() + ",");
                writer.write("\"" + student.getGrades().stream() // Convert Linked List Into CSV Format
                        .map(String::valueOf)
                        .collect(Collectors.joining(",")) + "\",");
                writer.write("\"" + student.getSignedLectures().stream() // Convert Linked List Into CSV Format
                        .map(String::valueOf)
                        .collect(Collectors.joining(",")) + "\",");
                writer.write("\"" + student.getAssignments().stream() // Convert Linked List Into CSV Format
                        .map(String::valueOf)
                        .collect(Collectors.joining(",")) + "\",");
                writer.write("\"" + student.getTickets().stream() // Convert Linked List Into CSV Format
                        .map(String::valueOf)
                        .collect(Collectors.joining(",")) + "\"\n");
            }
        } catch (IOException e) {
            System.out.println("An Error Occurred While Saving Changes to the Database");
        }
    }

}
