package models;
import models.user.LecturerModel;

import java.util.LinkedList;
import java.util.List;

public class LectureModel {
    private int id;
    private String lectureCode ;
    private String lectureName ;
    private LinkedList<String> syllabus;
    private LinkedList<String> gradingDictianory;
    private int lecturerid ;
    private LinkedList<String> resources ;

    public LectureModel(int id,String lectureCode, String lectureName, LinkedList<String> syllabus,LinkedList<String> gradingDictianory, int lecturerid, LinkedList<String> resources) {
        this.id = id;
        this.lectureCode = lectureCode;
        this.lectureName = lectureName;
        this.syllabus = syllabus;
        this.gradingDictianory = gradingDictianory;
        this.lecturerid = lecturerid;
        this.resources = resources;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLectureCode() {
        return lectureCode;
    }

    public void setLectureCode(String lectureCode) {
        this.lectureCode = lectureCode;
    }

    public String getLectureName() {
        return lectureName;
    }

    public void setLectureName(String lectureName) {
        this.lectureName = lectureName;
    }

    public LinkedList<String> getSyllabus() {
        return syllabus;
    }

    public void setSyllabus(LinkedList<String> syllabus) {
        this.syllabus = syllabus;
    }

    public LinkedList<String> getGradingDictianory() {
        return gradingDictianory;
    }

    public void setGradingDictianory(LinkedList<String> gradingDictianory) {
        this.gradingDictianory = gradingDictianory;
    }

    public int getLecturerID() {
        return lecturerid;
    }

    public void setLecturerID(int lecturerid) {
        this.lecturerid = lecturerid;
    }

    public LinkedList<String> getResources() {
        return resources;
    }

    public void setResources(LinkedList<String> resources) {
        this.resources = resources;
    }
}
