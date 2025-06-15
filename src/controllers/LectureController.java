package controllers;

import java.util.Iterator;
import java.util.LinkedList;

import services.LectureService;
import models.user.StudentModel;
import models.LectureModel;

import static controllers.UserController.current_user;

public class LectureController {
    private final LectureService lectureService;

    public LectureController(LectureService lectureService) {
        this.lectureService = lectureService;
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
                    "Grade: " + (grade != -1 ? grade:"--") + "\n");
        }
        return result;
    }


}
