package anaydis.inmutable;

import anaydis.immutable.List;
import org.jetbrains.annotations.NotNull;

public class Node<T> implements List<T> {
    private final T head;
    private final List<T> tail;

    Node(T head, List<T> tail) {
        this.head = head;
        this.tail = tail;
    }

    @Override
    public T head() {
        return head;
    }

    @NotNull
    @Override
    public List<T> tail() {
        return tail;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @NotNull
    @Override
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