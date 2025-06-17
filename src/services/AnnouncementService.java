package services;

import models.AnnouncementModel;
import models.LectureModel;
import models.user.StudentModel;
import models.user.UserModel;
import repositories.AnnouncementRepository;
import repositories.LectureRepository;

import java.util.LinkedList;

public class AnnouncementService {
    private final AnnouncementRepository announcementRepository;
    private final LectureRepository lectureRepository;

    public AnnouncementService(AnnouncementRepository announcementRepository, LectureRepository lectureRepository) {
        this.announcementRepository = announcementRepository;
        this.lectureRepository = lectureRepository;
    }

    public LinkedList<AnnouncementModel> sendStudentAnnouncements(StudentModel student) {
        LinkedList<AnnouncementModel> result = new LinkedList<>();
        result.addAll(announcementRepository.getAnnouncementsByDepartment(student.getDepartment()));
        LinkedList<Integer> lecture_id_list = student.getSignedLectures();
        for (int id : lecture_id_list) {
            String lecture_code = lectureRepository.getLectureById(id).getLectureCode();
            LinkedList<AnnouncementModel> lecture_announcements = announcementRepository.getAnnouncementsByLectureCode(lecture_code);
            for  (AnnouncementModel announcement : lecture_announcements) {
                if (!result.contains(announcement)) {
                    result.add(announcement);
                }
            }
        }
        return result;
    }
    public LinkedList<AnnouncementModel> sendSentAnnouncements(UserModel user) {
        return announcementRepository.getAnnouncementsBySenderID(user.getId());
    }

    public boolean addAnnouncement(AnnouncementModel announcement) {
        return announcementRepository.addAnnouncement(announcement);
    }
    public boolean deleteAnnouncement(int announcement_id) {
        return announcementRepository.removeAnnouncement(announcement_id);
    }
    public boolean checkOwnership(UserModel user, int announcement_id) {
        return announcementRepository.getAnnouncementByID(announcement_id).getSenderId() == user.getId();
    }


    public boolean checkExistence(int announcementId) {
        return announcementRepository.getAnnouncementByID(announcementId) != null;
    }
}
