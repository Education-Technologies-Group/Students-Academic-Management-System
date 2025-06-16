package controllers;

import models.user.*;
import services.LectureService;
import services.UserService;

import java.util.LinkedList;

public class UserController {
    private final String DEFAULT_PASSWORD = "Hello World";
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
        } else if (current_user instanceof AdminModel){
            return "Admin";
        }
        return "Invalid Credentials";
    }

    public LinkedList<String> getStudentGradesByLecturer(int student_id) {
        LinkedList<String> result = new LinkedList<>();
        if (!userService.checkStudentExistenceByID(student_id)) {
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
        if (!userService.checkStudentExistenceByID(student_id)) {
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

    public String createStudent(String full_name, String email, String phone_number,
                                int studentID, String department) {
        if  (!userService.checkStudentExistenceByStudentID(studentID)) {
            return "Student already exists";
        }
        int invalid_id = -1;
        StudentModel student = new StudentModel(
                invalid_id,
                DEFAULT_PASSWORD,
                full_name,
                email,
                phone_number,
                studentID,
                department,
                new LinkedList<>(),
                new LinkedList<>(),
                new LinkedList<>(),
                new LinkedList<>()
        );
        if (userService.register(student)){
            return "Success";
        }else {
            return "Something went wrong...";
        }
    }
    public String createLecturer(String full_name, String email, String phone_number,
                                 String department) {
        int invalid_id = -1;
        LecturerModel lecturer = new LecturerModel(
                invalid_id,
                DEFAULT_PASSWORD,
                full_name,
                email,
                phone_number,
                department,
                new LinkedList<>(),
                new LinkedList<>(),
                new LinkedList<>(),
                new LinkedList<>(),
                new LinkedList<>()
        );
        if (userService.register(lecturer)){
            return "Success";
        }else {
            return "Something went wrong...";
        }
    }
    public String createStudentAffairs(String full_name, String email, String phone_number,
                                int studentID, String department) {
        int invalid_id = -1;
        StudentAffairsModel studentAffairs = new StudentAffairsModel(
                invalid_id,
                DEFAULT_PASSWORD,
                full_name,
                email,
                phone_number,
                department,
                new LinkedList<>()
        );
        if (userService.register(studentAffairs)){
            return "Success";
        }else {
            return "Something went wrong...";
        }
    }
}
