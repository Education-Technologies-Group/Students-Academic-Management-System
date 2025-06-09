package models.user;

import java.util.LinkedList;

public class StudentAffairsModel extends UserModel {
    String department;
    LinkedList<Integer> issued_tickets;
    public StudentAffairsModel(int id, String hashed_password, String full_name, String email, String phone_number,
                               String department, LinkedList<Integer> issued_tickets) {
        super(id, hashed_password, full_name, email, phone_number);
        this.department = department;
        this.issued_tickets = issued_tickets;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public LinkedList<Integer> getIssuedTickets() {
        return issued_tickets;
    }

    public void setIssuedTickets(LinkedList<Integer> issued_tickets) {
        this.issued_tickets = issued_tickets;
    }
}
