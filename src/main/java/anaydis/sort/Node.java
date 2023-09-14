package anaydis.sort;

public class Node<T>{
    Node<T> next;
    T value;

    public Node(T value, Node<T> next){
        this.value = value;
        this.next = next;
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