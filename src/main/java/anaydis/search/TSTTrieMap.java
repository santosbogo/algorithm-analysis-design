package anaydis.search;

import org.jetbrains.annotations.NotNull;

import java.util.*;

public class TSTTrieMap<T> implements Map<String, T> {

    Node<T> root = null;
    int size = 0;

    public class Node<T> {
        private char c;
        private Node<T> left;
        private Node<T> middle;
        private Node<T> right;
        private T value;
    }

    public class Tuple<T> {
        private Node<T> node;
        private T value;

        public Tuple(Node<T> node, T value){
            this.node = node;
            this.value = value;
        }

        public Node<T> getNode(){
            return node;
        }

        public T getValue(){
            return value;
        }
    }

    private Node<T> find(Node<T> node, String key, int level) {
        if (root == null) return null;

        char c = key.charAt(level);

        if (c > node.c) return find(node.left, key, level);
        else if (c < node.c) return find(node.right, key, level);
            //Now we found the next char if the key (c == node.c). We need to ask if it is the las char of the key.
            // key.length() - 1 is because when we are at that level means it is the last character of the key, so we want that node
        else if (level < key.length() - 1) return find(node.middle, key, level + 1);
        return node;
    }

    @Override
    public boolean containsKey(String key) {
        if (key == null) throw new NullPointerException();
        return get(key) != null;
    }

    @Override
    public T get(String key) {
        if (key == null) throw new NullPointerException();
        Node<T> node = find(root, key, 0);
        return ((node != null) && (node.value != null)) ? node.value : null;
    }

    @Override
    public T put(String key, T value) {
        if (key == null) throw new NullPointerException();
        Tuple<T> tuple = put(root, key, value, 0);
        root = tuple.getNode();
        return tuple.getValue();
    }

    private Tuple<T> put(Node<T> node, String key, T value, int level) {
        char c = key.charAt(level);
        T oldValue = null;

        if (node == null) {
            node = new Node<>();
            node.c = c;
            return new Tuple<>(node, null);
        }


        if (c < node.c) node.left = put(node.left, key, value, level).getNode();
        else if (c > node.c) node.right = put(node.right, key, value, level).getNode();
        else if (level < key.length() - 1) node.middle = put(node.middle, key, value, level + 1).getNode();
        else {
            if (node.value != null) {
                oldValue = node.value;
            } else size++;
            node.value = value;
        }

        return new Tuple<>(node, oldValue);
    }

    @Override
    public Iterator<String> keys() {
        List<String> keys = new ArrayList<>(size);
        getKeys(root, "", keys);
        return keys.iterator();
    }

    private void getKeys(Node<T> node, String prefix, List<String> keys) {
        if (node == null) return;

        if (node.value != null) keys.add(prefix + node.c);

        getKeys(node.left, prefix, keys);

        getKeys(node.right, prefix, keys);

        getKeys(node.middle, prefix + node.c, keys);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        size = 0;
        root = null;
    }

    public String toString() {
        return "TST Trie";
    }
}