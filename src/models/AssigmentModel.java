package models;
import java.util.List;

public class AssigmentModel {
    private int id ;
    private String title ;
    private String description ;
    private List<String> attachedFiles ;
    private  float grade_weight;
    private String given_date;
    private String due_date;

    public AssigmentModel(int id , String title,String description, float grade_weight , String given_date, String due_date) {
        this.id = id;
        this.title = title;
        this.description = description;
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

    public List<String> getAttachedFiles() {
        return attachedFiles;
    }

    public void setAttachedFiles(List<String> attachedFiles) {
        this.attachedFiles = attachedFiles;
    }

    public float getGradeWeight() {
        return grade_weight;
    }

    public void setGrade_weight(float grade_weight) {
        this.grade_weight = grade_weight;
    }

    public String getGiven_date() {
        return given_date;
    }

    public void setGiven_date(String given_date) {
        this.given_date = given_date;
    }

    public String getDue_date() {
        return due_date;
    }

    public void setDue_date(String due_date) {
        this.due_date = due_date;
    }




}
