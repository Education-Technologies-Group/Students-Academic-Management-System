package models.user;

import java.util.LinkedList;

public class Student extends User {
    private int studentID;
    private String department;
    private LinkedList<String> signed_lectures; // Change Type Later
    private LinkedList<String> grades; // Change Type Later
    private LinkedList<String> assignments; // Change Type Later
    private LinkedList<String> tickets;

    public Student(int id, String username, String password, String full_name, String email, String phone_number,
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
