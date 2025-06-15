
import controllers.*;
import repositories.AnnouncementRepository;
import repositories.AssignmentRepository;
import repositories.LectureRepository;
import repositories.LiveSessionRepository;
import repositories.user.LecturerRepository;
import repositories.user.StudentAffairsRepository;
import repositories.user.StudentRepository;
import services.*;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Scanner;

public class Main {

    // Controllers
    public static UserController userController;
    public static LectureController lectureController;
    public static LiveSessionController liveSessionController;
    public static AssignmentController assignmentController;
    public static AnnouncementController announcementController;

    // Services
    public static UserService userService;
    public static LectureService lectureService;
    public static LiveSessionService liveSessionService;
    public static AssigmentService assigmentService;
    public static AnnouncementService announcementService;

    // Repos
    public static StudentRepository studentRepository;
    public static LecturerRepository lecturerRepository;
    public static StudentAffairsRepository studentAffairsRepository;

    public static LectureRepository lectureRepository;
    public static LiveSessionRepository liveSessionRepository;
    public static AssignmentRepository assignmentRepository;
    public static AnnouncementRepository announcementRepository;


    static Scanner input = new Scanner(System.in);
    static String user_role = "None";
    static boolean user_interface_active = true;
    static boolean backpagevaild;
    static int backpage;

    public static void main(String[] args) throws IOException {
        initializeSystem();
        displayWelcomeScreen();
        while (user_interface_active) {
            String auth_selection = input.nextLine();
            // User Selected Login
            if (auth_selection.equals("1")) {
                String response = login();
                switch (response) {
                    case "Student" -> user_role = "Student";
                    case "Lecturer" -> user_role = "Lecturer";
                    case "StudentAffairs" -> user_role = "StudentAffairs";
                    case "Admin" -> user_role = "Admin";
                    default -> user_role = "None";
                }
            } else if (auth_selection.equals("2")) {
                user_interface_active = false;
                logout();
                break;
            } else {
                System.out.println("Invalid option try again ...");
                System.out.print("--> :");
                continue;
            }
            switch (user_role) {
                // Student Menu
                case "Student" -> {
                    displayStudentMenu();
                    int user_input = Integer.parseInt(input.nextLine());

                    switch (user_input) {
                        case 1 -> {
                            displayStudentLecturers();
                        }
                        case 2 -> {
                            displayStudentGrades();
                        }
                        case 3 -> {
                            handeUserLiveSessionJoin();
                        }
                        case 4 -> {
                            displayStudentAssignments();
                        }
                        case 5 -> {
                            displayStudentAnnouncements();
                        }
                        case 6 -> {
                            user_interface_active = false;
                            logout();
                        }
                    }
                }

                // Lecturer Menu
                case "Lecturer" -> {
                    displayLecturerMenu();
                    int user_input = Integer.parseInt(input.nextLine());
                    switch (user_input) {
                        case 1 -> displaySentAnnouncements();
                        case 2 -> handleAddAnnouncement();
                        case 3 -> handeDeleteAnnouncement();
                        case 4 -> handleDisplayLectureAssignments();
                        case 5 -> handleAddAssignment();
                        case 6 -> handleDeleteAssignment();
                        case 7 -> {
                            System.out.println("Live session opened successfully ...:");
                            //LECTURER CONTROLLER
                            turnback_or_exit_screen();
                            turnback_or_exit_ProcessforLecturer();
                        }
                        case 8 -> {
                            System.out.println("Your Tickets :");
                            turnback_or_exit_screen();
                            turnback_or_exit_ProcessforLecturer();
                        }
                        case 9 -> {
                            System.out.println("Assignment edited successfully ...:");
                            //Assignment service de yazıldı title ve due date editleme
                            //LECTURE CONTROLLER
                            turnback_or_exit_screen();
                            turnback_or_exit_ProcessforLecturer();
                        }
                        case 10 -> {
                            System.out.println("Announcement edited successfully ...:");
                            //Announcement service de yazılı
                            //LECTURE CONTROLLER
                            turnback_or_exit_screen();
                            turnback_or_exit_ProcessforLecturer();
                        }
                        case 11 -> {
                            user_interface_active = false;
                            logout();
                        }
                        default -> System.out.println("Invalid option try again ...");
                    }
                }


                // Student Affairs Menu
                case "StudentAffairs" -> {
                    displayStudentAffairsMenu();
                    int user_input = Integer.parseInt(input.nextLine());
                    switch (user_input) {
                        case 1:
                            System.out.println("Ticket requests are answered successfully ...:");
                            //S.AFFAİRS CONTROLLER
                            turnback_or_exit_screen();
                            turnback_or_exit_ProcessforStudentaffairs();
                            break;
                        case 2:
                            System.out.println("Announcement added successfully ...:");
                            //S.AFFAİRS CONTROLLER
                            turnback_or_exit_screen();
                            turnback_or_exit_ProcessforStudentaffairs();
                            break;
                        default:
                            System.out.println("Invalid option try again ...");
                            break;
                    }
                }

                // Admin Menu
                case "Admin" -> displayAdminMenu();

                // No User Currently Logged In
                default -> displayWelcomeScreen();
            }


        }
    }

