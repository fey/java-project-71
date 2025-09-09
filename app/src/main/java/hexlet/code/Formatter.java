package hexlet.code;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

public class Formatter {
    public static String format(List<DiffNode> diff, String format) {
        return switch (format) {
            case "stylish" -> stylish(diff);
            case "plain" -> plain(diff);
            default -> throw new IllegalStateException("Unexpected value: " + format);
        };
    }

    private static String plain(List<DiffNode> diff) {
        var stringJoiner = new StringJoiner("\n");

        for (var node : diff) {
            String line;
            switch (node.type) {
                case UNCHANGED:
                    break;
                case ADDED:
                    line = String.format("Property '%s' was added with value: %s", node.key, plainStringifyValue(node.value2));
                    stringJoiner.add(line);
                    break;
                case REMOVED:
                    line = String.format("Property '%s' was removed", node.key);
                    stringJoiner.add(line);
                    break;
                case CHANGED:
                    line = String.format("Property '%s' was updated. From %s to %s", node.key, plainStringifyValue(node.value1), plainStringifyValue(node.value2));
                    stringJoiner.add(line);
                    break;
                default:
                    throw new RuntimeException("Invalid node type.");
            }
        }

        return stringJoiner.toString();
    }

    private static String plainStringifyValue(Object value) {
        return switch (value) {
            case null -> "null";
            case Map _v -> "[complex value]";
            case List _v -> "[complex value]";
            case Integer _v -> value.toString();
            case Boolean _v -> value.toString();
            default -> String.format("'%s'", value);
        };
    }

    private static String stylish(List<DiffNode> diff) {
        var stringJoiner = new StringJoiner("\n", "{\n", "\n}");

        for (var node : diff) {
            switch (node.type) {
                case UNCHANGED:
                    stringJoiner.add(String.format("    %s: %s", node.key, node.value1));
                    break;
                case ADDED:
                    stringJoiner.add(String.format("  + %s: %s", node.key, node.value2));
                    break;
                case REMOVED:
                    stringJoiner.add(String.format("  - %s: %s", node.key, node.value1));
                    break;
                case CHANGED:
                    stringJoiner.add(String.format("  - %s: %s", node.key, node.value1));
                    stringJoiner.add(String.format("  + %s: %s", node.key, node.value2));
                    break;
                default:
                    throw new RuntimeException("Invalid node type.");
            }
        }

        return stringJoiner.toString();
    }
}
