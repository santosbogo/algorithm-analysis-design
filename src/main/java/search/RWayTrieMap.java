package search;

import anaydis.search.Map;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;

public class RWayTrieMap<T> implements Map<String, T> {

    private int size = 0;
    private Node root = null;

    private static class Node<T>{
        public T value;
        private Node<T>[] next = new Node[256];
    }

    @Override
    public T put(String key, T value) {
        if (key == null) throw new NullPointerException();
        Node<T> previous = find(root, key, 0);
        root = put(root, key, value, 0);
        return previous.value;
    }
    private Node<T> put(Node<T> node, String key, T value, int pos) {
        // If the node is null, create a new node
        if (node == null) node = new Node<>();
        //If the position has the same length of the key, then we find the node, replace the old value with the new.
        if (pos == key.length()){
            node.value = value;
            return node;
        }
        //Now, that we know
        else {
            char c = key.charAt(pos);
            //Here when I use [c] I mean the position of the value of the char C that in ASCII is 0<=c<=256
            node.next[c] = put(node.next[c], key, value, pos + 1);
            return node;
        }
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
        return node.value;
    }

    @Override
    public Iterator keys() {
        return null;
    }

    //Easy methods already solved
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
