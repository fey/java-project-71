package hexlet.code.formatters;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import hexlet.code.DiffNode;

import java.util.List;

public class JsonFormatter {
    public static String format(List<DiffNode> diff) throws JsonProcessingException {
        var mapper = new ObjectMapper();

        return mapper.writeValueAsString(diff);
    }
}
