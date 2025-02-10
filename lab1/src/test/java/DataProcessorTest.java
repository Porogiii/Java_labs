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
        //создаем новый объект DataProcessor
        dataProcessor = new DataProcessor();

        //создаем временный файл для тестов
        tempFile = Files.createTempFile("testfile", ".txt");
    }

    @Test
    void testProcessFile_withIntegers() throws IOException {
        //записываем данные, состоящие из целых чисел
        Files.write(tempFile, "123\n456\n789\n".getBytes());

        //обрабатываем файл
        dataProcessor.processFile(tempFile.toString());

        //проверяем, что в наборе есть ожидаемые значения
        Set<Integer> integers = dataProcessor.getIntegers();
        assertTrue(integers.contains(123));
        assertTrue(integers.contains(456));
        assertTrue(integers.contains(789));
    }

    @Test
    void testProcessFile_withFloats() throws IOException {
        //данные double
        Files.write(tempFile, "12.34\n56.78\n90.12\n".getBytes());

        //обрабатываем файл
        dataProcessor.processFile(tempFile.toString());

        // Проверяем, что в наборе чисел с плавающей запятой есть ожидаемые значения
        Set<Double> floats = dataProcessor.getFloats();
        assertTrue(floats.contains(12.34));
        assertTrue(floats.contains(56.78));
        assertTrue(floats.contains(90.12));
    }

    @Test
    void testProcessFile_withStrings() throws IOException {
        //строки
        Files.write(tempFile, "Hello\nWorld\nTest\n".getBytes());

        dataProcessor.processFile(tempFile.toString());

        //проверяем ожидаемые значения
        Set<String> strings = dataProcessor.getStrings();
        assertTrue(strings.contains("Hello"));
        assertTrue(strings.contains("World"));
        assertTrue(strings.contains("Test"));
    }

    @Test
    void testProcessFile_withMixedData() throws IOException {
        //записываем все типы данных
        Files.write(tempFile, "123\n45.67\nHello\n890\n89.12\nWorld\n".getBytes());

        dataProcessor.processFile(tempFile.toString());

        //проверяем, что данные правильно классифицированы
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
        //записываем пустой файл
        Files.write(tempFile, "".getBytes());

        dataProcessor.processFile(tempFile.toString());

        //проверяем, что наборы пустые
        assertTrue(dataProcessor.getIntegers().isEmpty());
        assertTrue(dataProcessor.getFloats().isEmpty());
        assertTrue(dataProcessor.getStrings().isEmpty());
    }

    @Test
    void testProcessFile_withInvalidData() throws IOException {
        //записываем некорректные данные
        Files.write(tempFile, "abc\n123.45.67\n$%&\n".getBytes());

        //обрабатываем файл
        dataProcessor.processFile(tempFile.toString());

        //проверяем, что некорректные данные не добавлены в наборы
        assertTrue(dataProcessor.getStrings().contains("abc"));
        assertTrue(dataProcessor.getStrings().contains("$%&"));
        assertTrue(dataProcessor.getFloats().isEmpty());
        assertTrue(dataProcessor.getIntegers().isEmpty());
    }

    @Test
    void testProcessFile_withNonExistentFile() {
        //сохраняем оригинальный поток ошибок
        PrintStream originalErr = System.err;
        ByteArrayOutputStream errStream = new ByteArrayOutputStream();
        System.setErr(new PrintStream(errStream));

        //попытка обработки несуществующего файла
        dataProcessor.processFile("non_existent_file.txt");

        //проверяем, что данные не были добавлены
        assertTrue(dataProcessor.getIntegers().isEmpty());
        assertTrue(dataProcessor.getFloats().isEmpty());
        assertTrue(dataProcessor.getStrings().isEmpty());

        //проверяем, что в выводе содержится сообщение об ошибке
        String expectedErrorMessage = "Error reading file: non_existent_file.txt";
        assertTrue(errStream.toString().contains(expectedErrorMessage));

        //восстанавливаем оригинальный поток ошибок
        System.setErr(originalErr);
    }
}
