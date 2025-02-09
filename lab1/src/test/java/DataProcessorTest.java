import org.example.DataProcessor;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class DataProcessorTest {

    private DataProcessor dataProcessor;
    private Path tempFile;

    @BeforeEach
    void setUp() throws IOException {
        // Создаем новый объект DataProcessor перед каждым тестом
        dataProcessor = new DataProcessor();

        // Создаем временный файл для тестов
        tempFile = Files.createTempFile("testfile", ".txt");
    }

    @Test
    void testProcessFile_withIntegers() throws IOException {
        // Записываем данные, состоящие из целых чисел
        Files.write(tempFile, "123\n456\n789\n".getBytes());

        // Обрабатываем файл
        dataProcessor.processFile(tempFile.toString());

        // Проверяем, что в наборе целых чисел есть ожидаемые значения
        Set<Integer> integers = dataProcessor.getIntegers();
        assertTrue(integers.contains(123));
        assertTrue(integers.contains(456));
        assertTrue(integers.contains(789));
    }

    @Test
    void testProcessFile_withFloats() throws IOException {
        // Записываем данные с плавающими точками
        Files.write(tempFile, "12.34\n56.78\n90.12\n".getBytes());

        // Обрабатываем файл
        dataProcessor.processFile(tempFile.toString());

        // Проверяем, что в наборе чисел с плавающей запятой есть ожидаемые значения
        Set<Double> floats = dataProcessor.getFloats();
        assertTrue(floats.contains(12.34));
        assertTrue(floats.contains(56.78));
        assertTrue(floats.contains(90.12));
    }

    @Test
    void testProcessFile_withStrings() throws IOException {
        // Записываем строки
        Files.write(tempFile, "Hello\nWorld\nTest\n".getBytes());

        // Обрабатываем файл
        dataProcessor.processFile(tempFile.toString());

        // Проверяем, что в наборе строк есть ожидаемые значения
        Set<String> strings = dataProcessor.getStrings();
        assertTrue(strings.contains("Hello"));
        assertTrue(strings.contains("World"));
        assertTrue(strings.contains("Test"));
    }

    @Test
    void testProcessFile_withMixedData() throws IOException {
        // Записываем смешанные данные: целые числа, числа с плавающей запятой и строки
        Files.write(tempFile, "123\n45.67\nHello\n890\n89.12\nWorld\n".getBytes());

        // Обрабатываем файл
        dataProcessor.processFile(tempFile.toString());

        // Проверяем, что данные правильно классифицированы
        Set<Integer> integers = dataProcessor.getIntegers();
        Set<Double> floats = dataProcessor.getFloats();
        Set<String> strings = dataProcessor.getStrings();

        assertTrue(integers.contains(123));
        assertTrue(integers.contains(890));

        assertTrue(floats.contains(45.67));
        assertTrue(floats.contains(89.12));

        assertTrue(strings.contains("Hello"));
        assertTrue(strings.contains("World"));
    }

    @Test
    void testProcessFile_withEmptyFile() throws IOException {
        // Записываем пустой файл
        Files.write(tempFile, "".getBytes());

        // Обрабатываем файл
        dataProcessor.processFile(tempFile.toString());

        // Проверяем, что наборы пустые
        assertTrue(dataProcessor.getIntegers().isEmpty());
        assertTrue(dataProcessor.getFloats().isEmpty());
        assertTrue(dataProcessor.getStrings().isEmpty());
    }

    @Test
    void testProcessFile_withInvalidData() throws IOException {
        // Записываем некорректные данные
        Files.write(tempFile, "abc\n123.45.67\n$%&\n".getBytes());

        // Обрабатываем файл
        dataProcessor.processFile(tempFile.toString());

        // Проверяем, что некорректные данные не добавлены в наборы
        assertTrue(dataProcessor.getStrings().contains("abc"));
        assertTrue(dataProcessor.getStrings().contains("$%&"));
        assertTrue(dataProcessor.getFloats().isEmpty());
        assertTrue(dataProcessor.getIntegers().isEmpty());
    }

    @Test
    void testProcessFile_withNonExistentFile() {
        // Сохраняем оригинальный поток ошибок
        PrintStream originalErr = System.err;
        ByteArrayOutputStream errStream = new ByteArrayOutputStream();
        System.setErr(new PrintStream(errStream));

        // Попытка обработки несуществующего файла
        dataProcessor.processFile("non_existent_file.txt");

        // Проверяем, что данные не были добавлены
        assertTrue(dataProcessor.getIntegers().isEmpty());
        assertTrue(dataProcessor.getFloats().isEmpty());
        assertTrue(dataProcessor.getStrings().isEmpty());

        // Проверяем, что в выводе содержится сообщение об ошибке
        String expectedErrorMessage = "Error reading file: non_existent_file.txt";
        assertTrue(errStream.toString().contains(expectedErrorMessage));

        // Восстанавливаем оригинальный поток ошибок
        System.setErr(originalErr);
    }
}
