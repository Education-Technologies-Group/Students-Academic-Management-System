package models;
import java.util.List;

public class GradeDictionaryModel {
    private int id;
    private LectureModel belonged_lecture;
    private List<String> criteria_names;
    private List<Float> criteria_weights;
    private int total;

    public GradeDictionaryModel(int id, LectureModel lectureName, List<String> criteria_names, List<Float> criteria_weights, int total) {
        this.id = id;
        this.belonged_lecture = belonged_lecture;
        this.criteria_names = criteria_names;
        this.criteria_weights = criteria_weights;
        this.total = total;
    }

    public int getId() {
        return id;
    }

    public LectureModel getLectureName() {
        return belonged_lecture;
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

    public void setLectureName(LectureModel lectureName) {
        this.belonged_lecture = lectureName;
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
