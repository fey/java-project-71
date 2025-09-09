package hexlet.code.formatters;

import hexlet.code.DiffNode;

import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

public class PlainFormatter {
    public static String format(List<DiffNode> diff) {
        var stringJoiner = new StringJoiner("\n");

        for (var node : diff) {
            String line;
            switch (node.type) {
                case UNCHANGED:
                    break;
                case ADDED:
                    line = String.format(
                            "Property '%s' was added with value: %s",
                            node.key,
                            stringifyValue(node.value2)
                    );
                    stringJoiner.add(line);
                    break;
                case REMOVED:
                    line = String.format("Property '%s' was removed", node.key);
                    stringJoiner.add(line);
                    break;
                case CHANGED:
                    line = String.format(
                            "Property '%s' was updated. From %s to %s",
                            node.key,
                            stringifyValue(node.value1),
                            stringifyValue(node.value2)
                    );
                    stringJoiner.add(line);
                    break;
                default:
                    throw new RuntimeException("Invalid node type.");
            }
        }

        return stringJoiner.toString();
    }

    private static String stringifyValue(Object value) {
        return switch (value) {
            case null -> "null";
            case Map _v -> "[complex value]";
            case List _v -> "[complex value]";
            case Integer _v -> value.toString();
            case Boolean _v -> value.toString();
            default -> String.format("'%s'", value);
        };
    }
}
