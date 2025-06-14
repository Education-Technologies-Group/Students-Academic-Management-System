package services;
import models.AssigmentModel;
import models.LectureModel;
import models.user.StudentModel;
import repositories.AssigmentRepository;
import repositories.LectureRepository;
import repositories.user.StudentRepository;

import java.util.LinkedList;
import java.util.Scanner;

public class AssigmentService {
    Scanner sc = new Scanner(System.in);
    LinkedList<StudentModel> students ;
    LinkedList<AssigmentModel> assigments ;


    AssigmentRepository assigmentRepository;
    StudentRepository studentRepository;
    public AssigmentService(AssigmentRepository assigmentRepository, StudentRepository studentRepository) {
        this.assigmentRepository = assigmentRepository;
        this.studentRepository = studentRepository;
    }
    public void edit_Due_date() {
        System.out.print("Please enter the assignment ID for edit your assigment : ");
        String assigmentID = sc.nextLine();
        int assigmentId = Integer.parseInt(assigmentID);

        AssigmentModel assignment ;
        assignment = assigmentRepository.getAssigmentById(assigmentId);

        System.out.println("Please enter the new due date for edit your assigment : ");
        String dueDate = sc.nextLine();

        System.out.println("Your new due date has been edited !");
        System.out.println("Do you want to save your changes ?  (Y/N) ");
        String save = sc.nextLine();

        if (save.equalsIgnoreCase("Y")) {
            assignment.setDue_date(dueDate);
        }
        else
            System.out.println("Changes could not be applied !");

    }
    public void edit_assignment_title() {
        System.out.print("Please enter the assignment ID for edit your assigment title : ");
        int assigmentID = sc.nextInt();

        AssigmentModel assignment = assigmentRepository.getAssigmentById(assigmentID);
        System.out.println("Please enter the new title for edit your assigment : ");
        String title = sc.nextLine();
        System.out.println("Your new title has been edited !");
        System.out.println("Do you want to save your changes ? (Y/N)");
        String save = sc.nextLine();
        if (save.equalsIgnoreCase("Y")) {
            assignment.setTitle(title);
        }else
            System.out.println("Changes could not be applied !");
    }
}
