package controllers;

import models.user.LecturerModel;
import models.user.StudentAffairsModel;
import models.user.StudentModel;
import models.user.UserModel;
import services.UserService;

public class UserController {
    static UserModel current_user;
    String current_user_role;
    private final UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }

    public String login(String email, String password) {
        current_user = userService.login(email,password);
        if  (current_user instanceof StudentModel) {
            return "Student";
        } else if  (current_user instanceof LecturerModel) {
            return "Lecturer";
        } else if  (current_user instanceof StudentAffairsModel) {
            return "StudentAffairs";
        }
        return "Invalid Credentials";
    }
}
