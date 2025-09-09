package hexlet.code;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class DiffNode {
    public enum NodeType {
        UNCHANGED,
        ADDED,
        REMOVED,
        CHANGED,
    }

    private String key;
    private NodeType type;
    private Object value1;
    private Object value2;
}
