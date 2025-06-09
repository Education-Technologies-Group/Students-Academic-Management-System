package models.user;

public class StudentAffairsModel extends UserModel {
    String department;
    public StudentAffairsModel(int id, String username, String hashed_password, String full_name, String email, String phone_number,
                               String department) {
        super(id, hashed_password, full_name, email, phone_number);
        this.department = department;
    }
}
