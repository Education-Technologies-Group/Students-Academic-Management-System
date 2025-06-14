package services;
import models.AnnouncementModel;
import repositories.AnnouncementRepository;
import java.util.Scanner;
public class AnnouncementService{
    Scanner sc = new Scanner(System.in);

    AnnouncementRepository announcementRepo;
    AnnouncementService(AnnouncementRepository announcementRepo){
        this.announcementRepo = announcementRepo;
    }

    public void editAnnouncement(){
        boolean isvalidchoice = true;
        System.out.println("Please enter the id of the assigment you would like to edit");

        int id = sc.nextInt();
        System.out.println("Please enter the part of the assigment you would like to edit");
        System.out.println("1 : Title ");
        System.out.println("2 : Description ");
        System.out.println("3 : Expiration Date ");

        String choice = sc.nextLine() ;

            if(choice.equals("1") || choice.equals("2") || choice.equals("3")) {

            }
            else {
                while(isvalidchoice){
                    System.out.println("Please enter valid choice : ");
                    choice = sc.nextLine() ;
                    if(choice.equals("1") || choice.equals("2") || choice.equals("3")) {
                        isvalidchoice = false;
                    }
                }
            }

        AnnouncementModel editedAnnouncement;
        editedAnnouncement = announcementRepo.getAnnouncementByID(id);
         String oldTitle = editedAnnouncement.getAnnouncement_title();

        if(choice.equals("1")){
            System.out.println("Please enter the new title of the assigment :");
            String title = sc.nextLine();
            editedAnnouncement.setAnnouncement_title(title);
            System.out.println("Changes applied ... ");
        }
        else if(choice.equals("2")){
            System.out.println("Please enter the new description of the assigment :");
            String description = sc.nextLine();
            editedAnnouncement.setAnnouncement_description(description);
            System.out.println("Changes applied ... ");
        }
        else {
            System.out.println("Please enter the new expiration date of the assigment :");
            String expirationDate = sc.nextLine();
            editedAnnouncement.setExpirationDate(expirationDate);
            System.out.println("Changes applied ... ");
        }

        System.out.println("Do you want to save the changes Y/N ?");
        String save = sc.nextLine();

        if(save.equals("Y")){
            System.out.println("Saved Successfully ... ");
        }
        else {
            System.out.println("Saved Failed... ");
            editedAnnouncement.setAnnouncement_title(oldTitle);
        }
    }

    public void view_All_announcements(){
        AnnouncementModel announcement;
        int id = 1;
        while (announcementRepo.getAnnouncementByID(id) != null) {
            announcement = announcementRepo.getAnnouncementByID(id);
            System.out.println("---- AnnouncementID :" + announcement.getAnnouncement_id() +"----");
            System.out.println("Title : " + announcement.getAnnouncement_title());
            System.out.println("Description : " + announcement.getAnnouncement_description());
            System.out.println("Expiration Date : " + announcement.getExpirationDate());
            id ++;
        }
    }


}
