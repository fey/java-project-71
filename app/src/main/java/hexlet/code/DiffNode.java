package hexlet.code;

public class DiffNode {
    public enum NodeType {
        UNCHANGED,
        ADDED,
        REMOVED,
        CHANGED,
        ROOT,
        NESTED
    }

    public String key;
    public NodeType type;
    public Object value1;
    public Object value2;
}
