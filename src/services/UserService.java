package services;

import models.LectureModel;
import models.user.LecturerModel;
import models.user.StudentAffairsModel;
import models.user.StudentModel;
import models.user.UserModel;
import repositories.LectureRepository;
import repositories.user.AdminRepository;
import repositories.user.LecturerRepository;
import repositories.user.StudentAffairsRepository;
import repositories.user.StudentRepository;

import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Iterator;
import java.util.LinkedList;


public class UserService {
    private final StudentRepository studentRepository;
    private final LecturerRepository lecturerRepository;
    private final StudentAffairsRepository studentAffairsRepository;
    private final AdminRepository adminRepository;
    private final LectureRepository lectureRepository;
    private final String SALT = "Onion";

    public UserService(StudentRepository studentRepository, LecturerRepository lecturerRepository, StudentAffairsRepository studentAffairsRepository, AdminRepository adminRepository, LectureRepository lectureRepository) throws FileNotFoundException {
        this.studentRepository = studentRepository;
        this.lecturerRepository = lecturerRepository;
        this.studentAffairsRepository = studentAffairsRepository;
        this.adminRepository = adminRepository;

        this.lectureRepository = lectureRepository;
    }

    // Auth
    public boolean register(UserModel user) {
        if (!(studentRepository.getUserByEmail(user.getEmail()) == null &&
                lecturerRepository.getUserByEmail(user.getEmail()) == null &&
                studentAffairsRepository.getUserByEmail(user.getEmail()) == null)) {
            return false;
        }
        user.setHashedPassword(HashCreator.createSHAHash(user.getHashedPassword() + SALT));
        if (user instanceof StudentModel) {
            studentRepository.addStudent((StudentModel) user);
        } else if (user instanceof LecturerModel) {
            lecturerRepository.addLecturer((LecturerModel) user);
        } else if (user instanceof StudentAffairsModel) {
            studentAffairsRepository.addStudentAffair((StudentAffairsModel) user);
        }
        return true;
    }

    public UserModel login(String email, String password) {
        UserModel user;
        user = studentRepository.getUserByEmail(email);
        if (user == null) {
            user = lecturerRepository.getUserByEmail(email);
        }
        if (user == null) {
            user = studentAffairsRepository.getUserByEmail(email);
        }
        if (user == null) {
            user = adminRepository.getUserByEmail(email);
        }
        if (user == null) { // User Not Found
            return null;
        }
        String hashed_password = HashCreator.createSHAHash(password + SALT);
        if (!user.getHashedPassword().equals(hashed_password)) { // Incorrect Password
            return null;
        }
        return user;
    }

    public boolean checkStudentExistenceByID(int student_id) {
        return studentRepository.getStudentByStudentID(student_id) != null;
    }
    public boolean checkStudentExistenceByStudentID(int student_id) {
        return studentRepository.getStudentByStudentID(student_id) != null;
    }
    public LinkedList<String> sendStudentGradesByLecturer(LecturerModel lecturer, int student_id){
        LinkedList<String> result = new LinkedList<>();
        StudentModel student = studentRepository.getStudentByStudentID(student_id);
        Iterator<Integer> studentLectures = student.getSignedLectures().iterator();
        Iterator<Integer> studentGrades = student.getGrades().iterator();
        LinkedList<Integer> lecturerLectures = lecturer.getLectures();
        while(studentLectures.hasNext()){
            int lecture_id = studentLectures.next();
            int grade = studentGrades.next();
            if (lecturerLectures.contains(lecture_id)) {
                LectureModel lecture = lectureRepository.getLectureById(lecture_id);
                result.add(lecture.getLectureCode()+","+grade);
            }
        }
        return result;
    }
    public boolean updateGrade(int student_id, String lectureCode, int grade) {
        StudentModel student = studentRepository.getStudentByStudentID(student_id);
        LectureModel lecture = lectureRepository.getLectureByCode(lectureCode);
        LinkedList<Integer> studentLectures = student.getSignedLectures();
        LinkedList<Integer> studentGrades = student.getGrades();
        int index = 0 ;
        for (int signed_lecture_id: studentLectures){
            if (signed_lecture_id == lecture.getId()) {
                studentGrades.set(index, grade);
            }
            index++;
        }
        return true;
    }

    public boolean checkLecturerExistenceByID(int lecturer_id) {
        return lecturerRepository.getUserByID(lecturer_id) != null;
    }

    public static class HashCreator {
        public static String createSHAHash(String input) {

            String hashtext = null;
            MessageDigest md = null;
            try {
                md = MessageDigest.getInstance("SHA-256");
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }
            byte[] messageDigest = md.digest(input.getBytes(StandardCharsets.UTF_8));

            hashtext = convertToHex(messageDigest);
            return hashtext;
        }

        private static String convertToHex(final byte[] messageDigest) {
            BigInteger bigint = new BigInteger(1, messageDigest);
            String hexText = bigint.toString(16);
            while (hexText.length() < 32) {
                hexText = "0".concat(hexText);
            }
            return hexText;
        }
    }

}
