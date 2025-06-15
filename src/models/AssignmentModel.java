package models;
import java.time.LocalDateTime;
import java.util.LinkedList;

public class AssignmentModel {
    private int id ;
    private int belonged_lecture;
    private String title ;
    private String description ;
    private LinkedList<String> attachedFiles ;
    private float grade_weight;
    private LocalDateTime given_date;
    private LocalDateTime due_date;

    public AssignmentModel(int id, int belongedLecture, String title, String description, LinkedList<String> attachedFiles,
                           float grade_weight , LocalDateTime given_date, LocalDateTime due_date) {
        this.id = id;
        belonged_lecture = belongedLecture;
        this.title = title;
        this.description = description;
        this.attachedFiles = attachedFiles;
        this.grade_weight = grade_weight;
        this.given_date = given_date;
        this.due_date = due_date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBelongedLecture() {
        return belonged_lecture;
    }

    public void setBelongedLecture(int belonged_lecture) {
        this.belonged_lecture = belonged_lecture;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LinkedList<String> getAttachedFiles() {
        return attachedFiles;
    }

    public void setAttachedFiles(LinkedList<String> attachedFiles) {
        this.attachedFiles = attachedFiles;
    }
    public float getGradeWeight() {
        return grade_weight;
    }


    public void setGradeWeight(float grade_weight) {
        this.grade_weight = grade_weight;
    }

    public LocalDateTime getGivenDate() {
        return given_date;
    }

    public void setGivenDate(LocalDateTime given_date) {
        this.given_date = given_date;
    }

    public LocalDateTime getDueDate() {
        return due_date;
    }

    public void setDueDate(LocalDateTime due_date) {
        this.due_date = due_date;
    }




}
