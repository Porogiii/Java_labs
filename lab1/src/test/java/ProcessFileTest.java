import org.example.Main;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.*;
import java.util.HashSet;
import java.util.Set;

public class ProcessFileTest {

    @Test
    public void testProcessFile() throws IOException {
        // Создаем временный файл для теста
        File tempFile = File.createTempFile("test", ".txt");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {
            writer.write("123\n");
            writer.write("456.78\n");
            writer.write("Hello\n");
        }

        Set<Integer> integers = new HashSet<>();
        Set<Double> floats = new HashSet<>();
        Set<String> strings = new HashSet<>();

        Main.processFile(tempFile.getAbsolutePath(), integers, floats, strings);

        // Проверяем, что данные были правильно классифицированы
        assertTrue(integers.contains(123));
        assertTrue(floats.contains(456.78));
        assertTrue(strings.contains("Hello"));

        // Удаляем временный файл после теста
        tempFile.delete();
    }
}