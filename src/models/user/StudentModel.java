package models.user;

import models.AssigmentModel;
import models.GradeDictionaryModel;
import models.TicketModel;

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

    public LinkedList<LecturerModel> getSignedLectures() {
        return signed_lectures;
    }

    public void setSignedLectures(LinkedList<LecturerModel> signed_lectures) {
        this.signed_lectures = signed_lectures;
    }

    public LinkedList<GradeDictionaryModel> getGrades() {
        return grades;
    }

    public void setGrades(LinkedList<GradeDictionaryModel> grades) {
        this.grades = grades;
    }

    public LinkedList<AssigmentModel> getAssignments() {
        return assignments;
    }

    public void setAssignments(LinkedList<AssigmentModel> assignments) {
        this.assignments = assignments;
    }

    public LinkedList<TicketModel> getTickets() {
        return tickets;
    }

    public void setTickets(LinkedList<TicketModel> tickets) {
        this.tickets = tickets;
    }
}
