package anaydis.search.benchmark;

public class Scene {
    private final int size;
    private final String trie;

    public Scene(int size, String trie){
        this.size = size;
        this.trie = trie;
    }

    public String toString(){
        return "Trie= " + trie + " Size= " + size;
    }
}
