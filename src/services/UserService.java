package services;

import models.user.LecturerModel;
import models.user.StudentAffairsModel;
import models.user.StudentModel;
import models.user.UserModel;
import repositories.user.LecturerRepository;
import repositories.user.StudentAffairsRepository;
import repositories.user.StudentRepository;

import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.LinkedList;


public class UserService {
    private final StudentRepository studentRepository;
    private final LecturerRepository lecturerRepository;
    private final StudentAffairsRepository studentAffairsRepository;
    private final String SALT = "Onion";

    public UserService(StudentRepository studentRepository, LecturerRepository lecturerRepository, StudentAffairsRepository studentAffairsRepository) throws FileNotFoundException {
        this.studentRepository = studentRepository;
        this.lecturerRepository = lecturerRepository;
        this.studentAffairsRepository = studentAffairsRepository;
    }

    // Auth
    public UserModel register(UserModel user) {
        if (!(studentRepository.getUserByEmail(user.getEmail()) == null &&
                lecturerRepository.getUserByEmail(user.getEmail()) == null &&
                studentAffairsRepository.getUserByEmail(user.getEmail()) == null)) {
            System.out.println("User already exists");
            return null;
        }
        user.setHashedPassword(HashCreator.createSHAHash(user.getHashedPassword() + SALT));
        if (user instanceof StudentModel) {
            studentRepository.addStudent((StudentModel) user);
        } else if (user instanceof LecturerModel) {
            lecturerRepository.addLecturer((LecturerModel) user);
        } else if (user instanceof StudentAffairsModel) {
            studentAffairsRepository.addStudentAffair((StudentAffairsModel) user);
        }
        return user;
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
        if (user == null) { // User Not Found
            return null;
        }
        String hashed_password = HashCreator.createSHAHash(password + SALT);
        if (!user.getHashedPassword().equals(hashed_password)) { // Incorrect Password
            return null;
        }
        return user;
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
