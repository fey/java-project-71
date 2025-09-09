package hexlet.code;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.util.Map;

public class Parser {
    public enum Format {
        yaml,
        yml,
        json;
    }

    public static Map<String, Object> parse(String data, Format format) throws JsonProcessingException {
        return switch (format) {
            case Format.yaml, Format.yml -> parseYaml(data);
            case Format.json -> parseJson(data);
        };
    }

    private static Map<String, Object> parseYaml(String data) throws JsonProcessingException {
        var mapper = new ObjectMapper(new YAMLFactory());

        return mapper.readValue(data, new TypeReference<>() { });
    }

    private static Map<String, Object> parseJson(String data) throws JsonProcessingException {
        var mapper = new ObjectMapper();

        return mapper.readValue(data, new TypeReference<>() { });
    }
}