    // System
    public static void initializeSystem() throws IOException {
        // Initialize Repos
        studentRepository = new StudentRepository();
        lecturerRepository = new LecturerRepository();
        studentAffairsRepository = new StudentAffairsRepository();

        lectureRepository = new LectureRepository();
        liveSessionRepository = new LiveSessionRepository();
        assignmentRepository = new AssignmentRepository();
        announcementRepository = new AnnouncementRepository();

        // Initialize Services
        userService = new UserService(studentRepository, lecturerRepository, studentAffairsRepository);
        lectureService = new LectureService(lecturerRepository, lectureRepository);
        liveSessionService = new LiveSessionService(liveSessionRepository, lectureRepository, studentRepository);
        assigmentService = new AssigmentService(assignmentRepository, lectureRepository, studentRepository);
        announcementService = new AnnouncementService(announcementRepository, lectureRepository);

        // Initialize Controllers
        userController = new UserController(userService);
        lectureController = new LectureController(lectureService);
        liveSessionController = new LiveSessionController(liveSessionService);
        assignmentController = new AssignmentController(assigmentService, lectureService);
        announcementController = new AnnouncementController(announcementService, lectureService);
    }

    // Auth
    public static String login() {
        System.out.print("Please enter your email :");
        String email = input.nextLine();
        System.out.print("Please enter your password :");
        String password = input.nextLine();
        return userController.login(email, password); // Send Response Back
    }

    public static void logout() {
        System.out.println("Successfully logged out from SAMS ...");
        System.out.println("Goodbye!");
    }

    // User Menus
    public static void displayWelcomeScreen() {
        System.out.println("Welcome to SAMS !");
        System.out.println("Please choose one of the following options:");
        System.out.println("1. Login");
        System.out.println("2. Exit");
        System.out.print("--> :");
    }

    public static void displayStudentMenu() {
        System.out.println("Please select the operation you want to perform.");
        System.out.println("1. View Lectures");
        System.out.println("2. View Grades");
        System.out.println("3. Join live session");
        System.out.println("4. View Assignments");
        System.out.println("5. View Announcements");
        System.out.println("6. Exit");
        System.out.print("--> :");
    }

    public static void displayLecturerMenu() {
        System.out.println("Please select the operation you want to perform.");
        System.out.println("1.Display Sent Announcements");
        System.out.println("2.Add Announcement");
        System.out.println("3.Delete Announcement");
        System.out.println("4.Display Given Assignments");
        System.out.println("5.Add Assignment");
        System.out.println("6.Delete Assignment");
        System.out.println("7.Edit Grades");
        System.out.println("8.Open Live Session");
        System.out.println("9.View Tickets");
        System.out.println("10.Edit Assignment");
        System.out.println("11.Edit Announcement");
        System.out.println("12.Exit");
        System.out.print("-->");


    }

    public static void displayStudentAffairsMenu() {
        System.out.println("Please select the operation you want to perform.");
        System.out.println("1.Ticket Requests");
        System.out.println("2.Add Announcement");
    }

