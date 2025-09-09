package hexlet.code.formatters;

import hexlet.code.DiffNode;

import java.util.List;
import java.util.StringJoiner;

public class StylishFormatter {
    public static String format(List<DiffNode> diff) {
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
