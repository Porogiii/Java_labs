package org.example;

public class Main {
    public static void main(String[] args) {

        //обрабатываем аргументы
        ArgumentParser parser = new ArgumentParser();
        parser.parse(args);

        //обрабатываем входные файлы
        DataProcessor processor = new DataProcessor();
        for (String file : parser.getInputFiles()) {
            processor.processFile(file);
        }

        //запись результатов
        FileManager fileManager = new FileManager(parser.getOutputPath(), parser.getPrefix(), parser.isAppendMode());
        fileManager.writeToFile("integers.txt", processor.getIntegers());
        fileManager.writeToFile("floats.txt", processor.getFloats());
        fileManager.writeToFile("strings.txt", processor.getStrings());

        //удаление ненужных файлов
        fileManager.removeUnnecessaryFiles(!processor.getIntegers().isEmpty(), !processor.getFloats().isEmpty(), !processor.getStrings().isEmpty());

        //статистика
        StatsPrint.printStatistics(processor.getIntegers(), processor.getFloats(), processor.getStrings(), parser.isFullStats(), parser.isShortStats());
    }
}
