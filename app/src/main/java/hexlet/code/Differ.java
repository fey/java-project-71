package hexlet.code;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Sets;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.StringJoiner;

import static com.google.common.io.Files.getFileExtension;


public class Differ {
    public static String generate(String filepath1, String filepath2, String format) throws Exception {
        var fileContent1 = getFileContent(filepath1);
        var ext1 = getFileExtension(filepath1);
        var fileContent2 = getFileContent(filepath2);
        var ext2 = getFileExtension(filepath1);

        var data1 = Parser.parse(fileContent1, Parser.Format.valueOf(ext1));
        var data2 = Parser.parse(fileContent2, Parser.Format.valueOf(ext2));

        var keys1 = data1.keySet();
        var keys2 = data2.keySet();

        var keys = new ArrayList<>(
                Sets.union(keys1, keys2)
        );

        keys.sort(String.CASE_INSENSITIVE_ORDER);

        var diff = new LinkedList<DiffNode>();

        for (var key: keys) {
            var node = new DiffNode();
            var value1 = data1.get(key);
            var value2 = data2.get(key);
            if (keys1.contains(key) && keys2.contains(key) && value1.equals(value2)) {
                node.key = key;
                node.type = DiffNode.NodeType.UNCHANGED;
                node.value1 = data1.get(key);
            } else if (keys1.contains(key) && keys2.contains(key) && !value1.equals(value2)) {
                node.key = key;
                node.type = DiffNode.NodeType.CHANGED;
                node.value1 = data1.get(key);
                node.value2 = data2.get(key);
            } else if (keys1.contains(key) && !keys2.contains(key)) {
                node.key = key;
                node.type = DiffNode.NodeType.REMOVED;
                node.value1 = data1.get(key);
            } else if (!keys1.contains(key) && keys2.contains(key)) {
                node.key = key;
                node.type = DiffNode.NodeType.ADDED;
                node.value2 = data2.get(key);
            }
            diff.add(node);
        }

        return stylish(diff);
    }

    private static String getFileContent(String filepath1) throws IOException {
        return Files.readString(Path.of(filepath1));
    }

    public static String generate(String filepath1, String filepath2) throws Exception {
        return generate(filepath1, filepath2, "stylish");
    }

    private static String stylish(List<DiffNode> diff) {
        var stringJoiner = new StringJoiner("\n", "{\n", "\n}");

        for (var node: diff) {
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
