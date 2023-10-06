package anaydis.immutable;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayDeque;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Queue;

public class BinaryTree<K, V> implements Map<K, V> {
    private final Node root;
    private final Comparator<K> comparator;

    public BinaryTree(Comparator<K> comparator) {
        this.comparator = comparator;
        this.root = null;
    }

    private BinaryTree(Comparator<K> comparator, Node root) {
        this.comparator = comparator;
        this.root = root;
    }

    private class Node {
        final K key;
        final V value;
        final Node left;
        final Node right;
        final int size;

        Node(K key, V value, Node left, Node right, int size) {
            this.key = key;
            this.value = value;
            this.left = left;
            this.right = right;
            this.size = size;
        }

        Node(K key, V value) {
            this(key, value, null, null, 1);
        }
    }

    @Override
    public int size() {
        return root == null ? 0 : root.size;
    }

    private Node find(Node node, K key) {
        if (node == null) return null;

        int comp = comparator.compare(key, node.key);

        if (comp < 0) return find(node.left, key);
        else if (comp > 0) return find(node.right, key);
        else return node;
    }

    @Override
    public boolean containsKey(@NotNull K key) {
        return find(root, key) != null;
    }

    @Override
    public V get(@NotNull K key) {
        Node temp = find(root, key);
        return temp != null ? temp.value : null;
    }

    @Override
    public Map<K, V> put(@NotNull K key, V value) {
        Node newRoot = put(root, key, value);
        return new BinaryTree<>(comparator, newRoot);
    }

    private Node put(Node node, K key, V value) {
        if (node == null)
            return new Node(key, value);

        int cmp = comparator.compare(key, node.key);
        int newSize = node.size;

        if (cmp < 0) {
            Node newLeft = put(node.left, key, value);
            newSize = 1 + newLeft.size + (node.right == null ? 0 : node.right.size);
            return new Node(node.key, node.value, newLeft, node.right, newSize);
        } else if (cmp > 0) {
            Node newRight = put(node.right, key, value);
            newSize = 1 + (node.left == null ? 0 : node.left.size) + newRight.size;
            return new Node(node.key, node.value, node.left, newRight, newSize);
        } else {
            return new Node(key, value, node.left, node.right, node.size);
        }
    }

    @Override
    public Iterator<K> keys() {
        Queue<K> keys = new ArrayDeque<>();
        inOrder(root, keys);
        return keys.iterator();
    }

    private void inOrder(Node node, Queue<K> keys) {
        if (node == null) return;
        inOrder(node.left, keys);
        keys.add(node.key);
        inOrder(node.right, keys);
    }
}
