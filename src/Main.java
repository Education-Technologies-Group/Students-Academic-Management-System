
import services.AnnouncementService;
import services.AssigmentService;
import java.util.Scanner;

public class Main {

    static Scanner input = new Scanner(System.in);
    static String password ;
    static String email;
    static boolean exitbuttonpressed = false;
    static int option2;
    static boolean option2vaild ;
    static boolean backpagevaild ;
    static int backpage ;

    public static void main(String[] args) {
        welcome_screen();
        while (!exitbuttonpressed) {

            String option = input.next();
            System.out.println();

            if (option.equals("1")) {
                login();

                if (validLoginforStudent(email, password)) {
                    System.out.println("Login Successful ...");
                    validlog_screen();
                    option2 = input.nextInt();
                    if ( 0 < option2 && option2 < 7) {

                    }else {
                        option2vaild = false;
                        while (!option2vaild) {
                            System.out.print("Please enter a valid option:");
                            option2 = input.nextInt();
                            if (0 < option2 && option2 < 7) {
                                option2vaild = true;
                            }
                        }
                    }
                    // option valid
                    if (option2 == 1) {
                        System.out.println(" Your Lectures:");
                        //STUDENT CONTROLLER SİGNED LECTURE PART

                        turnback_or_exit_screen();
                        turnback_Or_exit_Process();
                    }
                    else if (option2 == 2) {
                        System.out.println("Please enter a lecture code for view your lecture grade :");
                        //STUDENT CONTROLLER GRADE PART

                        turnback_or_exit_screen();
                        turnback_Or_exit_Process();
                    }
                    else if (option2 == 3) {
                        System.out.println("Please enter a lecture code for join your lecture session :");
                        //LİVE SESSİON CONTROLLER PART

                    }
                    else if (option2 == 4) {
                        System.out.println("---- Your Assignments ---- : ");

                        turnback_or_exit_screen();
                        turnback_Or_exit_Process();
                    }
                    else if (option2 == 5) {
                        System.out.println("---- Your Announcements ---- :");

                        turnback_or_exit_screen();
                        turnback_Or_exit_Process();
                    }
                    else if (option2 == 6) {
                        exitbuttonpressed = true;
                        logout(exitbuttonpressed);
                    }
                } else {

                    String option3 ;
                    boolean invalid = false;

                    while (!validLoginforStudent(email, password)) {

                        if (!invalid) {
                            System.out.println("Wrong password or email address!");
                            System.out.println("1. Try again");
                            System.out.println("2. Exit");
                        }
                        invalid = false;

                        System.out.print("--> :");

                        option3 = input.next();
                        System.out.println();

                        if (option3.equals("1")) {
                            login();
                        } else if (option3.equals("2")) {
                            exitbuttonpressed = true;
                            logout(exitbuttonpressed);
                            break;
                        } else {
                            invalid = true;
                            System.out.println("Invalid option");
                        }
                    }
                    validLoginProcessforStudent();
                }


            }
            else if (option.equals("2")) {
                login();
                if (validLoginforLecturer(email, password)) {
                    System.out.println("Login Successful ...");
                    validlog_screen_lecturer();
                    option2 = input.nextInt();
                    if ( 0 < option2 && option2 < 11) {

                    }else {
                        option2vaild = false;
                        while (!option2vaild) {
                            System.out.print("Please enter a valid option:");
                            option2 = input.nextInt();
                            if (0 < option2 && option2 < 11) {
                                option2vaild = true;
                            }
                        }
                    }
                    //option valid
                    if (option2 == 1) {
                        System.out.println("Announcement added successfully ...:");
                        //LECTURER CONTROLLER KISMI
                        turnback_or_exit_screen();
                        turnback_or_exit_ProcessforLecturer();

                    }
                    else if (option2 == 2) {
                        System.out.println("Announcement deleted successfully ...:");
                        //LECTURER CONTROLLER
                        turnback_or_exit_screen();
                        turnback_or_exit_ProcessforLecturer();
                    }
                    else if (option2 == 3) {
                        System.out.println("Assignment added successfully ...:");
                        //LECTURER CONTROLLER
                        turnback_or_exit_screen();
                        turnback_or_exit_ProcessforLecturer();
                    }
                    else if (option2 == 4) {
                        System.out.println("Assignment deleted successfully ...:");
                        //LECTURER CONTROLLER
                        turnback_or_exit_screen();
                        turnback_or_exit_ProcessforLecturer();
                    }
                    else if (option2 == 5) {
                        System.out.println("Grades Edited successfully ...:");
                        //LECTURER CONTROLLER
                        turnback_or_exit_screen();
                        turnback_or_exit_ProcessforLecturer();

                    }
                    else if (option2 == 6) {
                        System.out.println("Live session opened successfully ...:");
                        //LECTURER CONTROLLER
                        turnback_or_exit_screen();
                        turnback_or_exit_ProcessforLecturer();
                    }
                    else if (option2 == 7) {
                        System.out.println("Your Tickets :");
                        turnback_or_exit_screen();
                        turnback_or_exit_ProcessforLecturer();
                    }
                    else if (option2 == 8) {
                        System.out.println("Assignment edited successfully ...:");
                        //Assignment service de yazıldı title ve due date editleme
                        //LECTURE CONTROLLER
                        turnback_or_exit_screen();
                        turnback_or_exit_ProcessforLecturer();
                    }
                    else if (option2 == 9) {
                        System.out.println("Announcement edited successfully ...:");
                        //Announcement service de yazılı
                        //LECTURE CONTROLLER
                        turnback_or_exit_screen();
                        turnback_or_exit_ProcessforLecturer();
                    }
                    else if (option2 == 10) {
                        exitbuttonpressed = true;
                        logout(exitbuttonpressed);
                    }


                }else {
                    String option3 ;
                    boolean invalid = false;

                    while (!validLoginforLecturer(email, password)) {

                        if (!invalid) {
                            System.out.println("Wrong password or email address!");
                            System.out.println("1. Try again");
                            System.out.println("2. Exit");
                        }
                        invalid = false;

                        System.out.print("--> :");

                        option3 = input.next();
                        System.out.println();

                        if (option3.equals("1")) {
                            login();
                        } else if (option3.equals("2")) {
                            exitbuttonpressed = true;
                            logout(exitbuttonpressed);
                            break;
                        } else {
                            invalid = true;
                            System.out.println("Invalid option");
                        }
                    }

                    validloginProcessforLecturer();
                }


            }
            else if (option.equals("3")) {
                login();
                if (validLoginforStudentaffairs(email, password)) {
                    System.out.println("Login Successful ...");
                    validlog_screen_studentaffairs();
                    option2 = input.nextInt();
                    if ( 0 < option2 && option2 < 3) {

                    }else {
                        option2vaild = false;
                        while (!option2vaild) {
                            System.out.print("Please enter a valid option:");
                            option2 = input.nextInt();
                            if (0 < option2 && option2 < 3) {
                                option2vaild = true;
                            }
                        }
                    }
                    //option valid
                    if (option2 == 1) {
                        System.out.println("Ticket requests are answered successfully ...:");
                        //S.AFFAİRS CONTROLLER
                        turnback_or_exit_screen();
                        turnback_or_exit_ProcessforStudentaffairs();
                    }
                    else if (option2 == 2) {
                        System.out.println("Announcement added successfully ...:");
                        //S.AFFAİRS CONTROLLER
                        turnback_or_exit_screen();
                        turnback_or_exit_ProcessforStudentaffairs();

                    }
                }else {
                    String option3 ;
                    boolean invalid = false;

                    while (!validLoginforLecturer(email, password)) {

                        if (!invalid) {
                            System.out.println("Wrong password or email address!");
                            System.out.println("1. Try again");
                            System.out.println("2. Exit");
                        }
                        invalid = false;

                        System.out.print("--> :");

                        option3 = input.next();
                        System.out.println();

                        if (option3.equals("1")) {
                            login();
                        } else if (option3.equals("2")) {
                            exitbuttonpressed = true;
                            logout(exitbuttonpressed);
                            break;
                        } else {
                            invalid = true;
                            System.out.println("Invalid option");
                        }
                    }

                    validLoginProcessforStudentaffairs();
                }


            } else if (option.equals("4")) {
                exitbuttonpressed = true;
                logout(exitbuttonpressed);
                break;

            } else {
                System.out.println("Invalid option try again ...");
                System.out.print("--> :");
            }



        }
    }
    public static boolean validLoginforStudent(String email, String password) {
        //login controller da kontrol edilmeli
        return true;
    }
    public static boolean validLoginforLecturer(String email, String password) {
        //login controller da kontrol edilmeli
        return true;
    }
    public static boolean validLoginforStudentaffairs(String email, String password) {
        //login controller da kontrol edilmeli
        return true;
    }
    public static void login(){
        System.out.print("Please enter your email :");
        email = input.next();
        System.out.print("Please enter your password :");
        password = input.next();
    }
    public static void logout(boolean exitbuttonpressed){
        if(exitbuttonpressed){
            System.out.println("Successfully logged out from SAMS ...");
            System.out.println("Goodbye!");
        }
    }
    public static void screen_Clear(){

        for (int i = 0 ; i < 50; i++){
            System.out.println(" ");
        }
    }
    public static void welcome_screen(){
        System.out.println("Welcome to SAMS !");
        System.out.println("Please choose one of the following options:");
        System.out.println("1. Login for student");
        System.out.println("2. Login for lecturer");
        System.out.println("3. Login for student affairs");
        System.out.println("4. Exit");
        System.out.print("--> :");
    }
    public static void validlog_screen_lecturer(){
        System.out.println("Please select the operation you want to perform.");
        System.out.println("1.Add Announcement");
        System.out.println("2.Delete Announcement");
        System.out.println("3.Add Assignment");
        System.out.println("4.Delete Assignment");
        System.out.println("5.Edit Grades");
        System.out.println("6.Open Live Session");
        System.out.println("7.View Tickets");
        System.out.println("8.Edit Assignment");
        System.out.println("9.Edit Announcement");
        System.out.println("10.Exit");
        System.out.println("-->");


    }
    public static void validlog_screen_studentaffairs(){
        System.out.println("Please select the operation you want to perform.");
        System.out.println("1.Ticket Requests");
        System.out.println("2.Add Announcement");
    }
    public static void validlog_screen(){
        System.out.println("Please select the operation you want to perform.");
        System.out.println("1. View Lectures");
        System.out.println("2. View Grades");
        System.out.println("3. Join live session");
        System.out.println("4. View Assignments");
        System.out.println("5. View Announcements");
        System.out.println("6. Exit");
        System.out.print("--> :");
    }
    public static void turnback_or_exit_screen(){
        System.out.println("Do you want to turn back page or exit the system ?");
        System.out.println("1.Turn back");
        System.out.println("2.Exit");
        System.out.print("--> :");
    }
    public static void turnback_Or_exit_Process(){
        backpage = input.nextInt();
        if (0< backpage && backpage < 3) {

        }
        else {
            while (!backpagevaild) {
                System.out.print("Please enter a valid option:");
                backpage = input.nextInt();
                if (0 < backpage && backpage < 3) {
                    backpagevaild = true;
                }
            }
        }
        if (backpage == 1) {// turn back
            validLoginProcessforStudent();
        }
        else if (backpage == 2) {
            exitbuttonpressed = true;
            logout(exitbuttonpressed);
        }
    }
    public static void turnback_or_exit_ProcessforLecturer(){
        backpage = input.nextInt();
        if (0< backpage && backpage < 3) {

        }
        else {
            while (!backpagevaild) {
                System.out.print("Please enter a valid option:");
                backpage = input.nextInt();
                if (0 < backpage && backpage < 3) {
                    backpagevaild = true;
                }
            }
        }
        if (backpage == 1) {// turn back
            validloginProcessforLecturer();
        }
        else if (backpage == 2) {
            exitbuttonpressed = true;
            logout(exitbuttonpressed);
        }
    }
    public static void turnback_or_exit_ProcessforStudentaffairs(){
        backpage = input.nextInt();
        if (0< backpage && backpage < 3) {

        }
        else {
            while (!backpagevaild) {
                System.out.print("Please enter a valid option:");
                backpage = input.nextInt();
                if (0 < backpage && backpage < 3) {
                    backpagevaild = true;
                }
            }
        }
        if (backpage == 1) {// turn back
            validLoginProcessforStudentaffairs();
        }
        else if (backpage == 2) {
            exitbuttonpressed = true;
            logout(exitbuttonpressed);
        }
    }


