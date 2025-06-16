package controllers;

import models.TicketModel;
import models.user.LecturerModel;
import models.user.StudentAffairsModel;
import models.user.StudentModel;
import models.user.UserModel;
import services.LectureService;
import services.UserService;

import java.util.LinkedList;

public class UserController {
    static UserModel current_user;
    private final UserService userService;
    private final LectureService lectureService;

    public UserController(UserService userService, LectureService lectureService) {
        this.userService = userService;
        this.lectureService = lectureService;
    }

    public String login(String email, String password) {
        current_user = userService.login(email, password);
        if (current_user instanceof StudentModel) {
            return "Student";
        } else if (current_user instanceof LecturerModel) {
            return "Lecturer";
        } else if (current_user instanceof StudentAffairsModel) {
            return "StudentAffairs";
        }
        return "Invalid Credentials";
    }

    public LinkedList<String> getStudentGradesByLecturer(int student_id) {
        LinkedList<String> result = new LinkedList<>();
        if (!userService.checkStudentExistence(student_id)) {
            return null;
        }
        LinkedList<String> grades = userService.sendStudentGradesByLecturer((LecturerModel) current_user, student_id);
        for (String grade : grades) {
            String[] data = grade.split(",");
            result.add("Lecture Code: " + data[0] + "-" + "Grade: " + data[1]);
        }
        return result;
    }

    public String updateGrade(int student_id, String lessonCode, int newGrade) {
        if (!userService.checkStudentExistence(student_id)) {
            return "Invalid Student ID";
        }
        if (!lectureService.checkExistenceByLectureCode(lessonCode)) {
            return "Invalid Lecture Code";
        }
        if (newGrade < 0 || newGrade > 100) {
            return "Invalid Grade Value";
        }
        if (userService.updateGrade(student_id, lessonCode, newGrade)) {
            return "Success";
        } else {
            return "Something went wrong...";
        }
    }
}
