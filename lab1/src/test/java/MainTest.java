import org.example.ArgumentParser;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

public class MainTest {

    @Test
    public void testParseArguments() {
        //пример аргументов командной строки
        String[] args = {
                "-o", "/output",
                "-p", "prefix_",
                "-a",
                "-f",
                "-s",
                "file1.txt",
                "file2.txt"
        };

        //объект ArgumentParser
        ArgumentParser parser = new ArgumentParser();

        //парсим аргументы
        parser.parse(args);

        System.out.println("Output Path: " + parser.getOutputPath());
        System.out.println("Prefix: " + parser.getPrefix());
        System.out.println("Append Mode: " + parser.isAppendMode());
        System.out.println("Full Stats: " + parser.isFullStats());
        System.out.println("Short Stats: " + parser.isShortStats());
        System.out.println("Input Files: " + parser.getInputFiles());

        //проверяем, что поля правильно проинициализированы
        assertEquals("/output", parser.getOutputPath(), "Output path is incorrect");
        assertEquals("prefix_", parser.getPrefix(), "Prefix is incorrect");
        assertTrue(parser.isAppendMode(), "Append mode should be true");
        assertTrue(parser.isFullStats(), "Full stats mode should be true");
        assertTrue(parser.isShortStats(), "Short stats mode should be true");

        //проверяем, что inputFiles содержат правильные файлы
        List<String> inputFiles = parser.getInputFiles();
        assertEquals(2, inputFiles.size(), "Input files count is incorrect");
        assertTrue(inputFiles.contains("file1.txt"), "Input files should contain file1.txt");
        assertTrue(inputFiles.contains("file2.txt"), "Input files should contain file2.txt");
    }
}
