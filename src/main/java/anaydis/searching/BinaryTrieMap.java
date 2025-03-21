package anaydis.searching;

import anaydis.search.Map;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class BinaryTrieMap<T> implements Map<String, T> {
    Node <T> root = null;
    int size;

    private static class Node<T> {
        private String key;
        private T value = null;
        private Node<T> left = null;
        private Node<T> right = null;
        Node(String key, T value){
            this.key = key;
            this.value = value;
        }
    }

    private boolean bitAt(String s, int nth) {
        final int position = nth / 8;
        if (position >= s.length()) {
            return false;
        }
        int bit = (s.charAt(position) >> (nth % 8)) & 1;
        return bit != 0;
    }

    private int bitAtInt(String s, int nth){
        final int position = nth / 8;
        if (position >= s.length()) {
            return 0;
        }
        return (s.charAt(position) >> (nth % 8)) & 1;
    }


    private Node<T> split(Node<T> nodeA, Node<T> nodeB, int level){
        Node<T> result = new Node<>("", null);
        int A = bitAtInt(nodeA.key, level);
        int B = bitAtInt(nodeB.key, level);

        switch (A * 2 + B) {
            case 0:
                result.left = split(nodeA, nodeB, level + 1);
                break;
            case 1:
                result.left = nodeA;
                result.right = nodeB;
                break;
            case 2:
                result.left = nodeB;
                result.right = nodeA;
                break;
            case 3:
                result.right = split(nodeA, nodeB, level + 1);
                break;
            default:
                break;
        }
        return result;
    }

    private boolean isLeaf(Node<T> node){
        return (node.right == null) && (node.left == null);
    }

    private Node<T> find(Node<T> node, String key, int level) {
        if (node == null) return null;
        if (isLeaf(node)) return key.equals(node.key) ? node : null;

        if (bitAt(key, level)) return find(node.right, key, level + 1);
        else return find(node.left, key, level + 1);
    }

    @Override
    public T get(String key) {
        if (key == null) throw new NullPointerException();
        if (!containsKey(key)) return null;
        Node<T> node = find(root, key, 0);
        return node.value;
    }

    @Override
    public T put(String key, T value) {
        if (key == null) throw new NullPointerException();

        T previous = get(key);
        root = put(root, new Node<>(key, value), 0);

        if (previous == null) {
            size++;
            return null;
        }
        return previous;
    }

    private Node<T> put(Node<T> node, Node<T> value, int level) {
        if (node == null) {
            return value;
        }

        if (isLeaf(node)) {
            if (node.key.equals(value.key)) {
                //This means it was an existing key
                node.value = value.value;
                return node;
            } else return split(value, node, level);
        } else {
            if (bitAt(value.key, level)) {
                node.right = put(node.right, value, level + 1);
            } else {
                node.left = put(node.left, value, level + 1);
            }
        }

        return node;
    }

    @Override
    public Iterator<String> keys() {
        ArrayList<String> list = new ArrayList<>();
        inOrderIterator(root, list);
        return list.iterator();
    }

    private void inOrderIterator(Node<T> node, ArrayList<String> list) {
        if (node==null) return;
        inOrderIterator(node.left, list);
        if (!Objects.equals(node.key, "")){
            list.add(node.key);}
        inOrderIterator(node.right, list);
    }

    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean containsKey(@NotNull String key) {
        Node<T> node = find(root, key, 0);
        return (node != null) && (node.value != null);
    }

    public String toString(){
        return "Binary Trie";
    }
}
