package anaydis.search;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class BinaryTrieMap<T> implements Map<String, T>{
    Node <T> root = null;
    int size;

    private class Node<T> {
        private T value = null;
        private Node<T> left = null;
        private Node<T> right = null;
    }

    private boolean bitAt(String s, int nth) {
        final int pos = nth / 8;
        return pos < s.length() && (s.charAt(pos) >> (nth % 8) & 1) != 0;
    }

    private Node<T> find (Node<T> node, String key){
        if (node == null) return null;
        int n = key.length();
        for (int i = 0; i < n; i++) {
            if (bitAt(key, i)) {
                node = node.right;
            }
            else node = node.left;
            if (node == null) return null;
        }
        return node;
    }

    @Override
    public T get(String key) {
        if (key == null) throw new NullPointerException();
        Node<T> node = find(root, key);
        return node == null ? null : node.value;
    }

    @Override
    public T put(@NotNull String key, T value) {
        if (key == null) throw new NullPointerException();

        T previous = get(key);
        root = put(root, key, value, 0);

        if (previous == null) {
            size ++;
            return null;
        }
        return previous;
    }
    private Node<T> put(Node<T> node, String key, T value, int nthBit) {
        if (node == null) {
            node = new Node<>();
        }

        if (nthBit == key.length() * 8) {
            // Last position of the key, put the value
            node.value = value;
            return node;
        }

        boolean bit = bitAt(key, nthBit);

        if (bit) {
            node.right = put(node.right, key, value, nthBit + 1);
        } else {
            node.left = put(node.left, key, value, nthBit + 1);
        }

        return node;
    }

    @Override
    public Iterator<String> keys() {
        return new BSIterator();
    }
    private class BSIterator implements Iterator<String> {
        List<String> keysList = new ArrayList<>();
        int currentIndex = 0;

        public BSIterator() {
            collectKeys(root, "");
        }

        private void collectKeys(Node<T> node, String prefix){
            if (node == null) return;

            if (node.value != null) {
                //Add to the list of keys the entire key that was collected
                keysList.add(prefix);
            }
            // Recursively collect keys for left and right subtrees
            collectKeys(node.left, prefix + "0");
            collectKeys(node.right, prefix + "1");
        }

        @Override
        public boolean hasNext() {
            return currentIndex < keysList.size();
        }

        @Override
        public String next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return keysList.get(currentIndex++);
        }
    }


    //Easy methods already solved
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
        return get(key) != null;
    }
}
