import org.example.ArgumentParser;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

public class MainTest {

    @Test
    public void testParseArguments() {
        // Пример аргументов командной строки
        String[] args = {
                "-o", "/output",
                "-p", "prefix_",
                "-a",
                "-f",
                "-s",
                "file1.txt",
                "file2.txt"
        };

        // Создаем объект ArgumentParser
        ArgumentParser parser = new ArgumentParser();

        // Парсим аргументы
        parser.parse(args);

        // Проверяем, что поля правильно проинициализированы
        assertEquals("/output", parser.getOutputPath(), "Output path is incorrect");
        assertEquals("prefix_", parser.getPrefix(), "Prefix is incorrect");
        assertTrue(parser.isAppendMode(), "Append mode should be true");
        assertTrue(parser.isFullStats(), "Full stats mode should be true");
        assertTrue(parser.isShortStats(), "Short stats mode should be true");

        // Проверяем, что inputFiles содержат правильные файлы
        List<String> inputFiles = parser.getInputFiles();
        assertEquals(2, inputFiles.size(), "Input files count is incorrect");
        assertTrue(inputFiles.contains("file1.txt"), "Input files should contain file1.txt");
        assertTrue(inputFiles.contains("file2.txt"), "Input files should contain file2.txt");
    }
}
