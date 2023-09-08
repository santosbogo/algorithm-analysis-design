package anaydis.search;

import org.jetbrains.annotations.NotNull;

import java.util.Iterator;

public class BinaryTrieMap<T> implements Map<String, T>{
    Node <T> root = null;
    int size;

    private class Node<T> {
        private String key;
        private T value = null;
        private Node<T> left = null;
        private Node<T> right = null;
    }

    private boolean isLeaf(Node<T> node){
        return (node.left == null && node.right == null);
    }

    private Node<T> find (Node<T> node){
        return null;
    }

    @Override
    public T get(@NotNull String key) {
        return null;
    }

    @Override
    public T put(@NotNull String key, T value) {
        return null;
    }

    //Easy methods already solved
    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    @Override
    public Iterator<String> keys() {
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
}
