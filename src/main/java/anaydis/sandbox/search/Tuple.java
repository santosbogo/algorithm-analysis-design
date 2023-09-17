package anaydis.sandbox.search;

public class Tuple <T> {
    private TSTNode<T> node;
    private T value;

    public Tuple(TSTNode<T> node, T value){
        this.node = node;
        this.value = value;
    }

    public TSTNode<T> getNode(){
        return node;
    }

    public T getValue(){
        return value;
    }
}
