package anaydis.search;

import org.jetbrains.annotations.NotNull;

import java.util.Iterator;

public class TSTTrieMap<T> implements Map<String, T> {

    Node<T> root = null;
    int size = 0;

    private class Node<T>{
        char c;
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
        return node.value;
    }

    @Override
    public T put(@NotNull String key, T value) {
        return null;
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
        return null;
    }


}
