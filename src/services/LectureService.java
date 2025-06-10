package services;
import models.LectureModel;
import models.user.LecturerModel;
import repositories.GradeDictionaryRepository;
import repositories.LectureRepository;
import repositories.user.LecturerRepository;

import java.util.Scanner;

public class LectureService {
    Scanner sc = new Scanner(System.in);
    LectureRepository lectureRepository ;
    LecturerRepository lecturerRepository ;
    GradeDictionaryRepository gradeDictionaryRepository ;
    LectureModel lecture ;

    public LectureService(LectureRepository lectureRepository, LecturerRepository lecturerRepository, GradeDictionaryRepository gradeDictionaryRepository) {
        this.lectureRepository = lectureRepository;
        this.lecturerRepository = lecturerRepository;
        this.gradeDictionaryRepository = gradeDictionaryRepository;
    }
    public void getSyllabusByID() {
        System.out.println("Enter the lecture id you would like to look for syllabus: ");
        int lectureId = sc.nextInt();
        lecture = findLecture(lectureId);
        System.out.println("The syllabus is : ");
        System.out.println(lecture.getSyllabus());
    }
    public void getSyllabusByCode() {
        System.out.println("Enter the lecture code you would like to look for syllabus: ");
        String lectureCode = sc.nextLine();
        LectureModel lecture;
        lecture = lectureRepository.getLectureByCode(lectureCode);
        System.out.println("The syllabus is : ");
        System.out.println(lecture.getSyllabus());

    }

    public void getLecturerByLectureID() {
        //find lecture
        System.out.println("Enter the lecture id you would like to look for lecturer of the lecture: ");
        int lectureId = sc.nextInt();
        lecture = findLecture(lectureId);;
        //find lecturer
        int lecturerId = lecture.getLecturerID();
        LecturerModel lecturer;
        lecturer = lecturerRepository.getLecturer(lecturerId);
        System.out.println("The lecturer of the lecture is : ");
        System.out.println(lecturer.getFullName());
        System.out.println("See details about the lecturer Y/N ? ");
        String choice = sc.nextLine();
        if (choice.equalsIgnoreCase("y")) {
            System.out.println("Department: "+lecturer.getDepartment());
            System.out.println("Phone Number: "+lecturer.getPhoneNumber());
            System.out.println("Email: "+lecturer.getEmail());
            System.out.println("Office hours: "+lecturer.getOfficeHours());
        }
        else {

        }
    }
    public void getResourcesByLectureID() {
        System.out.println("Enter the lecture id you would like to look for resources: ");
        int lectureId = sc.nextInt();
        lecture = findLecture(lectureId);
        System.out.println("The resources of this lecture is : ");
        System.out.println(lecture.getResources().toString());
    }
    public void getGradingCriteriaByLectureID() {
        System.out.println("Enter the lecture id you would like to look for grading criteria: ");
        int lectureId = sc.nextInt();
        lecture = findLecture(lectureId);
        System.out.println("The grading criteria of this lecture is : ");
        System.out.println(gradeDictionaryRepository.getGradeDictionariesByLectureId(lectureId));// sıkıntılı bakcaz buraya

    }
    public LectureModel findLecture(int lectureId) {
        LectureModel lecture;
        lecture = lectureRepository.getLectureById(lectureId);
        return lecture;
    }


}
