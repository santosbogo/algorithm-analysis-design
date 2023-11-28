package anaydis.search;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class UnorderedArrayPriorityQueue<T> implements PriorityQueue<T> {
    private T[] array;
    private int size = 0;
    private final Comparator<T> comparator;

    public UnorderedArrayPriorityQueue(Comparator<T> comparator) {
        this.comparator = comparator;
        this.array = (T[]) new Object[5];
    }

    public UnorderedArrayPriorityQueue(Comparator<T> comparator, int initialCapacity) {
        this.comparator = comparator;
        this.array = (T[]) new Object[initialCapacity];
    }

    private void resize(int newSize) {
        T[] temp = (T[]) new Object[newSize];
        System.arraycopy(array, 0, temp, 0, size);
        array = temp;
    }

    @Override
    public void insert(T key) {
        if (size == array.length) {
            resize(size * 2);
        }
        array[size++] = key;
    }

    @Override
    public T pop() {
        if (size == 0) throw new NoSuchElementException();

        int maxIndex = 0;
        for (int i = 1; i < size; i++) {
            if (comparator.compare(array[i], array[maxIndex]) > 0) {
                maxIndex = i;
            }
        }
        T max = array[maxIndex];
        array[maxIndex] = array[size - 1];
        array[size - 1] = null;
        size--;

        if (0.25 * size <= array.length && array.length > 5) {
            resize(Math.max(size, 5)); // asegurar que el nuevo tamaño es al menos igual a size o 5
        }

        return max;
    }

    @Override
    public T peek() {
        if (size == 0) throw new NoSuchElementException();

        T max = array[0];
        for (int i = 1; i < size; i++) {
            if (comparator.compare(array[i], max) > 0) {
                max = array[i];
            }
        }
        return max;
    }

    @Override
    public int size() {
        return size;
    }

    @NotNull
    @Override
    public Iterator<T> iterator() {
        T[] iterArray = Arrays.copyOf(array, size);
        Arrays.sort(iterArray, comparator.reversed());
        return Arrays.stream(iterArray).iterator();
    }
}
