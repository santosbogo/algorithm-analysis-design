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
        private String key;
        private T value = null;
        private Node<T> left = null;
        private Node<T> right = null;
        Node(String key, T value){
            this.key = key;
            this.value = value;
        }
        Node(){
        }
    }

    private boolean bitAt(String s, int nth) {
        final int pos = nth / 8;
        return pos < s.length() && (s.charAt(pos) >> (nth % 8) & 1) != 0;
    }

    private Node<T> split(Node<T> nodeA, Node<T> nodeB, int level){
        Node<T> result = new Node<>();
        int A = bitAt(nodeA.key, level) ? 1 : 0;
        int B = bitAt(nodeB.key, level)? 1 : 0;

        switch (A * 2 + B){
            case 0:
                result.left = split(nodeA, nodeB, level+1);
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
                result.right = split(nodeA, nodeB, level +1);
                break;
        }
        return result;
    }

    private boolean isLeaf(Node<T> node){
        return (node.right == null && node.left == null);
    }

    private Node<T> find(Node<T> node, String key, int level) {
        if (node == null) return null;
        if (isLeaf(node)) return key.equals(node.key) ? node : null;

        if (bitAt(key, level)) return find(node.right, key, level + 1);
        return find(node.left, key, level + 1);
    }


    @Override
    public T get(String key) {
        if (key == null) throw new NullPointerException();
        Node<T> node = find(root, key, 0);
        return node != null ? node.value : null;
    }

    @Override
    public T put(String key, T value) {
        if (key == null) throw new NullPointerException();

        T previous = get(key);
        root = put(root, key, value, 0);

        if (previous == null) {
            size++;
            return null;
        }
        return previous;
    }

    private Node<T> put(Node<T> node, String key, T value, int level) {
        if (node == null) {
            size++;
            return new Node<>(key, value);
        }

        if (level == key.length() * 8) {
            // Reached the end of the key, update the value
            node.value = value;
        } else {
            boolean bit = bitAt(key, level);

            if (bit) {
                node.right = put(node.right, key, value, level + 1);
            } else {
                node.left = put(node.left, key, value, level + 1);
            }
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
