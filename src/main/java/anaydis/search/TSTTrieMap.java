package anaydis.search;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class TSTTrieMap<T> implements Map<String, T> {

    Node<T> root = null;
    int size = 0;

    private class Node<T>{
        private char c;
        private Node<T> left;
        private Node<T> mid;
        private Node<T> right;
        private T value;
    }

    private Node<T> find (Node<T> node, String key, int pos){
        if (node == null) return null;
        char c = key.charAt(pos);
        if      (c < node.c) return find(node.left,  key, pos);
        else if (c > node.c) return find(node.right, key, pos);
        else if (pos < key.length() - 1) return find(node.mid, key, pos + 1);
        else return node;
    }

    @Override
    public T get(String key) {
        if (key == null) throw new NullPointerException();
        Node<T> node = find(root, key, 0);
        return node == null ? null : node.value;
    }

    @Override
    public T put(String key, T value) {
        if (key == null) throw new NullPointerException();

        T previous = get(key);
        root = put(root, key, value, 0);

        if (previous == null) {
            size ++;
            return null;
        }
        return previous;
    }
    private Node<T> put(Node<T> node, String key, T value, int pos){
        // c is the character of the key we want to put
        char c = key.charAt(pos);
        // If the node is null, then create a new node and put the character there
        if (node == null) {
            node = new Node<>();
            node.c = c;
        }
        //Check if i need to go to the left, mid or right of the node
        if      (c < node.c) node.left  = put(node.left,  key, value, pos);
        else if (c > node.c) node.right = put(node.right, key, value, pos);
        //c is the same character that the node, so put it in mid
        else if (pos < key.length() - 1) node.mid = put(node.mid, key, value, pos+1);
        //Put the value in the last node because !(pos < key.length() - 1)
        else node.value = value;

        return node;
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

    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    @Override
    public Iterator<String> keys() {
        return new TSTIterator();
    }
    private class TSTIterator implements Iterator<String> {
        List<String> keysList = new ArrayList<>();
        private int currentIndex = 0;

        public TSTIterator() {
            collectKeys(root, "");
        }

        private void collectKeys(Node<T> node, String prefix) {
            if (node == null) return;
            collectKeys(node.left, prefix);
            if (node.value != null) {
                keysList.add(prefix + node.c);
            }
            collectKeys(node.mid, prefix + node.c);
            collectKeys(node.right, prefix);
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


}
