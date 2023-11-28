package anaydis.sandbox.search;

import anaydis.search.PriorityQueue;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.function.Consumer;

public class PriorityQueueHeap<T> implements PriorityQueue<T> {

    private T[] array;
    private int size = 0;
    private final Comparator<T> comparator;

    public PriorityQueueHeap(T[] array, Comparator<T> comparator) {
        this.array = array;
        this.comparator = comparator;
    }


    private void resize(int newSize){
        T[] temp = (T[]) new Object[newSize];

        for (int i = 0; i < size; i++){
            temp[i] = array[i];
        }
        array = temp;

    }

    @Override
    public void insert(T key) {
        if(array.length == size)
            resize(size * 2);

        array[size++] = key;
    }

    @Override
    public T pop() {
        if (size == 0) throw new NoSuchElementException();

        int maxIndex = 0;

        for (int i = 1; i < size; i++)
            if (comparator.compare(array[i], array[maxIndex]) > 0)
                maxIndex = i;

        T max = array[maxIndex];
        array[maxIndex] = array[--size];
        array[size] = null;

        if(0.25 * size <= array.length)
            resize(size/2);

        return max;
    }

    @Override
    public T peek() {
        if (size == 0) throw new NoSuchElementException();

        int maxIndex = 0;

        for (int i = 1; i < size; i++)
            if (comparator.compare(array[i], array[maxIndex]) > 0)
                maxIndex = i;

        return array[maxIndex];
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return PriorityQueue.super.isEmpty();
    }

    @NotNull
    @Override
    public Iterator<T> iterator() {
        T[] iterArray = Arrays.copyOf(array, size);
        Arrays.sort(iterArray, comparator.reversed());
        return Arrays.stream(iterArray).iterator();
    }

    @Override
    public void forEach(Consumer<? super T> action) {
        PriorityQueue.super.forEach(action);
    }

    @Override
    public Spliterator<T> spliterator() {
        return PriorityQueue.super.spliterator();
    }
}
