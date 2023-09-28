package anaydis.sandbox.sort;

public class Node<T>{
    Node<T> next;
    T value;

    public Node(T value, Node<T> next){
        this.value = value;
        this.next = next;
    }

    public Node(){
        this.next = null;
        this.value = null;
    }

    public void newNext (Node<T> node){
        node.next = node;
    }

    public Node<T> create(T value, Node<T> next){
        return new Node<>(value, next);
    }

    public boolean hasNext(){
        return this.next != null;
    }

}