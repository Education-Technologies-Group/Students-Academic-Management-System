package repositories.user;

import models.user.StudentModel;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static repositories.user.UserRepository.last_pk;

public class StudentRepository implements  repositories.user.UserRepositoryInterface {
    String DB_PATH = "data/students.csv";
    LinkedList<StudentModel> students;
    private boolean db_changed = false;

    public StudentRepository() throws FileNotFoundException {
        loadFromCSV();
        if (!students.isEmpty()) {
            last_pk = Math.max(last_pk, students.getLast().getId() + 1);
        }
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            if (db_changed) saveToCSV();
        }));
    }

    // Select Operations
    public StudentModel getUserByID(int id) {
        for (StudentModel student : students) {
            if (student.getId() == id) {
                return student;
            }
        }
        return null;
    }

    public StudentModel getUserByEmail(String email) {
        for (StudentModel student : students) {
            if (student.getEmail().equals(email)) {
                return student;
            }
        }
        return null;
    }

    public StudentModel getStudentByStudentID(int studentID) {
        for (StudentModel student : students) {
            if (student.getStudentID() == studentID) {
                return student;
            }
        }
        return null;
    }

    public LinkedList<StudentModel> getStudentsByDepartment(String department) {
        LinkedList<StudentModel> selected_students = new LinkedList<>();
        for (StudentModel student : students) {
            if (student.getDepartment().equals(department)) {
                selected_students.add(student);
            }
        }
        return selected_students;
    }

    public LinkedList<StudentModel> getStudentsByLecture(int lecture_id) {
        LinkedList<StudentModel> selected_students = new LinkedList<>();
        for (StudentModel student : students) {
            if (student.getSignedLectures().contains(lecture_id)) {
                selected_students.add(student);
            }
        }
        return selected_students;
    }

    public LinkedList<Integer> getTicketsByID(int id) {
        for (StudentModel student : students) {
            if  (student.getId() == id) {
                return student.getTickets();
            }
        }
        return null;
    }

    // Edit Operations
    public void addStudent(StudentModel student) {
        student.setId(last_pk);
        students.add(student);
        db_changed = true;
        last_pk++;
    }

    public void updateStudent(StudentModel updated_student) {
        for (StudentModel old_student : students) {
            if (old_student.getId() == updated_student.getId()) {
                old_student.setEmail(updated_student.getEmail());
            }
        }
        throw new NoSuchElementException("Student Not Found");
    }

    public void removeStudent(StudentModel student) {
        students.remove(student);
        db_changed = true;
    }

    // File Handlers
    void loadFromCSV() throws FileNotFoundException {
        students = new LinkedList<>();
        Scanner sc = new Scanner(new File(DB_PATH));
        String line;
        String[] data;
        while (sc.hasNextLine()) {
            line = sc.nextLine();
            data = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
            for (int i = 0; i < data.length; i++) {
                data[i] = data[i].replaceAll("^\"|\"$", ""); // Clean Quotes
            }

            // Create Student Model and Add to Repo
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
