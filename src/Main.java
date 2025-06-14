import javax.swing.plaf.PanelUI;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

   static Scanner input = new Scanner(System.in);
    static String password = input.next();
    static String email = input.next();
    static boolean exitbuttonpressed = false;

    public static void main(String[] args) {


        welcome_screen();
        while (!exitbuttonpressed) {

            int option = input.nextInt();
            System.out.println();

            if (option == 1) {
                login();
                if (validLogin(email, password)) {
                    System.out.println("Successfully logged in to SAMS ...");
                    System.out.println("Please select the operation you want to perform.");
                    System.out.println("1. View Lectures");
                    System.out.println("2. View Grades");
                    System.out.println("3. Join live session");
                    System.out.println("4. View Assignments");
                    System.out.println("5. View Announcements");
                    System.out.println("6. Exit");
                } else {
                    while (!validLogin(email, password)) {
                        System.out.println("Wrong password or email address!");
                        System.out.println("1. Try again");
                        System.out.println("2. Exit");
                        if (option == 1) {
                            login();
                        } else if (option == 2) {
                            exitbuttonpressed = true;
                            logout(exitbuttonpressed);
                            break;
                        } else if (option == 3) {
                            System.out.println("Invalid option");
                        }

                    }
                }


            } else if (option == 2) {


            } else if (option == 3) {

            } else if (option == 4) {
                exitbuttonpressed = true;
                logout(exitbuttonpressed);
                break;

            } else {
                System.out.println("Invalid option try again ...");
            }
        }
    }
    public static boolean validLogin(String email, String password) {
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
    public void screen_Clear(){
        for (int i = 0 ; i < 100 ; i++){
            System.out.print("                                                                                    ");
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
}