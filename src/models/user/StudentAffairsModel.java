package models.user;

public class StudentAffairsModel extends UserModel {
    String department;
    public StudentAffairsModel(int id, String username, String password, String full_name, String email, String phone_number,
                               String department) {
        super(id, username, password, full_name, email, phone_number);
        this.department = department;
    }
}
