package models;
import java.util.List;

public class GradeDictionary {
    private int id;
    private String lectureName; // was: belonged_lecture
    private List<String> criteria_names;
    private List<Float> criteria_weights;
    private int total;

    public GradeDictionary(int id, String lectureName, List<String> criteria_names, List<Float> criteria_weights, int total) {
        this.id = id;
        this.lectureName = lectureName;
        this.criteria_names = criteria_names;
        this.criteria_weights = criteria_weights;
        this.total = total;
    }

    public int getId() {
        return id;
    }

    public String getLectureName() {
        return lectureName;
    }

    public List<String> getCriteria_names() {
        return criteria_names;
    }

    public List<Float> getCriteria_weights() {
        return criteria_weights;
    }

    public int getTotal() {
        return total;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setLectureName(String lectureName) {
        this.lectureName = lectureName;
    }

    public void setCriteria_names(List<String> criteria_names) {
        this.criteria_names = criteria_names;
    }

    public void setCriteria_weights(List<Float> criteria_weights) {
        this.criteria_weights = criteria_weights;
    }

    public void setTotal(int total) {
        this.total = total;
    }

}
