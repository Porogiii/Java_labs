package org.example;

import java.util.*;

public class ArgumentParser {
    private String outputPath = "";
    private String prefix = "";
    private boolean appendMode = false;
    private boolean fullStats = false;
    private boolean shortStats = false;
    private final List<String> inputFiles = new ArrayList<>();

    public void parse(String[] args) {
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

    public String getOutputPath() { return outputPath; }
    public String getPrefix() { return prefix; }
    public boolean isAppendMode() { return appendMode; }
    public boolean isFullStats() { return fullStats; }
    public boolean isShortStats() { return shortStats; }
    public List<String> getInputFiles() { return inputFiles; }
}
