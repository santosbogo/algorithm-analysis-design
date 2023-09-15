package anaydis.search;

import org.jetbrains.annotations.NotNull;

import java.util.*;

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
    private Node<T> put(Node<T> node, String key, T value, int level){
        // c is the character of the key we want to put
        char c = key.charAt(level);
        // If the node is null, then create a new node and put the character there
        if (node == null) {
            node = new Node<>();
            node.c = c;
        }
        //Check if i need to go to the left, mid or right of the node
        if      (c < node.c) node.left  = put(node.left,  key, value, level);
        else if (c > node.c) node.right = put(node.right, key, value, level);
        //c is the same character that the node, so put it in mid
        else if (level < key.length() - 1) node.mid = put(node.mid, key, value, level+1);
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

    public String toString(){
        return "TST Trie";
    }

    public List<String> autocomplete(String pattern){
        final List<String> result = new ArrayList<>();
        autocomplete(root, pattern, 0, "", result);
        return result;
    }
    private void autocomplete(Node<T> node, String pattern, int level, String match, List<String> result){
        //1ero llegar al ultimo nodo de pattern
        //2do recorrer el nodo desde node.next.right y node.next.left
        char c = pattern.charAt(level);

        if (match.length() < pattern.length()) {
            if (c < node.c) {
                autocomplete(node.left, pattern, level, match, result);
                return;
            } else if (c > node.c) {
                autocomplete(node.right, pattern, level, match, result);
                return;
            } else if (level < pattern.length() - 1) {
                match += c;
                autocomplete(node.mid, pattern, level + 1, match, result);
            }
        }
        else {
            if(node == null) return;
            //Ya encontre mi nodo
            if (node.value != null) {
                result.add(match + node.c);
                return;
            }
            autocomplete(node.left, pattern, level, match, result);
            autocomplete(node.mid, pattern, level+1, match + node.c, result);
            autocomplete(node.right, pattern, level, match, result);
        }
    }

    public static void main(String[] args) {
        final TSTTrieMap<Integer> map = new TSTTrieMap<>();
        final String[] keys = {"lucas", "lupani", "lupin", "luz", "luana"};

        Iterator<String> wordsIter = Arrays.stream(keys).iterator();
        int value = 0;
        while (wordsIter.hasNext()){
            map.put(wordsIter.next(), value++);
        }

        System.out.println(map.autocomplete("lu"));
    }
}
