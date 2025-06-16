package repositories;

import models.AssignmentModel;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.stream.Collectors;

public class AssignmentRepository {
    String DB_Path = "data/assignments.csv";
    LinkedList<AssignmentModel> assignments;
    boolean db_changed = false;
    private int last_pk = 0;

    public AssignmentRepository() throws FileNotFoundException {
        loadFromCsv();
        if (!assignments.isEmpty()) {
            last_pk = Math.max(last_pk, assignments.getLast().getId());
        }
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
    public AssignmentModel getAssigmentById(int id) {
        for (AssignmentModel assigment : assignments) {
            if (assigment.getId() == id) {
                return assigment;
            }
        }
        return null;
    }

    public LinkedList<AssignmentModel> getAssignmentsById(LinkedList<Integer> id_list) {
        LinkedList<AssignmentModel> result = new LinkedList<>();
        for (int id : id_list) {
            AssignmentModel assigment = getAssigmentById(id);
            if (assigment != null) {
                result.add(assigment);
            }
        }
        return result;
    }
    public LinkedList<AssignmentModel> getAssignmentsByLectureID(int lecture_id) {
        LinkedList<AssignmentModel> result = new LinkedList<>();
        for ( AssignmentModel assigment : assignments) {
            if (assigment.getBelongedLecture() == lecture_id) {
                result.add(assigment);
            }
        }
        return result;
    }

    // Edit Operations
    public boolean addAssigment(AssignmentModel assigment) {
        last_pk++;
        assigment.setId(last_pk);
        assignments.add(assigment);
        db_changed = true;
        return true;
    }

    public boolean removeAssigment(int assigment_id) {
        for  (AssignmentModel assignment : assignments) {
            if (assignment.getId() == assigment_id) {
                assignments.remove(assignment);
                db_changed = true;
                return true;
            }
        }
        return false;
    }

    // File Operations
    void loadFromCsv() throws FileNotFoundException {
        assignments = new LinkedList<>();
        Scanner sc = new Scanner(new File(DB_Path));
        String line;
        String[] data;
        while (sc.hasNextLine()) {
            line = sc.nextLine();
            data = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
            for (int i = 0; i < data.length; i++) {
                data[i] = data[i].replaceAll("^\"|\"$", ""); // Clean Quotes
            }

            //initialize assignment
            assignments.add(new AssignmentModel(Integer.parseInt(data[0]),//assignment id
                    Integer.parseInt(data[1]), // Belonged Lesson ID
                    data[2],// title
                    data[3],// description
                    Arrays.stream(data[4]. // attachedFiles
                            split(",")). // Split Given Line
                            filter(s -> !s.isEmpty()). // Handle Empty Lists by Removing Them
                            collect(Collectors.toCollection(LinkedList::new)), // Convert into Linked List
                    Float.parseFloat(data[5]),//grade weight
                    LocalDateTime.parse(data[6]),// given date
                    LocalDateTime.parse(data[7])// due date
            ));
        }
    }

    void saveToCsv() throws FileNotFoundException {
        try (FileWriter writer = new FileWriter(DB_Path)) {
            for (AssignmentModel assignment : assignments) {
                writer.write(assignment.getId() + ",");
                writer.write(assignment.getBelongedLecture() + ",");
                writer.write(assignment.getTitle() + ",");
                writer.write(assignment.getDescription() + ",");
                writer.write("\"" + assignment.getAttachedFiles().stream() // Convert Linked List Into CSV Format
                        .map(String::valueOf)
                        .collect(Collectors.joining(",")) + "\",");

                writer.write(assignment.getGradeWeight() + ",");
                writer.write(assignment.getGivenDate() + ",");
                writer.write(assignment.getDueDate() + "\n");
            }
        } catch (IOException e) {
            System.out.println("An Error Occurred While Saving Changes to the Database");
        }
    }
}
