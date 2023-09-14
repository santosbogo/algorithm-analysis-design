package anaydis.sort;

import java.util.Comparator;

public class LinkedMaxSelectionSort<T>{
    public Node<T> sort(Node<T> list, Comparator<T> comparator){
        return null;
    }

    private Node<T> fMin(Node<T> node, Comparator<T> comparator){
        while (node.hasNext()){
            Node<T> next = node.next;
            int comp = comparator.compare(node.value, next.value);
            if (comp > 0) node = node.next;
            //else we dont need because node is lower or equal
        }
        return node;
    }

    private void remove(Node<T> node){

    }

    public static void main(String[] args) {
    }
}
