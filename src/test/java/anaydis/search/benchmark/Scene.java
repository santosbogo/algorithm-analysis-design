package anaydis.search.benchmark;

import anaydis.search.Map;

public class Scene {
    private final int size;
    private final Map<String, Integer> trie;

    public Scene(int size, Map<String, Integer> trie){
        this.size = size;
        this.trie = trie;
    }

    public String toString(){
        return trie.toString() + " Size= " + size;
    }
}
