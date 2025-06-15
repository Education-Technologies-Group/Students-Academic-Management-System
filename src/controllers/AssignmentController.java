package controllers;

import models.AssignmentModel;
import models.user.LecturerModel;
import models.user.StudentModel;
import services.AssigmentService;
import services.LectureService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;

import static controllers.UserController.current_user;

public class AssignmentController {
    private final AssigmentService assignmentService;
    private final LectureService lectureService;

    public AssignmentController(AssigmentService assignmentService, LectureService lectureService) {
        this.assignmentService = assignmentService;
        this.lectureService = lectureService;
    }

    public LinkedList<String> sendStudentAssignments() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd - HH:mm");
        LinkedList<AssignmentModel> assignments = assignmentService.sendStudentAssignments((StudentModel) current_user);
        LinkedList<String> student_assignments = new LinkedList<>();
        for (AssignmentModel assignment : assignments) {
            student_assignments.add(
                    "Lecture Code: " + lectureService.sendLectureById(assignment.getBelongedLecture()).getLectureCode() + "|" +
                            "Name: " + assignment.getTitle() + " - " +
                            "Due Date: " + assignment.getDueDate().format(formatter) + "# " +
                            assignment.getDescription());
        }
        return student_assignments;
    }

    public LinkedList<String> sendLectureAssignments(String lectureCode) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd - HH:mm");
        LinkedList<AssignmentModel> assignments = assignmentService.sendLectureAssignments(lectureCode);
        LinkedList<String> lecture_assignments = new LinkedList<>();
        for (AssignmentModel assignment : assignments) {
            lecture_assignments.add(
                    "ID: " + assignment.getId() + "|" +
                            "Name: " + assignment.getTitle() + " - " +
                            "Due Date: " + assignment.getDueDate().format(formatter) + "# " +
                            assignment.getDescription());
        }
        return lecture_assignments;
    }

    public String createAssignment(String belonged_lecture_code, String title, String description, LinkedList<String> attachedFiles,
                                   float grade_weight, String due_date) {
        if (lectureService.sendLectureByCode(belonged_lecture_code) == null) {
            return "Lecture Code Invalid";
        }
        if (grade_weight < 0 || grade_weight > 1) {
            return "Grade Weight must be Between(Inclusive) 0 and 1";
        }
        LocalDateTime given_date = LocalDateTime.now();

        int belonged_lecture_id = lectureService.sendLectureByCode(belonged_lecture_code).getId();

        LocalDateTime date;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        try {
            date = LocalDateTime.parse(due_date, formatter);
        } catch (Exception e) {
            return "Invalid Expiration Date";
        }

        int invalid_id = -1;
        AssignmentModel assignment = new AssignmentModel(
                invalid_id,
                belonged_lecture_id,
                title,
                description,
                attachedFiles,
                grade_weight,
                given_date,
                date
        );
        if (assignmentService.createAssignment(assignment)) {
            return "Success";
        } else {
            return "Something went wrong...";
        }
    }

    public String deleteAssignment(int assignment_id) {
        if (!assignmentService.checkOwnership((LecturerModel) current_user, assignment_id)) {
            return "You are not allowed to delete this assignment!";
        }
        if (!assignmentService.deleteAssignment(assignment_id)){
            return "Something Went Wrong...";
        }
        return "Success";
    }
}
