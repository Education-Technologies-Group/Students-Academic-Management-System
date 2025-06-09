package repositories;

import models.GradeDictionaryModel;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.Arrays;

public class GradeDictionaryRepository {
    private final String DB_PATH = "data/grade_dictionary.csv";
    private LinkedList<GradeDictionaryModel> gradeDictionaries;
    private boolean dbChanged = false;

    public GradeDictionaryRepository() throws IOException {
        loadFromCSV();
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            if (dbChanged) saveToCSV();
        }));
    }

    public GradeDictionaryModel getGradeDictionaryById(int id) {
        for (GradeDictionaryModel dict : gradeDictionaries) {
            if (dict.getId() == id) {
                return dict;
            }
        }
        return null;
    }

    public List<GradeDictionaryModel> getGradeDictionariesByLectureId(int lectureId) {
        return gradeDictionaries.stream()
                .filter(dict -> dict.getLectureId() == lectureId)
                .collect(Collectors.toList());
    }

    public void addGradeDictionary(GradeDictionaryModel dict) {
        gradeDictionaries.add(dict);
        dbChanged = true;
    }

    public void removeGradeDictionary(GradeDictionaryModel dict) {
        gradeDictionaries.remove(dict);
        dbChanged = true;
    }

    private void loadFromCSV() throws IOException {
        gradeDictionaries = new LinkedList<>();
        File file = new File(DB_PATH);

        if (!file.exists()) {
            file.getParentFile().mkdirs();
            file.createNewFile();
            return;
        }

        Scanner sc = new Scanner(file);
        while (sc.hasNextLine()) {
            String[] data = sc.nextLine().split(",", -1);

            gradeDictionaries.add(new GradeDictionaryModel(
                    Integer.parseInt(data[0]),                             // id
                    Integer.parseInt(data[1]),                             // lectureID
                    Arrays.stream(data[2].split(";"))                // criteria_names
                            .filter(s -> !s.isEmpty())
                            .collect(Collectors.toList()),
                    Arrays.stream(data[3].split(";"))                // criteria_weights
                            .filter(s -> !s.isEmpty())
                            .map(Float::parseFloat)
                            .collect(Collectors.toList()),
                    Integer.parseInt(data[4])                               // total
            ));
        }
        sc.close();
    }


    private void saveToCSV() {
        System.out.println("Saving grade dictionaries...");
        try (FileWriter writer = new FileWriter(DB_PATH)) {
            for (GradeDictionaryModel dict : gradeDictionaries) {
                writer.write(dict.getId() + ",");
                writer.write(dict.getLectureId() + ",");
                writer.write(String.join(";", dict.getCriteria_names()) + ",");
                writer.write(dict.getCriteria_weights().stream()
                        .map(String::valueOf)
                        .collect(Collectors.joining(";")) + ",");
                writer.write(dict.getTotal() + "\n");
            }
        } catch (IOException e) {
            System.out.println("Error saving grade dictionaries: " + e.getMessage());
        }
    }
}
