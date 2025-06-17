package services;

import models.AssignmentModel;
import models.user.LecturerModel;
import models.user.StudentModel;
import repositories.AssignmentRepository;
import repositories.LectureRepository;
import repositories.user.StudentRepository;

import java.util.LinkedList;

public class AssignmentService {
    private final AssignmentRepository assigmentRepository;
    private final LectureRepository lectureRepository;
    private final StudentRepository studentRepository;

    public AssignmentService(AssignmentRepository assigmentRepository, LectureRepository lectureRepository, StudentRepository studentRepository) {
        this.assigmentRepository = assigmentRepository;
        this.lectureRepository = lectureRepository;
        this.studentRepository = studentRepository;
    }

    public LinkedList<AssignmentModel> sendStudentAssignments(StudentModel student) {
        LinkedList<Integer> student_assignments = student.getAssignments();
        return assigmentRepository.getAssignmentsById(student_assignments);
    }

    public boolean createAssignment(AssignmentModel assignment) {
        return assigmentRepository.addAssigment(assignment);
    }

    public LinkedList<AssignmentModel> sendLectureAssignments(String lectureCode) {
        int lecture_id = lectureRepository.getLectureByCode(lectureCode).getId();
        return assigmentRepository.getAssignmentsByLectureID(lecture_id);
    }

    public boolean checkOwnership(LecturerModel lecturer, int assignment_id) {
        return lecturer.getLectures().contains(assigmentRepository.getAssigmentById(assignment_id).getBelongedLecture());
    }
    public boolean checkExistence(int assignment_id) {
        return assigmentRepository.getAssigmentById(assignment_id) != null;
    }

    public boolean deleteAssignment(int assignmentId) {
        return assigmentRepository.removeAssigment(assignmentId);
    }
}
