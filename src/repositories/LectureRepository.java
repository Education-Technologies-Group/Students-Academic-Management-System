package repositories;

import models.LectureModel;
import models.user.LecturerModel;
import models.user.StudentModel;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.stream.Collectors;

public class LectureRepository {
    String db_path = "lectures.csv";
    LinkedList<LectureModel> lectures;
    boolean db_changed = false;

    public LectureRepository() {

    }

    void addLecture(LectureModel lecture) {
        lectures.add(lecture);
        db_changed = true;
    }

    void removeLecture(LectureModel lecture) {
        lectures.remove(lecture);
        db_changed = true;

    }

    LectureModel getLectureId(int id) {
        for (LectureModel lecture : lectures) {
            if (lecture.getId() == id) {
                return lecture;
            }
        }
        return null;
    }

    LectureModel getLectureByName(String name) {
        for (LectureModel lecture : lectures) {
            if (lecture.getLectureName().equals(name)) {
                return lecture;
            }
        }
        return null;
    }

    LectureModel getLectureByCode(String code) {
        for (LectureModel lecture : lectures) {
            if (lecture.getLectureCode().equals(code)) {
                return lecture;
            }
        }
        return null;
    }

    void loadFromCsv() throws FileNotFoundException {
        lectures = new LinkedList<>();
        Scanner sc = new Scanner(new File(db_path));
        String line;
        String[] data;
        while (sc.hasNextLine()) {
            line = sc.nextLine();
            data = line.split(",(?=(?:[^\"]\"[^\"]\")[^\"]$)");
            // create initial lecture
            lectures.add(new LectureModel(
                    Integer.parseInt(data[0]),//id
                    data[1],//code
                    data[2],//name
                    Arrays.stream(data[3]. // syylabus
                            split(",")). // Split Given Line
                            filter(s -> !s.isEmpty()). // Handle Empty Lists by Removing Them
                            collect(Collectors.toCollection(LinkedList::new)), // Convert into Linked List
                    Arrays.stream(data[4]. // gradingDictianory
                            split(",")). // Split Given Line
                            filter(s -> !s.isEmpty()). // Handle Empty Lists by Removing Them
                            collect(Collectors.toCollection(LinkedList::new)), // Convert into Linked List
                    Integer.parseInt(data[5]),
                    Arrays.stream(data[6]. // resources
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
                writer.write("\"" + lecture.getSyllabus().stream() // Convert Linked List Into CSV Format
                        .map(String::valueOf)
                        .collect(Collectors.joining(",")) + "\",");
                writer.write("\"" + lecture.getGradingDictianory().stream() // Convert Linked List Into CSV Format
                        .map(String::valueOf)
                        .collect(Collectors.joining(",")) + "\",");

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


