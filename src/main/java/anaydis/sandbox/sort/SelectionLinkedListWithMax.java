package anaydis.sandbox.sort;

import java.util.Comparator;

public class SelectionLinkedListWithMax<T>{
    public Node<T> sort(Node<T> list, Comparator<T> comparator){

        Node<T> sortedList = null;
        Node<T> max;

        while(list != null){
            max = findMax(list, comparator);
            list = remove(list, max);
            max.next = sortedList;
            sortedList = max;

        }

        return sortedList;
    }

    private Node<T> findMax(Node<T> node, Comparator<T> comparator){

        Node<T> max = node;

        while (node != null) {
            if (comparator.compare(node.value, max.value) > 0) max = node;
            node = node.next;
        }

        return max;
    }

    private Node<T> remove(Node<T> list, Node<T> node){
        if (list == null) return null;
        if (list == node) return list.next;
        list.next = remove(list.next, node);
        return list;
    }


    public static void main(String[] args) {
        Comparator<Integer> comparator = Comparator.naturalOrder();

        // Create nodes for the linked list
        Node<Integer> node3 = new Node<>(3, null);
        Node<Integer> node2 = new Node<>(2, node3);
        Node<Integer> node1 = new Node<>(1, node2);

        // Create an instance of your sorting class
        SelectionLinkedListWithMax<Integer> sorter = new SelectionLinkedListWithMax<>();

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
