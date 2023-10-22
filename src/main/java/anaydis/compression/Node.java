package anaydis.compression;

import anaydis.bit.Bits;
import java.util.Map;

public class Node implements Comparable<Node> {
    public final char character;
    public final int value;
    public final Node left;
    public final Node right;

    // Constructor for leaf nodes
    public Node(char character, int value) {
        this.character = character;
        this.value = value;
        this.left = null;
        this.right = null;
    }

    // Constructor for internal nodes
    public Node(Node left, Node right, int value) {
        this.character = '\0';  // Null character for non-leaf nodes
        this.value = value;
        this.left = left;
        this.right = right;
    }

    public boolean isLeaf() {
        return left == null && right == null;
    }

    @Override
    public int compareTo(Node other) {
        return Integer.compare(this.value, other.value);
    }

    public void collect(Map<Integer, Bits> table, Bits prefix) {
        if (isLeaf()) {
            table.put((int) character, prefix);
        } else {
            Bits leftPrefix = prefix.copy();
            leftPrefix.add(false);
            left.collect(table, leftPrefix);

            Bits rightPrefix = prefix.copy();
            rightPrefix.add(true);
            right.collect(table, rightPrefix);
        }
    }
}
