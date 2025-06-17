package repositories.user;

import models.user.StudentModel;
import models.user.UserModel;

public interface UserRepositoryInterface {
    public UserModel getUserByID(int id);
    public UserModel getUserByEmail(String email);
}
