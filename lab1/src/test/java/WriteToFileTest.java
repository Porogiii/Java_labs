import org.example.Main;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.*;
import java.nio.file.*;
import java.util.HashSet;
import java.util.Set;

public class WriteToFileTest {

    @Test
    public void testWriteToFile() throws IOException {
        Set<Integer> integers = new HashSet<>();
        integers.add(123);

        String tempDir = System.getProperty("java.io.tmpdir");
        String outputPath = tempDir + "/test_output";
        Main.outputPath = outputPath;  // Устанавливаем путь для вывода

        // Записываем данные в файл
        Main.writeToFile("integers.txt", integers);

        Path outputFilePath = Paths.get(outputPath, "integers.txt");

        // Проверяем, что файл был создан и содержит данные
        assertTrue(Files.exists(outputFilePath));
        try (BufferedReader reader = Files.newBufferedReader(outputFilePath)) {
            String line = reader.readLine();
            assertEquals("123", line);
        }

        // Удаляем файл после теста
        Files.delete(outputFilePath);
    }
}