    public static void displayAdminMenu() {
        System.out.println("Please select the operation you want to perform.");
        System.out.println("1. Create User");
        System.out.println("2. Update User");
    }

    // Student Menu Operations
    public static void displayStudentLecturers() {
        System.out.println(" Your Lectures: ");
        LinkedList<String> lecture_lines = lectureController.getStudentLectures();
        for (String lecture : lecture_lines) {
            System.out.println(lecture);
        }
    }

    public static void displayStudentGrades() {
        System.out.println(" Your Grades: ");
        LinkedList<String> grade_lines = lectureController.getStudentLectureGrades();
        for (String grade : grade_lines) {
            System.out.println(grade);
        }
    }

    public static void handeUserLiveSessionJoin() {
        System.out.println("Invited Live Sessions: ");
        LinkedList<String> sessions = liveSessionController.sendSessions();
        for (String session : sessions) {
            System.out.println(session);
        }
        System.out.print("Pls Enter a Live session ID to Join: ");
        int sessionID = Integer.parseInt(input.nextLine());
        if (liveSessionController.joinSession(sessionID)) {
            System.out.println("Successfully Joined Live Session");
        } else {
            System.out.println("Failed To Join Live Session");
        }

    }

    public static void displayStudentAssignments() {
        LinkedList<String> assignments = assignmentController.sendStudentAssignments();
        for (String assignment : assignments) {
            System.out.println(assignment);
        }

    }

    public static void displayStudentAnnouncements() {
        LinkedList<String> announcements = announcementController.sendStudentAnnouncements();
        for (String announcement : announcements) {
            System.out.println(announcement);
        }
    }

    // Lecturer Menu Operations
    public static void displaySentAnnouncements() {
        LinkedList<String> announcements = announcementController.sendSentAnnouncementsByCurrentUser();
        for (String announcement : announcements) {
            System.out.println(announcement);
        }

    }

