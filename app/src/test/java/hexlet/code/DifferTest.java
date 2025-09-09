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
    void testGenerateJson() throws Exception {
        var filePath1 = getFixturePath("file1.json");
        var filePath2 = getFixturePath("file2.json");
        var expectedStylish = readFixture("result.stylish");
        var expectedPlain = readFixture("result.plain");

        assertEquals(expectedStylish, Differ.generate(filePath1.toString(), filePath2.toString(), "stylish"));
        assertEquals(expectedPlain, Differ.generate(filePath1.toString(), filePath2.toString(), "plain"));
    }

    @Test
    void testGenerateYaml() throws Exception {
        var filePath1 = getFixturePath("file1.yml");
        var filePath2 = getFixturePath("file2.yml");
        var expectedStylish = readFixture("result.stylish");
        var expectedPlain = readFixture("result.plain");

        assertEquals(expectedStylish, Differ.generate(filePath1.toString(), filePath2.toString(), "stylish"));
        assertEquals(expectedPlain, Differ.generate(filePath1.toString(), filePath2.toString(), "plain"));
    }

    private static Path getFixturePath(String filename) {
        return Paths.get("src",  "test", "resources", "fixtures", filename);
    }

    private static String readFixture(String fileName) throws Exception {
        var path = getFixturePath(fileName);
        return Files.readString(path).trim();
    }
}
