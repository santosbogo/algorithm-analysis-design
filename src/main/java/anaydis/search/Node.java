package anaydis.search;

public class Node<T>{
    public String key;
    public  T value;
    public Node<T> left;
    public Node<T> right;

    public Node(String key, T value){
        this.key = key;
        this.value = value;
        this.right = null;
        this.left = null;
    }


}