    public static void handleAddAnnouncement() {
        // Necessary Inputs for Creating Announcement
        String lectureCode;
        String department;
        String title;
        String description;
        LinkedList<String> attachedFiles;
        String expirationDate;
        System.out.println("You can Skip any of the Inputs by Pressing Enter...");

        System.out.print("Enter lecture code which will be target: ");
        lectureCode = input.nextLine().trim();

        System.out.print("Enter department name which will be target: ");
        department = input.nextLine().trim();

        System.out.print("Enter title: ");
        title = input.nextLine();

        System.out.print("Enter description: ");
        description = input.nextLine();

        System.out.print("Enter number of attached files: ");
        int fileCount = 0;
        try {
            fileCount = Integer.parseInt(input.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid number...");
        }
        attachedFiles = new LinkedList<>();
        for (int i = 0; i < fileCount; i++) {
            System.out.print("Enter file path " + (i + 1) + ": ");
            attachedFiles.add(input.nextLine());
        }

        System.out.print("Enter Expiration Date and Time (eg. '2025-12-23 21:00'): ");
        expirationDate = input.nextLine().trim();

        String response = announcementController.createAnnouncement(lectureCode, department, title, description, attachedFiles, expirationDate);
        // Now the `announcement` object is fully populated from user input.
        if (response.equals("Success")) {
            System.out.println("Announcement created successfully!");
        } else {
            System.out.println("Error: " + response);
        }
    }

    public static void handeDeleteAnnouncement() {
        displaySentAnnouncements();
        System.out.print("Pls enter the ID of the announcement you want to delete: ");
        int announcementID = -1;
        try {
            announcementID = Integer.parseInt(input.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid ID...");
        }
        String response = announcementController.deleteAnnouncement(announcementID);
        if (response.equals("Success")) {
            System.out.println("Announcement deleted successfully!");
        } else {
            System.out.println("Error: " + response);
        }
    }

    public static void handleDisplayLectureAssignments() {
        System.out.print("Pls enter lecture code to display it's assignments: ");
        String lectureCode = input.nextLine().trim();
        LinkedList<String> assignments = assignmentController.sendLectureAssignments(lectureCode);
        for (String assignment : assignments) {
            System.out.println(assignment);
        }
    }

    public static void handleAddAssignment() {
        // Necessary Inputs for Creating Assignment
        String belongedLecture;
        String title;
        String description;
        LinkedList<String> attachedFiles;
        float gradeWeight = 0;
        String dueDate;

        System.out.println("You can Skip any of the Inputs by Pressing Enter where applicable...");

        System.out.print("Enter lecture code this assignment belongs to: ");
        belongedLecture = input.nextLine().trim();

        System.out.print("Enter title: ");
        title = input.nextLine();

        System.out.print("Enter description: ");
        description = input.nextLine();

        System.out.print("Enter number of attached files: ");
        int fileCount = 0;
        try {
            fileCount = Integer.parseInt(input.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid number...");
        }
        attachedFiles = new LinkedList<>();
        for (int i = 0; i < fileCount; i++) {
            System.out.print("Enter file path " + (i + 1) + ": ");
            attachedFiles.add(input.nextLine());
        }

        System.out.print("Enter grade weight between 0 and 1 ( e.g., '0.2'): ");
        try {
            gradeWeight = Float.parseFloat(input.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid grade weight...");
        }

        System.out.print("Enter due date and time (e.g., '2025-09-15 23:59'): ");
        dueDate = input.nextLine().trim();

        String response = assignmentController.createAssignment(
                belongedLecture, title, description, attachedFiles, gradeWeight, dueDate
        );

        if (response.equals("Success")) {
            System.out.println("Assignment created successfully!");
        } else {
            System.out.println("Error: " + response);
        }

    }

    public static void handleDeleteAssignment() {
        System.out.print("Pls enter the ID of the assignment you want to delete: ");
        int assignemntID = -1;
        try {
            assignemntID = Integer.parseInt(input.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid ID...");
        }
        String response = assignmentController.deleteAssignment(assignemntID);
        if (response.equals("Success")) {
            System.out.println("Assignment deleted successfully!");
        } else {
            System.out.println("Error: " + response);
        }
    }

    // Helpers

    public static void turnback_or_exit_screen() {
        System.out.println("Do you want to turn back page or exit the system ?");
        System.out.println("1.Turn back");
        System.out.println("2.Exit");
        System.out.print("--> :");
    }

    public static void turnback_Or_exit_Process() {
        backpage = Integer.parseInt(input.nextLine());
        if (0 < backpage && backpage < 3) {

        } else {
            while (!backpagevaild) {
                System.out.print("Please enter a valid option:");
                backpage = Integer.parseInt(input.nextLine());
                if (0 < backpage && backpage < 3) {
                    backpagevaild = true;
                }
            }
        }
        if (backpage == 1) {// turn back
            displayStudentMenu();
        } else if (backpage == 2) {
            user_interface_active = true;
            logout();
        }
    }

    public static void turnback_or_exit_ProcessforLecturer() {
        backpage = Integer.parseInt(input.nextLine());
        if (0 < backpage && backpage < 3) {

        } else {
            while (!backpagevaild) {
                System.out.print("Please enter a valid option:");
                backpage = Integer.parseInt(input.nextLine());
                if (0 < backpage && backpage < 3) {
                    backpagevaild = true;
                }
            }
        }
        if (backpage == 1) {// turn back
            displayLecturerMenu();
        } else if (backpage == 2) {
            user_interface_active = true;
            logout();
        }
    }

    public static void turnback_or_exit_ProcessforStudentaffairs() {
        backpage = Integer.parseInt(input.nextLine());
        if (0 < backpage && backpage < 3) {

        } else {
            while (!backpagevaild) {
                System.out.print("Please enter a valid option:");
                backpage = Integer.parseInt(input.nextLine());
                if (0 < backpage && backpage < 3) {
                    backpagevaild = true;
                }
            }
        }
        if (backpage == 1) {// turn back
            displayStudentMenu();
        } else if (backpage == 2) {
            user_interface_active = true;
            logout();
        }
    }

    public static void clearScreen() {
        for (int i = 0; i < 50; i++) {
            System.out.println(" ");
        }
    }

}