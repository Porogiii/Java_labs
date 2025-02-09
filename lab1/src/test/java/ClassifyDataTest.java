import org.example.Main;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;
import java.util.Set;

public class ClassifyDataTest {

    @Test
    public void testClassifyData() {
        Set<Integer> integers = new HashSet<>();
        Set<Double> floats = new HashSet<>();
        Set<String> strings = new HashSet<>();

        // Проверка целых чисел
        Main.classifyData("123", integers, floats, strings);
        assertTrue(integers.contains(123));

        // Проверка чисел с плавающей точкой
        Main.classifyData("456.78", integers, floats, strings);
        assertTrue(floats.contains(456.78));

        // Проверка строк
        Main.classifyData("Hello", integers, floats, strings);
        assertTrue(strings.contains("Hello"));
    }
}
