package services;

import java.util.LinkedList;
import models.LectureModel;

import models.user.StudentModel;
import repositories.LectureRepository;
import repositories.user.LecturerRepository;



public class LectureService {
    private final LecturerRepository lecturerRepository;
    private final LectureRepository lectureRepository;

    public LectureService(LecturerRepository lecturerRepository, LectureRepository lectureRepository) {
        this.lecturerRepository = lecturerRepository;
        this.lectureRepository = lectureRepository;

    }

    public boolean checkExistenceByLectureCode(String lecture_code) {
        return lectureRepository.getLectureByCode(lecture_code) != null;
    }
    public LectureModel sendLectureById(int id) {
        return lectureRepository.getLectureById(id);
    }
    public LectureModel sendLectureByCode(String lecture_code) {
        return lectureRepository.getLectureByCode(lecture_code);
    }
    public LinkedList<LectureModel> getStudentLectures(StudentModel student) {
        LinkedList<Integer> lecture_id_list = student.getSignedLectures();
        return lectureRepository.getLecturesById(lecture_id_list);
    }

    public String getLectureSyllabubs(int lecture_id) {
        return lectureRepository.getLectureById(lecture_id).getSyllabus();
    }

    public boolean saveLecture(LectureModel lecture) {
        lectureRepository.addLecture(lecture);
        return true;
    }
}