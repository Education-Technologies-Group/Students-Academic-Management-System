package models;

import java.util.LinkedList;

public class LectureModel {
    private int id;
    private String lectureCode ;
    private String lectureName ;
    private String syllabus;
    private int lecturerid ;
    private LinkedList<String> resources ;


    public LectureModel(int id,String lectureCode, String lectureName, String syllabus, int lecturerid, LinkedList<String> resources) {
        this.id = id;
        this.lectureCode = lectureCode;
        this.lectureName = lectureName;
        this.syllabus = syllabus;
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

    public String getSyllabus() {
        return syllabus;
    }

    public void setSyllabus(String syllabus) {
        this.syllabus = syllabus;
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