    public static void validloginProcessforLecturer(){
        if (validLoginforLecturer(email, password)) {
            validlog_screen_lecturer();
            option2 = input.nextInt();
            if (0 < option2 && option2 < 11) {

            } else {
                option2vaild = false;
                while (!option2vaild) {
                    System.out.print("Please enter a valid option:");
                    option2 = input.nextInt();
                    if (0 < option2 && option2 < 11) {
                        option2vaild = true;
                    }
                }
            }
            //option valid
            if (option2 == 1) {
                System.out.println("Announcement added successfully ...");
                //LECTURER CONTROLLER KISMI
                turnback_or_exit_screen();
                turnback_or_exit_ProcessforLecturer();

            } else if (option2 == 2) {
                System.out.println("Announcement deleted successfully ...");
                //LECTURER CONTROLLER
                turnback_or_exit_screen();
                turnback_or_exit_ProcessforLecturer();
            } else if (option2 == 3) {
                System.out.println("Assignment added successfully ...");
                //LECTURER CONTROLLER
                turnback_or_exit_screen();
                turnback_or_exit_ProcessforLecturer();
            } else if (option2 == 4) {
                System.out.println("Assignment deleted successfully ...");
                //LECTURER CONTROLLER
                turnback_or_exit_screen();
                turnback_or_exit_ProcessforLecturer();
            } else if (option2 == 5) {
                System.out.println("Grades Edited successfully ...");
                //LECTURER CONTROLLER
                turnback_or_exit_screen();
                turnback_or_exit_ProcessforLecturer();

            } else if (option2 == 6) {
                System.out.println("Live session opened successfully ...");
                //LECTURER CONTROLLER
                turnback_or_exit_screen();
                turnback_or_exit_ProcessforLecturer();
            } else if (option2 == 7) {
                System.out.println("Your Tickets :");
                turnback_or_exit_screen();
                turnback_or_exit_ProcessforLecturer();
            } else if (option2 == 8) {
                System.out.println("Assignment edited successfully ...");
                //Assignment service de yazıldı title ve due date editleme
                //LECTURE CONTROLLER
                turnback_or_exit_screen();
                turnback_or_exit_ProcessforLecturer();
            } else if (option2 == 9) {
                System.out.println("Announcement edited successfully ...");
                //Announcement service de yazılı
                //LECTURE CONTROLLER
                turnback_or_exit_screen();
                turnback_or_exit_ProcessforLecturer();
            } else if (option2 == 10) {
                exitbuttonpressed = true;
                logout(exitbuttonpressed);
            }
        }

    }
    public static void validLoginProcessforStudentaffairs(){
        if (validLoginforStudentaffairs(email, password)) {
            validlog_screen_studentaffairs();
            option2 = input.nextInt();
            if (0 < option2 && option2 < 3) {

            } else {
                option2vaild = false;
                while (!option2vaild) {
                    System.out.print("Please enter a valid option:");
                    option2 = input.nextInt();
                    if (0 < option2 && option2 < 3) {
                        option2vaild = true;
                    }
                }
            }
            //option valid
            if (option2 == 1) {
                System.out.println("Ticket requests are answered successfully ...:");
                //S.AFFAİRS CONTROLLER
                turnback_or_exit_screen();
                turnback_or_exit_ProcessforStudentaffairs();
            } else if (option2 == 2) {
                System.out.println("Announcement added successfully ...:");
                //S.AFFAİRS CONTROLLER
                turnback_or_exit_screen();
                turnback_or_exit_ProcessforStudentaffairs();

            }
        }
    }

