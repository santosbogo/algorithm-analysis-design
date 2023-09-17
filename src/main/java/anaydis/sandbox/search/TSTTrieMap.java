package anaydis.sandbox.search;

import anaydis.search.Map;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TSTTrieMap <T> implements Map<String, T> {
    int size = 0;
    TSTNode<T> root = null;

    private TSTNode<T> find(TSTNode<T> node, String key, int level) {
        if (root == null) return null;

        char c = key.charAt(level);

        if (c > node.c) return find(node.left, key, level);
        else if (c < node.c) return find(node.right, key, level);
            //Now we found the next char if the key (c == node.c). We need to ask if it is the las char of the key.
            // key.length() - 1 is because when we are at that level means it is the last character of the key, so we want that node
        else if (level < key.length() - 1) return find(node.middle, key, level + 1);
        return node;
    }

    @Override
    public boolean containsKey(String key) {
        if (key == null) throw new NullPointerException();
        return get(key) != null;
    }

    @Override
    public T get(String key) {
        if (key == null) throw new NullPointerException();
        TSTNode<T> node = find(root, key, 0);
        return ((node != null) && (node.value != null)) ? node.value : null;
    }

    @Override
    public T put(String key, T value) {
        if (key == null) throw new NullPointerException();
        Tuple<T> tuple = put(root, key, value, 0);
        root = tuple.getNode();
        return tuple.getValue();
    }

    private Tuple<T> put(TSTNode<T> node, String key, T value, int level) {
        char c = key.charAt(level);
        T oldValue = null;

        if (node == null) {
            node = new TSTNode<>();
            node.c = c;
            return new Tuple<>(node, null);
        }


        if (c < node.c) node.left = put(node.left, key, value, level).getNode();
        else if (c > node.c) node.right = put(node.right, key, value, level).getNode();
        else if (level < key.length() - 1) node.middle = put(node.middle, key, value, level + 1).getNode();
        else {
            if (node.value != null) {
                oldValue = node.value;
            } else size++;
            node.value = value;
        }

        return new Tuple<>(node, oldValue);
    }

    @Override
    public Iterator<String> keys() {
        List<String> keys = new ArrayList<>(size);
        getKeys(root, "", keys);
        return keys.iterator();
    }

    private void getKeys(TSTNode<T> node, String prefix, List<String> keys) {
        if (node == null) return;

        if (node.value != null) keys.add(prefix + node.c);

        getKeys(node.left, prefix, keys);

        getKeys(node.right, prefix, keys);

        getKeys(node.middle, prefix + node.c, keys);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        size = 0;
        root = null;
    }

}
// TODO
//    public List<String> autocomplete(String pattern){
//        final List<String> result = new ArrayList<>();
//        autocomplete(root, pattern, 0, "", result);
//        return result;
//    }
//    private void autocomplete(anaydis.search.TSTTrieMap.Node<T> node, String pattern, int level, String match, List<String> result){
//        //1ero llegar al ultimo nodo de pattern
//        //2do recorrer el nodo desde node.next.right y node.next.left
//        char c = pattern.charAt(level);
//        if (match.length() < pattern.length()) {
//            if (c < node.c) {
//                autocomplete(node.left, pattern, level, match, result);
//                return;
//            } else if (c > node.c) {
//                autocomplete(node.right, pattern, level, match, result);
//                return;
//            } else if (level < pattern.length() - 1) {
//                match += c;
//                autocomplete(node.mid, pattern, level + 1, match, result);
//            }
//        }
//        else {
//            if(node == null) return;
//            //Ya encontre mi nodo
//            if (node.value != null) {
//                result.add(match + node.c);
//                return;
//            }
//            autocomplete(node.left, pattern, level, match, result);
//            autocomplete(node.mid, pattern, level+1, match + node.c, result);
//            autocomplete(node.right, pattern, level, match, result);
//        }
//    }
//    public static void main(String[] args) {
//        final anaydis.search.TSTTrieMap<Integer> map = new anaydis.search.TSTTrieMap<>();
//        final String[] keys = {"lucas", "lupani", "lupin", "luz", "luana"};
//        Iterator<String> wordsIter = Arrays.stream(keys).iterator();
//        int value = 0;
//        while (wordsIter.hasNext()){
//            map.put(wordsIter.next(), value++);
//        }
//        System.out.println(map.autocomplete("lu"));
//    }
//}
