package models;

import java.util.List;

public class LectureModel {
    private int id ;
    private String lectureCode ;
    private String lectureName ;
    private List<String> syllabus;
    private List<String> gradingDictianory;
    private String lecturer ;// tür değiştirilecek
    private List<String> resources ;
    public LectureModel(String lectureCode, String lectureName, String lecturer, List<String> resources) {
        this.lectureCode = lectureCode;
        this.lectureName = lectureName;
        this.lecturer = lecturer;
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

    public List<String> getSyllabus() {
        return syllabus;
    }

    public void setSyllabus(List<String> syllabus) {
        this.syllabus = syllabus;
    }

    public List<String> getGradingDictianory() {
        return gradingDictianory;
    }

    public void setGradingDictianory(List<String> gradingDictianory) {
        this.gradingDictianory = gradingDictianory;
    }

    public String getLecturer() {
        return lecturer;
    }

    public void setLecturer(String lecturer) {
        this.lecturer = lecturer;
    }

    public List<String> getResources() {
        return resources;
    }

    public void setResources(List<String> resources) {
        this.resources = resources;
    }
}
