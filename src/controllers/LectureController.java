package controllers;

import java.util.Iterator;
import java.util.LinkedList;

import services.LectureService;
import models.user.StudentModel;
import models.LectureModel;
import services.UserService;

import static controllers.UserController.current_user;

public class LectureController {
    private final LectureService lectureService;
    private final UserService userService;

    public LectureController(LectureService lectureService, UserService userService) {
        this.lectureService = lectureService;
        this.userService = userService;
    }

    // Student Usage
    public LinkedList<String> getStudentLectures() {
        LinkedList<LectureModel> student_lectures = lectureService.getStudentLectures((StudentModel) current_user);
        LinkedList<String> result = new LinkedList<>();

        for (LectureModel lecture : student_lectures) {
            result.add(
                    "Lecture Code: " + lecture.getLectureCode() + "-" +
                            "Lecture Name: " + lecture.getLectureName());
        }
        return result;
    }

    public LinkedList<String> getStudentLectureGrades() {
        LinkedList<String> result = new LinkedList<>();

        Iterator<Integer> gradeIterator = ((StudentModel) current_user).getGrades().iterator();
        Iterator<LectureModel> lectureIterator = lectureService.getStudentLectures((StudentModel) current_user).iterator();

        while (gradeIterator.hasNext()) {
            LectureModel lecture = lectureIterator.next();
            int grade = gradeIterator.next();
            result.add(
                    "Lesson Name: " + lecture.getLectureName() + "-" +
                            "Grade: " + (grade != -1 ? grade : "--") + "\n");
        }
        return result;
    }

    public String createLecture(String lecture_code, String lecture_name, String syllabus, int lecturerID) {
        if (!userService.checkLecturerExistenceByID(lecturerID)) {
            return "Invalid Lecturer ID";
        }
        int invalid_id = -1;
        LectureModel lecture = new LectureModel(
                invalid_id,
                lecture_code,
                lecture_name,
                syllabus,
                lecturerID,
                new LinkedList<>()
        );
        if (lectureService.saveLecture(lecture)) {
            return "Success";
        } else {
            return "Something went wrong";
        }
    }
}
