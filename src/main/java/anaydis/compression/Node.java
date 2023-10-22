package anaydis.compression;

import anaydis.bit.Bits;
import java.util.Map;
public class Node implements Comparable<Node> {
    public Character character; // The character this node represents (null for internal nodes)
    public int value; // The frequency of the character (or combined frequencies for internal nodes)
    public Node left;  // Left child
    public Node right; // Right child

    // Constructor for leaf nodes
    public Node(Character character, int value) {
        this.character = character;
        this.value = value;
        this.left = null;
        this.right = null;
    }

    // Constructor for internal nodes
    public Node(Node left, Node right, int combinedValue) {
        this.character = null; // Internal nodes don't have a character
        this.left = left;
        this.right = right;
        this.value = combinedValue; // The sum of the children's frequencies
    }

    // Check if this node is a leaf (i.e., has no children)
    public boolean isLeaf() {
        return left == null && right == null;
    }

    // Used to build the symbol table recursively
    public void collect(Map<Integer, Bits> map, Bits prefix) {
        if (isLeaf()) {
            map.put((int) character, prefix);
        } else {
            if (left != null) {
                Bits leftPrefix = prefix.copy();
                leftPrefix.add(false);
                left.collect(map, leftPrefix);
            }
            if (right != null) {
                Bits rightPrefix = prefix.copy();
                rightPrefix.add(true);
                right.collect(map, rightPrefix);
            }
        }
    }


    @Override
    public int compareTo(Node other) {
        return Integer.compare(this.value, other.value); // Compare based on frequencies
    }
}

