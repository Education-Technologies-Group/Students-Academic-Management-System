package repositories.user;

import models.user.AdminModel;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import static repositories.user.UserRepository.last_pk;

public class AdminRepository implements repositories.user.UserRepositoryInterface {
    String DB_PATH = "data/admins.csv";
    LinkedList<AdminModel> admins;
    private boolean db_changed = false;

    public AdminRepository() throws FileNotFoundException {
        loadFromCSV();
        if (!admins.isEmpty()) {
            last_pk = Math.max(last_pk, admins.getLast().getId() + 1);
        }
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            if (db_changed) saveToCSV();
        }));
    }

    // Select Operations
    public AdminModel getUserByID(int id) {
        for (AdminModel student : admins) {
            if (student.getId() == id) {
                return student;
            }
        }
        return null;
    }

    public AdminModel getUserByEmail(String email) {
        for (AdminModel student : admins) {
            if (student.getEmail().equals(email)) {
                return student;
            }
        }
        return null;
    }

    // Edit Operations
    public void addAdmin(AdminModel student) {
        student.setId(last_pk);
        admins.add(student);
        db_changed = true;
        last_pk++;
    }

    public void removeAdmin(AdminModel student) {
        admins.remove(student);
        db_changed = true;
    }

    // File Handlers
    private void loadFromCSV() throws FileNotFoundException {
        admins = new LinkedList<>();
        Scanner sc = new Scanner(new File(DB_PATH));
        String line;
        String[] data;
        while (sc.hasNextLine()) {
            line = sc.nextLine();
            data = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
            for (int i = 0; i < data.length; i++) {
                data[i] = data[i].replaceAll("^\"|\"$", ""); // Clean Quotes
            }

            // Create Admin Model and Add to Repo
            admins.add(new AdminModel(Integer.parseInt(data[0]), // Primary Key
                    data[1], // Hashed Password
                    data[2], // Full Name
                    data[3], // Email
                    data[4] // Phone Number
            ));
        }
    }

    private void saveToCSV() {
        try (FileWriter writer = new FileWriter(DB_PATH)) {
            for (AdminModel student : admins) {
                writer.write(student.getId() + ",");
                writer.write(student.getHashedPassword() + ",");
                writer.write(student.getFullName() + ",");
                writer.write(student.getEmail() + ",");
                writer.write(student.getPhoneNumber() + "\n");
            }
        } catch (IOException e) {
            System.out.println("An Error Occurred While Saving Changes to the Database");
        }
    }
}
