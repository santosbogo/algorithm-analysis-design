package anaydis.immutable;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayDeque;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Queue;

public class BinaryTree<K, V> implements Map<K, V>{
    private int size = 0;
    private Node root;
    Comparator<K> comparator;

    public BinaryTree(Comparator<K> comparator){
        this.comparator = comparator;
    }

    private BinaryTree(Comparator<K> comparator, Node root, int size){
        this.comparator = comparator;
        this.root = root;
        this.size = size;
    }

    private class Node{
        final K key;
        final V value;
        final Node left;
        final Node right;

        Node(K key, V value, Node left, Node right){
            this.key = key;
            this.value = value;
            this.left = left;
            this.right = right;
        }

        Node(K key, V value){
            this.key = key;
            this.value = value;
            this.left = null;
            this.right = null;
        }
    }

    @Override
    public int size() {
        return size;
    }

    private Node find(Node node, K key){
        if (node == null) return null;

        int comp = comparator.compare(key, node.key);

        if(comp < 0) return find(node.left, key);
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
        if (newRoot == root) {
            return this; // No changes were made
        }
        return new BinaryTree<>(comparator, newRoot, size + 1);
    }
    private Node put(Node node, K key, V value){
        if (node == null)
            return new Node(key, value);

        int cmp = comparator.compare(key, node.key);

        if (cmp < 0)
            return new Node(node.key, node.value, put(node.left, key, value), node.right);
        else if (cmp > 0)
            return new Node(node.key, node.value, node.left, put(node.right, key, value));
        else
            return new Node(key, value, node.left, node.right);
    }

    @Override
    public Iterator<K> keys() {
        Queue<K> keys = new ArrayDeque<>() {
        };
        inOrder(root, keys);
        return keys.iterator();
    }
    private void inOrder(Node node, Queue<K> keys){
        if (node == null) return;
        inOrder(node.left, keys);
        keys.add(node.key);
        inOrder(node.right, keys);
    }
}
