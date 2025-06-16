package models.user;

public class AdminModel extends UserModel {

    public AdminModel(int id, String password, String full_name, String email, String phone_number) {
        super(id, password, full_name, email, phone_number);
    }

}
