package org.example;

import java.util.*;

public class StatsPrint {
    //вывод статистики
    public static void printStatistics(Set<Integer> integers, Set<Double> floats, Set<String> strings, boolean fullStats, boolean shortStats) {
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

    //full статистика
    private static void printNumericStats(String type, Set<? extends Number> numbers) {
        if (numbers.isEmpty()) return;
        double min = Collections.min(numbers, Comparator.comparingDouble(Number::doubleValue)).doubleValue();
        double max = Collections.max(numbers, Comparator.comparingDouble(Number::doubleValue)).doubleValue();
        double sum = numbers.stream().mapToDouble(Number::doubleValue).sum();
        double avg = sum / numbers.size();
        System.out.printf("%s: Min=%.2f, Max=%.2f, Sum=%.2f, Avg=%.2f%n", type, min, max, sum, avg);
    }

    //статистика string
    private static void printStringStats(Set<String> strings) {
        if (strings.isEmpty()) return;
        int minLen = strings.stream().mapToInt(String::length).min().orElse(0);
        int maxLen = strings.stream().mapToInt(String::length).max().orElse(0);
        System.out.printf("Strings: Shortest=%d, Longest=%d%n", minLen, maxLen);
    }
}
