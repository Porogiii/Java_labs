package org.example;

import java.io.*;
import java.util.*;

public class DataProcessor {

    //поля
    private final Set<Integer> integers = new HashSet<>();
    private final Set<Double> floats = new HashSet<>();
    private final Set<String> strings = new HashSet<>();

    //открывает файл и построчно передаёт данные classify
    public void processFile(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                classifyData(line);
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + filename);
        }
    }

    //преобразовывает строку в int или double или оставляет в string
    private void classifyData(String line) {
        try {
            integers.add(Integer.parseInt(line));
        } catch (NumberFormatException e1) {
            try {
                floats.add(Double.parseDouble(line));
            } catch (NumberFormatException e2) {
                strings.add(line);
            }
        }
    }

    public Set<Integer> getIntegers() { return integers; }
    public Set<Double> getFloats() { return floats; }
    public Set<String> getStrings() { return strings; }
}
