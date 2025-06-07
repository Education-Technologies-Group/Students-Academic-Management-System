package models.user;
import java.time.LocalDateTime;
import java.util.LinkedList;

public class Lecturer extends User {
    private String department;
    private LinkedList<String> lectures;
    private LinkedList<LocalDateTime> office_hours;
    private LinkedList<String> given_assignments; // Change Type Later
    private LinkedList<String> announcements; // Change Type Later
    private LinkedList<String> tickets; // Change Type Later


    public Lecturer(int id, String username, String password, String full_name, String email, String phone_number,
                    String department) {
        super(id, username, password, full_name, email, phone_number);
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

    public LinkedList<String> getLectures() {
        return lectures;
    }

    public void setLectures(LinkedList<String> lectures) {
        this.lectures = lectures;
    }

    public LinkedList<LocalDateTime> getOfficeHours() {
        return office_hours;
    }

    public void setOfficeHours(LinkedList<LocalDateTime> office_hours) {
        this.office_hours = office_hours;
    }

    public LinkedList<String> getGivenAssignments() {
        return given_assignments;
    }

    public void setGivenAssignments(LinkedList<String> given_assignments) {
        this.given_assignments = given_assignments;
    }

    public LinkedList<String> getAnnouncements() {
        return announcements;
    }

    public void setAnnouncements(LinkedList<String> announcements) {
        this.announcements = announcements;
    }

    public LinkedList<String> getTickets() {
        return tickets;
    }

    public void setTickets(LinkedList<String> tickets) {
        this.tickets = tickets;
    }
}
