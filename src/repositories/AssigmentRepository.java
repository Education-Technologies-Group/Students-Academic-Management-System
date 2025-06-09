package repositories;
import models.AssigmentModel;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.stream.Collectors;

public class AssigmentRepository {
    String DB_Path = "data/assigments.csv";
    LinkedList<AssigmentModel> assigments;
    boolean db_changed = false;

    AssigmentRepository() throws FileNotFoundException {
        LoadFromCsv();
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

    AssigmentModel getAssigmentId(int id) {
        for (AssigmentModel assigment : assigments) {
            if (assigment.getId() == id) {
                return assigment;
            }
        }
        return null;
    }
     void addAssigment(AssigmentModel assigment) {
        assigments.add(assigment);
        db_changed = true;
     }
     void removeAssigment(AssigmentModel assigment) {
        assigments.remove(assigment);
        db_changed = true;
     }

     void LoadFromCsv() throws FileNotFoundException {
        assigments = new LinkedList<>();
        Scanner sc = new Scanner(new File(DB_Path));
        String line;
        String[]  data;
        while (sc.hasNextLine()) {
            line = sc.nextLine();
            data = line.split(",(?=(?:[^\"]\"[^\"]\")[^\"]$)");

            //initialize assignment
            assigments.add(new AssigmentModel(Integer.parseInt(data[0]),//assignment id
                    data[1],// title
                    data[2],// description
                    Arrays.stream(data[3]. // attachedFiles
                            split(",")). // Split Given Line
                            filter(s -> !s.isEmpty()). // Handle Empty Lists by Removing Them
                            collect(Collectors.toCollection(LinkedList::new)), // Convert into Linked List
                    Float.parseFloat(data[4]),//grade weight
                    data[5],// given date
                    data[6]// due date
            ));
        }
     }

     void saveToCsv() throws FileNotFoundException {
         System.out.println("Saving assigments into database..." );
         try (FileWriter writer = new FileWriter(DB_Path)) {
             for (AssigmentModel assignment : assigments) {
                 writer.write(assignment.getId() + ",");
                 writer.write(assignment.getTitle() + ",");
                 writer.write(assignment.getDescription() + ",");
                 writer.write("\"" + assignment.getAttachedFiles().stream() // Convert Linked List Into CSV Format
                         .map(String::valueOf)
                         .collect(Collectors.joining(",")) + "\",");

                 writer.write(assignment.getGrade_weight() + ",");
                 writer.write(assignment.getGiven_date() + ",");
                 writer.write(assignment.getDue_date() + ",");
             }
         } catch (IOException e) {
             System.out.println("An Error Occurred While Saving Changes to the Database");
         }
     }




}
