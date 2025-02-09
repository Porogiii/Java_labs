package org.example;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class Main {
    public static String outputPath = "";
    public static String prefix = "";
    public static boolean appendMode = false;
    public static boolean fullStats = false;
    public static boolean shortStats = false;

    public static void main(String[] args) {
        List<String> inputFiles = new ArrayList<>();
        parseArguments(args, inputFiles);

        Set<Integer> integers = new HashSet<>();
        Set<Double> floats = new HashSet<>();
        Set<String> strings = new HashSet<>();

        for (String file : inputFiles) {
            processFile(file, integers, floats, strings);
        }

        // Пишем в файл только если набор данных не пустой
        if (!integers.isEmpty()) {
            writeToFile("integers.txt", integers);
        }
        if (!floats.isEmpty()) {
            writeToFile("floats.txt", floats);
        }
        if (!strings.isEmpty()) {
            writeToFile("strings.txt", strings);
        }

        printStatistics(integers, floats, strings);
    }

    public static void parseArguments(String[] args, List<String> inputFiles) {
        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "-o":
                    outputPath = args[++i];
                    break;
                case "-p":
                    prefix = args[++i];
                    break;
                case "-a":
                    appendMode = true;
                    break;
                case "-f":
                    fullStats = true;
                    break;
                case "-s":
                    shortStats = true;
                    break;
                default:
                    inputFiles.add(args[i]);
                    break;
            }
        }
    }

    public static void processFile(String filename, Set<Integer> integers, Set<Double> floats, Set<String> strings) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                classifyData(line, integers, floats, strings);
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + filename);
        }
    }

    private static void classifyData(String line, Set<Integer> integers, Set<Double> floats, Set<String> strings) {
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

    public static <T> void writeToFile(String filename, Set<T> data) {
        // Если данных нет — не создаем файл
        if (data.isEmpty()) return;

        // Путь к файлу
        String path = outputPath.isEmpty() ? filename : Paths.get(outputPath, prefix + filename).toString();

        try {
            // Проверка, существует ли файл, если нет — создаем директорию и файл
            Path filePath = Path.of(path);
            Path dirPath = filePath.getParent();  // Получаем путь к директории

            // Создаем директорию, если она не существует
            if (dirPath != null && !Files.exists(dirPath)) {
                Files.createDirectories(dirPath);
            }

            // Записываем данные в файл
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(path, appendMode))) {
                for (T item : data) {
                    writer.write(item.toString());
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            System.err.println("Ошибка при записи в файл: " + path);
        }
    }



    private static void printStatistics(Set<Integer> integers, Set<Double> floats, Set<String> strings) {
        System.out.println("Statistics:");
        if (shortStats || fullStats) {
            System.out.printf("Integers: Count=%d%n", integers.size());
            System.out.printf("Floats: Count=%d%n", floats.size());
            System.out.printf("Strings: Count=%d%n", strings.size());
        }
        if (fullStats) {
            printNumericStats("Integers", integers);
            printNumericStats("Floats", floats);
            printStringStats(strings);
        }
    }

    private static void printNumericStats(String type, Set<? extends Number> numbers) {
        if (numbers.isEmpty()) return;
        double min = Collections.min(numbers, Comparator.comparingDouble(Number::doubleValue)).doubleValue();
        double max = Collections.max(numbers, Comparator.comparingDouble(Number::doubleValue)).doubleValue();
        double sum = numbers.stream().mapToDouble(Number::doubleValue).sum();
        double avg = sum / numbers.size();
        System.out.printf("%s: Min=%.2f, Max=%.2f, Sum=%.2f, Avg=%.2f%n", type, min, max, sum, avg);
    }

    private static void printStringStats(Set<String> strings) {
        if (strings.isEmpty()) return;
        int minLen = strings.stream().mapToInt(String::length).min().orElse(0);
        int maxLen = strings.stream().mapToInt(String::length).max().orElse(0);
        System.out.printf("Strings: Shortest=%d, Longest=%d%n", minLen, maxLen);
    }
}
