package anaydis.sandbox.sort;

import java.util.Comparator;

public class MergeLinkedList<T> {

    public Node<T> sort(Node<T> list, Comparator<T> comparator){
        if(list == null || list.next == null)
            return list;
        Node<T> a = list;
        for (Node<T> c = list.next; c != null && c.next != null; c = c.next.next){
            list = list.next;
        }
        Node<T> b = list.next;
        list.next = null;
        return merge(a, b, comparator);
    }
    private Node<T> merge(Node <T> a, Node<T> b, Comparator<T> comparator){
        Node<T> c = new Node<>();
        Node<T> result;
        for(result = c; a != null && b != null; c = c.next) {
            int comp = comparator.compare(a.value, b.value);
            if (comp<0){
                c.next = a;
                a = a.next;
            }else {
                c.next = b;
                b = b.next;
            }
        }
        c.next = a == null ? b : a;
        return result.next;
    }

    public static void main(String[] args) {
        Comparator<Integer> comparator = Comparator.naturalOrder();

        // Create nodes for the linked list
        Node<Integer> node3 = new Node<>(3, null);
        Node<Integer> node2 = new Node<>(2, node3);
        Node<Integer> node1 = new Node<>(1, node2);

        // Create an instance of your sorting class
        MergeLinkedList<Integer> sorter = new MergeLinkedList<>();

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
