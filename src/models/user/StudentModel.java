package models.user;

import java.util.LinkedList;

public class StudentModel extends UserModel {
    private int studentID;
    private String department;
    private LinkedList<Integer> signed_lectures;
    private LinkedList<Integer> grades;
    private LinkedList<Integer> assignments;
    private LinkedList<Integer> tickets;

    public StudentModel(int id, String password, String full_name, String email, String phone_number,
                        int studentID, String department) {
        super(id, password, full_name, email, phone_number);
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

    public LinkedList<Integer> getSignedLectures() {
        return signed_lectures;
    }

    public void setSignedLectures(LinkedList<Integer> signed_lectures) {
        this.signed_lectures = signed_lectures;
    }

    public LinkedList<Integer> getGrades() {
        return grades;
    }

    public void setGrades(LinkedList<Integer> grades) {
        this.grades = grades;
    }

    public LinkedList<Integer> getAssignments() {
        return assignments;
    }

    public void setAssignments(LinkedList<Integer> assignments) {
        this.assignments = assignments;
    }

    public LinkedList<Integer> getTickets() {
        return tickets;
    }

    public void setTickets(LinkedList<Integer> tickets) {
        this.tickets = tickets;
    }
}
