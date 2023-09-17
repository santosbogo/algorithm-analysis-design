package anaydis.sandbox.sort;

import java.util.Comparator;

public class SelectionLinkedListWithMin<T> {

    public Node<T> sort(Node<T> list, Comparator<T> comparator){

        Node<T> sortedList = findMin(list, comparator);
        list = remove(list, sortedList);

        while(list != null){
            sortedList.next = findMin(list, comparator);
            list = remove(list, sortedList);
        }
        return sortedList;
    }

    private Node<T> findMin(Node<T> node, Comparator<T> comparator){

        Node<T> min = node;

        while(node != null){
            if(comparator.compare(node.value, min.value) > 0) min = node;
            node = node.next;
        }

        return min;
    }

    private Node<T> remove(Node<T> list, Node<T> node){
        if(list == null) return null;
        if(list == node) return list.next;
        list.next = remove(list.next, node);
        return list;
    }
}
