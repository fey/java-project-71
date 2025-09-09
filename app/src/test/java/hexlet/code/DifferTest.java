package hexlet.code;

import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DifferTest {
    @Test
    void testGenerate() throws Exception {
        var filePath1 = getFixturePath("file1.json");
        var filePath2 = getFixturePath("file2.json");

        var expected = readFixture("result.stylish");

        var result = Differ.generate(filePath1.toString(), filePath2.toString());

        assertEquals(expected, result);
    }

    @Test
    void testGenerateStylish() throws Exception {
        var filePath1 = getFixturePath("file1.json");
        var filePath2 = getFixturePath("file2.json");

        var expected = readFixture("result.stylish");

        var result = Differ.generate(filePath1.toString(), filePath2.toString(), "stylish");

        assertEquals(expected, result);
    }

    private static Path getFixturePath(String filename) {
        return Paths.get("src",  "test", "resources", "fixtures", filename);
    }

    private static String readFixture(String fileName) throws Exception {
        var path = getFixturePath(fileName);
        return Files.readString(path).trim();
    }
}
