package anaydis.sandbox.inmutable;

import anaydis.immutable.List;
import org.jetbrains.annotations.NotNull;

public class LinkedList<T> implements List<T>{
    private final T head;
    private final List<T> tail;
    LinkedList(T head, List<T> tail) {
        this.head = head;
        this.tail = tail;
    }
    public T head() {
        return head;
    }
    public List<T> tail() {
        return tail;
    }
    public boolean isEmpty() {
        return false;
    }
    public List<T> reverse() {
        List<T> result = (List<T>) NIL;
        List<T> current = this;
        while (!current.isEmpty()) {
            result = List.cons(current.head(), result);
            current = current.tail();
        }
        return result;
    }

}
