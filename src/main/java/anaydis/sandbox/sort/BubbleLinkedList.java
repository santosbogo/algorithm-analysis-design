package anaydis.sandbox.sort;

import java.util.Comparator;

public class BubbleLinkedList<T> {

    public Node<T> sort(Node<T> list, Comparator<T> comparator){
        Node<T> out = null;
        Node<T> aux = new Node<>(null, list);
        Node<T> previous;
        while(aux.next.next!=null){
            previous = aux;
            while(previous.next.next!=null) {
                if (comparator.compare(previous.next.value, previous.next.next.value) > 0) {
                    exchange(previous);
                }
                previous=previous.next;
            }
            previous.next.next=out;
            out=previous.next;
            previous.next=null;
        }
        aux.next.next=out;
        return aux.next;
    }

    private void exchange(Node<T> x){
        Node<T> m = x.next;
        Node<T> n = x.next.next;
        x.next = n;
        m.next = n.next;
        n.next = m;
    }

    public static void main(String[] args) {
        Comparator<Integer> comparator = Comparator.naturalOrder();

        // Create nodes for the linked list
        Node<Integer> node3 = new Node<>(3, null);
        Node<Integer> node2 = new Node<>(2, node3);
        Node<Integer> node1 = new Node<>(1, node2);

        // Create an instance of your sorting class
        BubbleLinkedList<Integer> sorter = new BubbleLinkedList<>();

        // Sort the linked list
        Node<Integer> sortedList = sorter.sort(node1, comparator);

        // Print the sorted linked list
        Node<Integer> current = sortedList;
        while (current != null) {
            System.out.print(current.value + " ");
            current = current.next;
        }
    }
}
