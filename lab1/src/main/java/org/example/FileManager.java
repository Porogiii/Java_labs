package org.example;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class FileManager {
    private final String outputPath;
    private final String prefix;
    private final boolean appendMode;

    public FileManager(String outputPath, String prefix, boolean appendMode) {
        this.outputPath = outputPath;
        this.prefix = prefix;
        this.appendMode = appendMode;
    }

    public <T> void writeToFile(String filename, Set<T> data) {
        if (data.isEmpty()) return;
        String path = outputPath.isEmpty() ? filename : Paths.get(outputPath, prefix + filename).toString();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path, appendMode))) {
            for (T item : data) {
                writer.write(item.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error writing file: " + path);
        }
    }

    public void removeUnnecessaryFiles(boolean hasIntegers, boolean hasFloats, boolean hasStrings) {
        Map<String, Boolean> fileStatus = Map.of(
                "integers.txt", hasIntegers,
                "floats.txt", hasFloats,
                "strings.txt", hasStrings
        );

        for (Map.Entry<String, Boolean> entry : fileStatus.entrySet()) {
            String path = outputPath.isEmpty() ? entry.getKey() : Paths.get(outputPath, prefix + entry.getKey()).toString();
            File file = new File(path);
            if (file.exists() && !entry.getValue()) {
                file.delete();
            }
        }
    }
}
