package anaydis.priorityqueue;

import anaydis.search.PriorityQueue;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class HeapPriorityQueue<T> implements PriorityQueue<T> {
    private final ArrayList<T> heap;
    private final Comparator<T> comparator;

    public HeapPriorityQueue(Comparator<T> comparator) {
        this.heap = new ArrayList<>();
        this.comparator = comparator;
    }

    @Override
    public void insert(T key) {
        heap.add(key);
        swim(heap.size() - 1); // maintain heap invariant
    }

    @Override
    public T pop() {
        if (isEmpty()) throw new NoSuchElementException();

        T max = heap.get(0);
        int lastIndex = heap.size() - 1;
        swap(0, lastIndex);
        heap.remove(lastIndex);

        if (!isEmpty()) sink(0); // maintain heap invariant

        return max;
    }

    @Override
    public T peek() {
        if (isEmpty()) throw new NoSuchElementException();
        return heap.get(0);
    }

    @Override
    public int size() {
        return heap.size();
    }

    @NotNull
    @Override
    public Iterator<T> iterator() {
        return heap.iterator();
    }

    private void swim(int k) {
        while (k > 0 && less(parent(k), k)) {
            swap(parent(k), k);
            k = parent(k);
        }
    }

    private void sink(int k) {
        while (2*k + 1 < heap.size()) {
            int j = 2*k + 1; // left child
            if (j < heap.size() - 1 && less(j, j + 1)) j++; // use right child if larger
            if (!less(k, j)) break;

            swap(k, j);
            k = j;
        }
    }

    private boolean less(int i, int j) {
        return comparator.compare(heap.get(i), heap.get(j)) < 0;
    }

    private void swap(int i, int j) {
        T temp = heap.get(i);
        heap.set(i, heap.get(j));
        heap.set(j, temp);
    }

    private int parent(int k) {
        return (k - 1) / 2;
    }
}