    public static void validLoginProcessforStudent(){
        if (validLoginforStudent(email, password)) {
            validlog_screen();
            option2 = input.nextInt();
            if (0 < option2 && option2 < 7) {

            } else {
                option2vaild = false;
                while (!option2vaild) {
                    System.out.print("Please enter a valid option:");
                    option2 = input.nextInt();
                    if (0 < option2 && option2 < 7) {
                        option2vaild = true;
                    }
                }
            }
            // option valid
            if (option2 == 1) {
                System.out.println("---- Your Lectures ---- :");
                //STUDENT SERVİCE SİGNED LECTURE PART
                turnback_or_exit_screen();
                turnback_Or_exit_Process();
            } else if (option2 == 2) {
                System.out.println("Please enter a lecture code for view your lecture grade :");
                //STUDENT SERVİCE GRADE PART
                turnback_or_exit_screen();
                turnback_Or_exit_Process();
            } else if (option2 == 3) {
                System.out.println("Please enter a lecture code for join your lecture session :");
                turnback_or_exit_screen();
                turnback_Or_exit_Process();
            } else if (option2 == 4) {
                System.out.println("Your Assignments :  :");
                //assigmentService.view_All_assigments();
                turnback_or_exit_screen();
                turnback_Or_exit_Process();
            } else if (option2 == 5) {
                System.out.println("Your Announcements :");
                //announcementService.view_All_announcements();
                turnback_or_exit_screen();
                turnback_Or_exit_Process();
            } else if (option2 == 6) {
                exitbuttonpressed = true;
                logout(exitbuttonpressed);
            }
        }
    }
}