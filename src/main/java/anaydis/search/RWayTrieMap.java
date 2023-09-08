package anaydis.search;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class RWayTrieMap<T> implements Map<String, T> {

    private int size = 0;
    private Node<T> root = null;

    private static class Node<T>{
        public T value;
        private Node<T>[] next = new Node[256];
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
    private Node<T> put(Node<T> node, String key, T value, int pos) {
        // If the node is null, create a new node
        if (node == null) node = new Node<>();
        //If the position has the same length of the key, then we find the node, replace the old value with the new.
        if (pos == key.length()) node.value = value;
        //Now, that we know
        else {
            char c = key.charAt(pos);
            //Here when I use [c] I mean the position of the value of the char C that in ASCII is 0<=c<=256
            if (node.next[c] == null) node.next[c] = new Node<>();
            node.next[c] = put(node.next[c], key, value, pos + 1);
        }
        return node;
    }

    private Node<T> find(Node<T> node, String key, int pos){
        if (node == null) return null;
        if (pos == key.length()) return node;
        char c = key.charAt(pos);
        return find(node.next[c], key, pos+1);
    }

    @Override
    public T get(String key) {
        if (key == null) throw new NullPointerException();
        Node<T> node = find(root, key, 0);
        return node == null ? null : node.value;
    }

    @Override
    public Iterator keys() {
        return new RWayIterator();
    }
    private class RWayIterator implements Iterator<String> {
        List<String> keysList = new ArrayList<>();
        private int currentIndex = 0;

        public RWayIterator() {
            collectKeys(root, "");
        }

        private void collectKeys(Node<T> node, String prefix) {
            if (node == null) return;
            if (node.value != null) {
                //Add to the list of keys the entire key that was collected
                keysList.add(prefix);
            }
            for (char c = 0; c < 256; c++) {
                collectKeys(node.next[c], prefix + c);
            }
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
        root = null;;
        size = 0;
    }
}
