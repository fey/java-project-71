package hexlet.code;

import com.google.common.collect.Sets;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Objects;

import static com.google.common.io.Files.getFileExtension;
import static hexlet.code.DiffNode.NodeType.ADDED;
import static hexlet.code.DiffNode.NodeType.CHANGED;
import static hexlet.code.DiffNode.NodeType.REMOVED;
import static hexlet.code.DiffNode.NodeType.UNCHANGED;

public class Differ {
    public static String generate(String filepath1, String filepath2, String format) throws Exception {
        var fileContent1 = getFileContent(filepath1);
        var ext1 = getFileExtension(filepath1);
        var fileContent2 = getFileContent(filepath2);
        var ext2 = getFileExtension(filepath1);

        var data1 = Parser.parse(fileContent1, ext1.toLowerCase());
        var data2 = Parser.parse(fileContent2, ext2.toLowerCase());

        var keys1 = data1.keySet();
        var keys2 = data2.keySet();

        var unionKeys = new ArrayList<>(
                Sets.union(keys1, keys2)
        );

        unionKeys.sort(String.CASE_INSENSITIVE_ORDER);

        var diff = new LinkedList<DiffNode>();

        for (var key: unionKeys) {
            DiffNode node = null;
            var value1 = data1.get(key);
            var value2 = data2.get(key);

            if (keys1.contains(key) && keys2.contains(key) && Objects.equals(value1, value2)) {
                node = new DiffNode(key, UNCHANGED, value1, value2);
            } else if (keys1.contains(key) && keys2.contains(key) && !Objects.equals(value1, value2)) {
                node = new DiffNode(
                        key,
                        CHANGED,
                        value1,
                        value2
                );
            } else if (keys1.contains(key) && !keys2.contains(key)) {
                node = new DiffNode(
                        key,
                        REMOVED,
                        value1,
                        null
                );
            } else if (!keys1.contains(key) && keys2.contains(key)) {
                node = new DiffNode(key, ADDED, null, value2);
            }

            diff.add(node);
        }

        return Formatter.format(diff, format);
    }

    private static String getFileContent(String filepath1) throws IOException {
        return Files.readString(Path.of(filepath1));
    }

    public static String generate(String filepath1, String filepath2) throws Exception {
        return generate(filepath1, filepath2, "stylish");
    }

}
