package services;

import models.LectureModel;
import models.LiveSessionModel;
import models.user.StudentModel;
import models.user.UserModel;
import repositories.LectureRepository;
import repositories.LiveSessionRepository;
import repositories.user.StudentRepository;

import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.Scanner;

public class LiveSessionService {
    private final Scanner sc = new Scanner(System.in);
    private final LiveSessionRepository liveSessionRepository;
    private final LectureRepository lectureRepository;
    private final StudentRepository studentRepository;

    public LiveSessionService(LiveSessionRepository liveSessionRepository, LectureRepository lectureRepository, StudentRepository studentRepository) {
        this.liveSessionRepository = liveSessionRepository;
        this.lectureRepository = lectureRepository;
        this.studentRepository = studentRepository;
    }

    public boolean joinSession(UserModel user, int session_id) {
        LiveSessionModel session = liveSessionRepository.getSessionByID(session_id);
        if (session == null) { // Session Not Exists
            return false;
        }
        if (session.getInvitedParticipants().contains(user.getId())) { // User Not Invited
            session.getActiveParticipants().add(user.getId());
            return true;
        }
        return false;
    }

    public void leaveSession(UserModel user, int session_id) {
        LiveSessionModel session = liveSessionRepository.getSessionByID(session_id);
        session.getActiveParticipants().remove(user.getId());
    }

    public void inviteStudentByLectureCode(String lecture_code, int session_id) {
        LiveSessionModel session = liveSessionRepository.getSessionByID(session_id);
        LectureModel lecture = lectureRepository.getLectureByCode(lecture_code);
        LinkedList<StudentModel> students = studentRepository.getStudentsByLecture(lecture.getId());
        for (StudentModel student : students) {
            session.getInvitedParticipants().add(student.getId());
        }
    }

    public boolean createNewSession(LiveSessionModel live_session, String lesson_code) {
        LectureModel lecture = lectureRepository.getLectureByCode(lesson_code);
        LinkedList<StudentModel> invited_students = studentRepository.getStudentsByLecture(lecture.getId());
        for (StudentModel student : invited_students) {
            live_session.getInvitedParticipants().add(student.getId());
        }
        liveSessionRepository.addSession(live_session);
        return true;
    }

    public void recordSession(LiveSessionModel session) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm");
        String video_path = session.getTitle() + "_" + session.getScheduled_date().format(formatter);
        session.setRecord_path(video_path);
    }

    public LinkedList<LiveSessionModel> listHostSessions(UserModel user) {
        LinkedList<LiveSessionModel> sessions = liveSessionRepository.getSessionsByHostID(user.getId());
        return sessions;
    }

    public LinkedList<LiveSessionModel> listUserSessions(UserModel user) {
        LinkedList<LiveSessionModel> user_sessions = liveSessionRepository.getSessionsByUserID(user.getId());
        return user_sessions;
    }

}
