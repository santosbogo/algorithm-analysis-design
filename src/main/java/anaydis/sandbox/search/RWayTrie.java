package anaydis.sandbox.search;

import anaydis.search.Map;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
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
    public T put(String key, T value) {
        if (key == null) throw new NullPointerException();
        root = put(root, key, value, 0);
        return oldValue;
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
            getKeys(node.next[i], keys, prefix + (char)i);
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


    public List<String> autocomplete(String pattern){
        final List<String> keys = new ArrayList<>();
        autocomplete(root, keys, pattern, "", 0);
        return keys;
    }
    private void autocomplete(RNode<T> node, List<String> keys, String pattern, String match, int level){
        if(node == null) return;

        if(level < pattern.length()){
            char c = pattern.charAt(level);
            autocomplete(node.next[c], keys, pattern, match + c, level + 1);
        }
        else{
            if(node.value != null) keys.add(match);
            for (char c = 0; c < 256; c++)
                autocomplete(node.next[c], keys, pattern, match + c, level + 1);
        }
    }

//    public String longestPrefixOf(String pattern){
//        return longestPrefixOf(root, pattern, "", "", 0);
//    }
//    private String longestPrefixOf(TSTNode<T> node, String pattern, String match, String lastKey, int level){
//        if (node == null) return lastKey;
//
//        char c = pattern.charAt(level);
//
//        if(c < node.c) return longestPrefixOf(node.left, pattern, match, lastKey, level);
//        else if (c > node.c) return longestPrefixOf(node.right, pattern, match, lastKey, level);
//        else{
//            if (node.value != null) lastKey = match + node.c;
//            if (level < pattern.length() - 1)
//                return longestPrefixOf(node.middle, pattern, match + node.c, lastKey, level + 1);
//        }
//        return lastKey;
//    }

    public static void main(String[] args) {

        //AUTOCOMPLETE
        System.out.println("\nAutocomplete:\nExpected: lupani, lupin");
        RWayTrie<Integer> map = new RWayTrie<>();
        final String[] keys = {"lucas", "lupani", "lupin", "luz", "luana", "tractor", "laura"};
        Iterator<String> wordsIter = Arrays.stream(keys).iterator();
        int value = 0;
        while (wordsIter.hasNext()){
            map.put(wordsIter.next(), value++);
        }
        System.out.println(map.autocomplete("lup"));

//        //LONGEST PREFIX OF
//        System.out.println("\nLongest prefix of:\nExpected: she, shells");
//        map = new TSTTrie<>();
//        final String[] keys2 = {"she", "sells", "sea", "shells", "by", "the", "sea", "shore"};
//        wordsIter = Arrays.stream(keys2).iterator();
//        value = 0;
//        while (wordsIter.hasNext()){
//            map.put(wordsIter.next(), value++);
//        }
//        System.out.println(map.longestPrefixOf("shell"));
//        System.out.println(map.longestPrefixOf("shellsort"));
//
//        //KEYS THAT MATCH
//        System.out.println("\nKeys that match:\nExpected: she, the");
//        map = new TSTTrie<>();
//        final String[] keys3 = {"she", "sells", "sea", "shells", "by", "the", "sea", "shore"};
//        wordsIter = Arrays.stream(keys3).iterator();
//        value = 0;
//        while (wordsIter.hasNext()){
//            map.put(wordsIter.next(), value++);
//        }
//        System.out.println(map.wildcard(".he") + "\nExpected: sea, she");
//        System.out.println(map.wildcard("s..") + "\nExpected: sells, shore");
//        System.out.println(map.wildcard("....."));
//

    }

}
