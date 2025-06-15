package models.user;

import java.time.LocalDateTime;
import java.util.LinkedList;

public class LecturerModel extends UserModel {
    private String department;
    private LinkedList<LocalDateTime> office_hours;
    // Foreign Keys
    private LinkedList<Integer> lectures;
    private LinkedList<Integer> given_assignments;
    private LinkedList<Integer> announcements;
    private LinkedList<Integer> tickets;


    public LecturerModel(int id, String hashed_password, String full_name, String email, String phone_number,
                         String department, LinkedList<LocalDateTime> office_hours, LinkedList<Integer> lectures,
                         LinkedList<Integer> given_assignments, LinkedList<Integer> announcements, LinkedList<Integer> tickets) {
        super(id, hashed_password, full_name, email, phone_number);
        this.department = department;
        this.office_hours = office_hours;
        this.lectures = lectures;
        this.given_assignments = given_assignments;
        this.announcements = announcements;
        this.tickets = tickets;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public LinkedList<Integer> getLectures() {
        return lectures;
    }

    public void setLectures(LinkedList<Integer> lectures) {
        this.lectures = lectures;
    }

    public LinkedList<LocalDateTime> getOfficeHours() {
        return office_hours;
    }

    public void setOfficeHours(LinkedList<LocalDateTime> office_hours) {
        this.office_hours = office_hours;
    }

    public LinkedList<Integer> getGivenAssignments() {
        return given_assignments;
    }

    public void setGivenAssignments(LinkedList<Integer> given_assignments) {
        this.given_assignments = given_assignments;
    }

    public LinkedList<Integer> getAnnouncements() {
        return announcements;
    }

    public void setAnnouncements(LinkedList<Integer> announcements) {
        this.announcements = announcements;
    }

    public LinkedList<Integer> getTickets() {
        return tickets;
    }

    public void setTickets(LinkedList<Integer> tickets) {
        this.tickets = tickets;
    }
}
