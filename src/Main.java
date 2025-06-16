
import controllers.*;
import models.TicketModel;
import models.user.UserModel;
import repositories.*;
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
    public static TicketController ticketController;

    // Services
    public static UserService userService;
    public static LectureService lectureService;
    public static LiveSessionService liveSessionService;
    public static AssigmentService assigmentService;
    public static AnnouncementService announcementService;
    public static TicketService ticketService;

    // Repos
    public static StudentRepository studentRepository;
    public static LecturerRepository lecturerRepository;
    public static StudentAffairsRepository studentAffairsRepository;

    public static LectureRepository lectureRepository;
    public static LiveSessionRepository liveSessionRepository;
    public static AssignmentRepository assignmentRepository;
    public static AnnouncementRepository announcementRepository;
    public static TicketRepository ticketRepository;


    static Scanner input = new Scanner(System.in);
    static String user_role = "None";
    static boolean user_interface_active = true;

    public static void main(String[] args) throws IOException {

        initializeSystem();
        System.out.println("Welcome to SAMS !");
        while (user_interface_active) {
            switch (user_role) {
                // Student Menu
                case "Student" -> {
                    displayStudentMenu();
                    int user_input = Integer.parseInt(input.nextLine());

                    switch (user_input) {
                        case 1 -> displayStudentLecturers();
                        case 2 -> displayStudentGrades();
                        case 3 -> handeUserLiveSessionJoin();
                        case 4 -> displayStudentAssignments();
                        case 5 -> displayStudentAnnouncements();
                        case 6 -> logout();
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
                        case 7 -> handleEditGrades();
                        case 8 -> handleSessionCreation();
                        case 9 -> handleTicketCreation();
                        case 10 -> displayTickets();
                        // case 11 -> ;
                        // case 12 -> ;
                        case 13 -> logout();
                        default -> System.out.println("Invalid option try again ...");
                    }
                }


                // Student Affairs Menu
                case "StudentAffairs" -> {
                    displayStudentAffairsMenu();
                    int user_input = Integer.parseInt(input.nextLine());
                    switch (user_input) {
                        case 1 -> displayTickets();
                        case 2 -> handleTicketSolve();
                        case 3 -> handleAddAnnouncement();
                        case 4 -> logout();
                        default -> System.out.println("Invalid option try again ...");
                    }
                }

                // Admin Menu
                case "Admin" -> displayAdminMenu();

                // No User Currently Logged In
                default -> {
                    displayWelcomeScreen();
                    String auth_selection = input.nextLine();
                    // User Selected Login
                    switch (auth_selection) {
                        case "1" -> login();
                        case "2" -> logout();
                        default -> {
                            System.out.println("Invalid option try again ...");
                            System.out.print("--> :");
                        }
                    }
                }
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
        ticketRepository = new TicketRepository();

        // Initialize Services
        userService = new UserService(studentRepository, lecturerRepository, studentAffairsRepository, lectureRepository);
        lectureService = new LectureService(lecturerRepository, lectureRepository);
        liveSessionService = new LiveSessionService(liveSessionRepository, lectureRepository, studentRepository);
        assigmentService = new AssigmentService(assignmentRepository, lectureRepository, studentRepository);
        announcementService = new AnnouncementService(announcementRepository, lectureRepository);
        ticketService = new TicketService(ticketRepository);

        // Initialize Controllers
        userController = new UserController(userService, lectureService);
        lectureController = new LectureController(lectureService);
        liveSessionController = new LiveSessionController(liveSessionService, lectureService);
        assignmentController = new AssignmentController(assigmentService, lectureService);
        announcementController = new AnnouncementController(announcementService, lectureService);
        ticketController = new TicketController(ticketService);
    }

    // Auth
    public static void login() {
        System.out.print("Please enter your email :");
        String email = input.nextLine();
        System.out.print("Please enter your password :");
        String password = input.nextLine();
        String response = userController.login(email, password);
        switch (response) {
            case "Student" -> user_role = "Student";
            case "Lecturer" -> user_role = "Lecturer";
            case "StudentAffairs" -> user_role = "StudentAffairs";
            case "Admin" -> user_role = "Admin";
            default -> user_role = "None";
        }
    }

    public static void logout() {
        user_interface_active = false;
        System.out.println("Successfully logged out from SAMS ...");
        System.out.println("Goodbye!");
    }

    // User Menus
    public static void displayWelcomeScreen() {

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
        System.out.println("9.Create Ticket");
        System.out.println("10.View Tickets");
        System.out.println("11.Edit Assignment");
        System.out.println("12.Edit Announcement");
        System.out.println("13.Exit");
        System.out.print("-->");


    }

    public static void displayStudentAffairsMenu() {
        System.out.println("Please select the operation you want to perform.");
        System.out.println("1.Ticket Requests");
        System.out.println("2.Mark Ticket as Solved");
        System.out.println("3.Add Announcement");
        System.out.println("4.Exit");
        System.out.print("-->");
    }

    public static void displayAdminMenu() {
        System.out.println("Please select the operation you want to perform.");
        System.out.println("1. Create User");
        System.out.println("2. Create Lecture");
        System.out.println("3. Create User");
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

    public static void handleEditGrades() {
        System.out.print("Enter the student ID you want to update the grade for: ");
        int studentID = -1;
        try {
            studentID = Integer.parseInt(input.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid Student ID...");
        }
        LinkedList<String> lecturer_grades = userController.getStudentGradesByLecturer(studentID);
        if (lecturer_grades == null) {
            System.out.println("Student not Found...");
            return;
        }
        if (lecturer_grades.isEmpty()) {
            System.out.println("You don't have any grades to update related to this student...");
        }
        for (String lecturer_grade : lecturer_grades) {
            System.out.println(lecturer_grade);
        }
        System.out.print("Enter the Lesson Code you want to update the grade for: ");
        String lessonCode = input.nextLine().trim();
        int new_grade = -1;
        try {
            new_grade = Integer.parseInt(input.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid Student ID...");
        }
        String response = userController.updateGrade(studentID, lessonCode, new_grade);
        if (response.equals("Success")) {
            System.out.println("Grade updated successfully!");
        } else {
            System.out.println("Error: " + response);
        }
    }

    public static void handleSessionCreation() {
        // Necessary Inputs for Creating Live Session
        String title;
        String description;
        String scheduledDate;
        String recordPath;
        String target_lecture;

        System.out.println("You can Skip any of the Inputs by Pressing Enter where applicable...");

        System.out.print("Enter title: ");
        title = input.nextLine();

        System.out.print("Enter description: ");
        description = input.nextLine();

        System.out.print("Enter scheduled date and time (e.g., '2025-06-20 18:30'): ");
        scheduledDate = input.nextLine().trim();

        System.out.print("Enter record file path (or leave blank if none): ");
        recordPath = input.nextLine().trim();

        System.out.print("Enter target lecture code to invite students: ");
        target_lecture = input.nextLine().trim();


        String response = liveSessionController.createLiveSession(title, description, scheduledDate
                , recordPath, target_lecture);

        if (response.equals("Success")) {
            System.out.println("Live session created successfully!");
        } else {
            System.out.println("Error: " + response);
        }

    }

    public static void displayTickets() {
        LinkedList<String> tickets = ticketController.getTickets();
        if (tickets == null) {
            System.out.println("Something went wrong...");
            return;
        }
        if (tickets.isEmpty()) {
            System.out.println("You don't have any tickets ...");
        }
        for (String ticket : tickets) {
            System.out.println(ticket);
        }
    }

    public static void handleTicketCreation() {
        // Necessary Inputs for Creating Ticket
        String title;
        String description;

        System.out.println("You can Skip any of the Inputs by Pressing Enter where applicable...");


        System.out.print("Enter title: ");
        title = input.nextLine();

        System.out.print("Enter description: ");
        description = input.nextLine();


        String response = ticketController.createTicket(title, description);

        // Response handling
        if (response.equals("Success")) {
            System.out.println("Ticket created successfully!");
        } else {
            System.out.println("Error: " + response);
        }


    }

    // StudentAffairs Menu Operations
    public static void handleTicketSolve() {
        System.out.print("Enter the ID of ticket you want to mark as solved: ");
        int  ticketID = Integer.parseInt(input.nextLine());

        try {
            ticketID = Integer.parseInt(input.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid ID...");
        }
        String response = ticketController.markTicketSolved(ticketID);
        if (response.equals("Success")) {
            System.out.println("Ticket Marked as Solved successfully!");
        } else {
            System.out.println("Error: " + response);
        }
    }


    // Lecturer & Student Affairs Common Operation
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


    // Helpers
    public static void clearScreen() {
        for (int i = 0; i < 50; i++) {
            System.out.println(" ");
        }
    }

}