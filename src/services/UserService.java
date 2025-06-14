package services;

import models.user.LecturerModel;
import models.user.StudentAffairsModel;
import models.user.StudentModel;
import models.user.UserModel;
import repositories.user.LecturerRepository;
import repositories.user.StudentAffairsRepository;
import repositories.user.StudentRepository;

import java.io.FileNotFoundException;
import java.util.LinkedList;


public class UserService {
    private final StudentRepository studentRepository;
    private final LecturerRepository lecturerRepository;
    private final StudentAffairsRepository studentAffairsRepository;
    private final String SALT = "Onion";

    UserService(StudentRepository studentRepository, LecturerRepository lecturerRepository, StudentAffairsRepository studentAffairsRepository) throws FileNotFoundException {
        this.studentRepository = studentRepository;
        this.lecturerRepository = lecturerRepository;
        this.studentAffairsRepository = studentAffairsRepository;
    }

    // Auth
    public void register(UserModel user) {
        if (studentRepository.getUserByEmail(user.getEmail()) == null ||
                lecturerRepository.getUserByEmail(user.getEmail()) == null ||
                studentAffairsRepository.getUserByEmail(user.getEmail()) == null) {
            System.out.println("User already exists");
            return;
        }
        user.setHashedPassword(String.valueOf((user.getHashedPassword() + SALT).hashCode()));
        if (user instanceof StudentModel) {
            studentRepository.addStudent((StudentModel) user);
        } else if (user instanceof LecturerModel) {
            lecturerRepository.addLecturer((LecturerModel) user);
        } else if (user instanceof StudentAffairsModel) {
            studentAffairsRepository.addStudentAffair((StudentAffairsModel) user);
        }
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
            return null;
        }
        String hashed_password = String.valueOf((password + SALT).hashCode());
        if (!user.getHashedPassword().equals(hashed_password)) {
            return null;
        }
        return user;
    }

    public void updateUser(UserModel user) {


    }
}
