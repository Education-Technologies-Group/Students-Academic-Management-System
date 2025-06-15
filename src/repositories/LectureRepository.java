package repositories;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.stream.Collectors;

import models.LectureModel;

public class LectureRepository {
    String db_path = "data/lectures.csv";
    LinkedList<LectureModel> lectures;
    boolean db_changed = false;

    public LectureRepository() throws FileNotFoundException {
        loadFromCsv();
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            if (db_changed) {
                try {
                    saveToCsv();
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        }));
    }

    // Select Operations
    public LectureModel getLectureById(int id) {
        for (LectureModel lecture : lectures) {
            if (lecture.getId() == id) {
                return lecture;
            }
        }
        return null;
    }

    public LectureModel getLectureByName(String name) {
        for (LectureModel lecture : lectures) {
            if (lecture.getLectureName().equals(name)) {
                return lecture;
            }
        }
        return null;
    }

    public LectureModel getLectureByCode(String code) {
        for (LectureModel lecture : lectures) {
            if (lecture.getLectureCode().equals(code)) {
                return lecture;
            }
        }
        return null;
    }

    public LinkedList<LectureModel> getLecturesById(LinkedList<Integer> id_list) {
        LinkedList<LectureModel> lectures = new LinkedList<>();
        for (Integer id : id_list) {
            lectures.add(getLectureById(id));
        }
        return lectures;
    }

    // Edit Operations
    void addLecture(LectureModel lecture) {
        lectures.add(lecture);
        db_changed = true;
    }

    void removeLecture(LectureModel lecture) {
        lectures.remove(lecture);
        db_changed = true;

    }

    // File Handlers
    void loadFromCsv() throws FileNotFoundException {
        lectures = new LinkedList<>();
        Scanner sc = new Scanner(new File(db_path));
        String line;
        String[] data;
        while (sc.hasNextLine()) {
            line = sc.nextLine();
            data = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
            for (int i = 0; i < data.length; i++) {
                data[i] = data[i].replaceAll("^\"|\"$", ""); // Clean Quotes
            }
            // create initial lecture
            lectures.add(new LectureModel(
                    Integer.parseInt(data[0]),//id
                    data[1],//code
                    data[2],//name
                    data[3],  // Syllabus
                    Integer.parseInt(data[4]),
                    Arrays.stream(data[5]. // resources
                            split(",")). // Split Given Line
                            filter(s -> !s.isEmpty()). // Handle Empty Lists by Removing Them
                            collect(Collectors.toCollection(LinkedList::new)) // Convert into Linked List
            ));
        }
    }

    void saveToCsv() throws FileNotFoundException {
        System.out.println("Saving lectures into csv ...");
        try (FileWriter writer = new FileWriter(db_path)) {
            for (LectureModel lecture : lectures) {
                writer.write(lecture.getId() + ",");
                writer.write(lecture.getLectureCode() + ",");
                writer.write(lecture.getLectureName() + ",");
                writer.write(lecture.getSyllabus() + ",");
                writer.write(lecture.getLecturerID() + ",");
                writer.write("\"" + lecture.getResources().stream() // Convert Linked List Into CSV Format
                        .map(String::valueOf)
                        .collect(Collectors.joining(",")) + "\",");
            }
        } catch (IOException e) {
            System.out.println("An Error Occurred While Saving Changes to the Database");
        }
    }
}


