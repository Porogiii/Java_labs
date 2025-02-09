import org.example.Main;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

public class MainTest {

    @Test
    public void testParseArguments() {
        String[] args = {"-o", "/output", "-p", "prefix_", "-a", "-f", "file1.txt", "file2.txt"};
        List<String> inputFiles = new ArrayList<>();
        Main.parseArguments(args, inputFiles);

        assertEquals("/output", Main.outputPath);
        assertEquals("prefix_", Main.prefix);
        assertTrue(Main.appendMode);
        assertTrue(Main.fullStats);
        assertFalse(Main.shortStats);
        assertEquals(2, inputFiles.size());
        assertTrue(inputFiles.contains("file1.txt"));
        assertTrue(inputFiles.contains("file2.txt"));
    }
}
