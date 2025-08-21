package hexlet.code;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.nio.file.Files;
import java.nio.file.Path;


public class Differ {
    public static String generate(String filepath1, String filepath2, String format) throws Exception {
        var fileContent1 = Files.readString(Path.of(filepath1));
        var fileContent2 = Files.readString(Path.of(filepath2));
        System.out.println(fileContent1);

        var mapper = new ObjectMapper();

        var data1 = mapper.readTree(fileContent1);
        var data2 = mapper.readTree(fileContent2);

        System.out.println(data1.get("host"));
        System.out.println(data2);

        return "";
    }

    public static String generate(String filepath1, String filepath2) throws Exception {
        return generate(filepath1, filepath2, "stylish");
    }
}
