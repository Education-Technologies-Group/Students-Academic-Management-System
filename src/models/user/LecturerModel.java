package models.user;
import models.AnnouncementModel;
import models.AssigmentModel;
import models.TicketModel;

import java.time.LocalDateTime;
import java.util.LinkedList;

public class LecturerModel extends UserModel {
    private String department;
    private LinkedList<Integer> lectures;
    private LinkedList<LocalDateTime> office_hours;
    private LinkedList<Integer> given_assignments;
    private LinkedList<Integer> announcements;
    private LinkedList<Integer> tickets;


    public LecturerModel(int id, String password, String full_name, String email, String phone_number,
                         String department) {
        super(id, password, full_name, email, phone_number);
        this.department = department;
        lectures = new LinkedList<>();
        office_hours = new LinkedList<>();
        given_assignments = new LinkedList<>();
        announcements = new LinkedList<>();
        tickets = new LinkedList<>();
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
