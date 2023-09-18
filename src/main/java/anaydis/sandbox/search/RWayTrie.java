package anaydis.sandbox.search;

import anaydis.search.Map;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class RWayTrie<T> implements Map<String, T> {

    int size = 0;
    RNode<T> root = null;
    T oldValue;

    private RNode<T> find(RNode<T> node, String key, int level){
        if (node == null) return null;
        if (level == key.length()) return node;
        char c = key.charAt(level);
        return find(node.next[c], key,level+1);
    }

    @Override
    public T get(String key) {
        if (key == null) throw new NullPointerException();
        RNode<T> node = find(root, key, 0);
        return  node != null ? node.value : null;
    }

    @Override
    public T put(@NotNull String key, T value) {
        return null;
    }
    private RNode<T> put(RNode<T> node, String key, T value, int level){
        if (node == null) node = new RNode<>();

        if (level == key.length()){
            oldValue = node.value;
            node.value = value;
            if(oldValue == null){
                size++;
            }
            return node;
        }

        char c = key.charAt(level);
        if(node.next[c] == null) node.next[c] = new RNode<>();
        node.next[c] = put(node.next[c], key, value, level + 1);
        return node;
    }

    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    @Override
    public Iterator<String> keys() {
        List<String> keys = new ArrayList<>();
        getKeys(root, keys, "");
        return keys.iterator();
    }
    private void getKeys(RNode<T> node, List<String> keys, String prefix){
        if(node == null) return;

        if (node.value != null) keys.add(prefix);

        for(int i = 0; i < 256; i++){
            getKeys(node.next[i], keys, prefix);
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean containsKey(String key) {
        if (key == null) throw new NullPointerException();
        return get(key) != null;
    }
}
