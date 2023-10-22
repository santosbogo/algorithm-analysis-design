package anaydis.compression;

import anaydis.bit.Bits;

import java.util.Map;

public class Node implements Comparable<Node> {
    public final char character;  // El carácter representado por el nodo (solo relevante para las hojas)
    public final int value;      // Frecuencia del carácter o suma de las frecuencias de los hijos
    public final Node left;      // Hijo izquierdo
    public final Node right;     // Hijo derecho

    // Constructor para nodos hoja
    public Node(char character, int value) {
        this.character = character;
        this.value = value;
        this.left = null;
        this.right = null;
    }

    // Constructor para nodos internos
    public Node(Node left, Node right, int value) {
        this.character = '\0';
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
            if (left != null) {
                Bits leftPrefix = prefix.copy();
                leftPrefix.add(false);
                left.collect(table, leftPrefix);
            }
            if (right != null) {
                Bits rightPrefix = prefix.copy();
                rightPrefix.add(true);
                right.collect(table, rightPrefix);
            }
        }
    }
}
