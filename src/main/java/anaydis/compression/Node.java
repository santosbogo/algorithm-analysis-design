package anaydis.compression;

import anaydis.bit.Bits;
import java.util.Map;
public class Node implements Comparable<Node> {
    public char character;
    public Node left;
    public Node right;
    int frequency;

    // Constructor for leaf nodes
    public Node(Character character, int frequency) {
        this.character = character;
        this.frequency = frequency;
        this.left = null;
        this.right = null;
    }

    // Constructor for internal nodes
    public Node(Node left, Node right) {
        frequency = left.frequency + right.frequency;
        this.left = left;
        this.right = right;
    }

    // Check if this node is a leaf (i.e., has no children)
    public boolean isLeaf() {
        return left == null && right == null;
    }

    public void collect(Map<Character, Bits> result, Bits bits){
        if(isLeaf())
            result.put(character, bits);
        else {
            left.collect(result, bits.copy().add(false));
            right.collect(result, bits.copy().add(true));
        }
    }

    private int compareFrequencies(Node a, Node b){
        return a.frequency - b.frequency;
    }

    @Override
    public int compareTo(Node other) {
        return Integer.compare(this.frequency, other.frequency);
    }
}

