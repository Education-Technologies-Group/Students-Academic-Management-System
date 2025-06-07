package repositories.user;


import models.user.StudentModel;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Scanner;

public class StudentRepository {
    LinkedList<StudentModel> students;


    StudentRepository() throws FileNotFoundException {
        students = new LinkedList<>();
        Scanner sc = new Scanner(new File("students.csv"));
        sc.useDelimiter(",");
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            String[] data = line.split(",");
            students.add(new StudentModel(Integer.parseInt(data[0]),
                            data[1],
                            data[2],
                            data[3],
                            data[4],
                            Integer.parseInt(data[5]),
                            data[6]));
        }
    }

}
