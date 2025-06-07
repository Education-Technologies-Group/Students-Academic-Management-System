package models.user;

import models.AssigmentModel;
import models.GradeDictionary;
import models.TicketControllerModel;

import java.util.LinkedList;

public class StudentModel extends UserModel {
    private int studentID;
    private String department;
    private LinkedList<LecturerModel> signed_lectures;
    private LinkedList<GradeDictionaryModel> grades;
    private LinkedList<AssigmentModel> assignments;
    private LinkedList<TicketModel> tickets;

    public StudentModel(int id, String username, String password, String full_name, String email, String phone_number,
                        int studentID, String department) {
        super(id, username, password, full_name, email, phone_number);
        this.studentID = studentID;
        this.department = department;
        signed_lectures = new LinkedList<>();
        grades = new LinkedList<>();
        assignments = new LinkedList<>();
        tickets = new LinkedList<>();
    }

    public int getStudentID() {
        return studentID;
    }

    public void setStudentID(int studentID) {
        this.studentID = studentID;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public LinkedList<String> getSignedLectures() {
        return signed_lectures;
    }

    public void setSignedLectures(LinkedList<String> signed_lectures) {
        this.signed_lectures = signed_lectures;
    }

    public LinkedList<String> getGrades() {
        return grades;
    }

    public void setGrades(LinkedList<String> grades) {
        this.grades = grades;
    }

    public LinkedList<String> getAssignments() {
        return assignments;
    }

    public void setAssignments(LinkedList<String> assignments) {
        this.assignments = assignments;
    }

    public LinkedList<String> getTickets() {
        return tickets;
    }

    public void setTickets(LinkedList<String> tickets) {
        this.tickets = tickets;
    }
}
