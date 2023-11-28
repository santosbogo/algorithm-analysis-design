package anaydis.search;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class OrderedArrayPriorityQueue<T> implements PriorityQueue<T> {
    private T[] array;
    private int size = 0;
    private final Comparator<T> comparator;

    public OrderedArrayPriorityQueue(Comparator<T> comparator) {
        this.comparator = comparator;
        this.array = (T[]) new Object[5];
    }

    public OrderedArrayPriorityQueue(Comparator<T> comparator, int initialCapacity) {
        this.comparator = comparator;
        this.array = (T[]) new Object[initialCapacity];
    }

    private void resize(int newSize) {
        T[] temp = (T[]) new Object[newSize];
        System.arraycopy(array, 0, temp, 0, size);
        array = temp;
    }

    private void ensureCapacity() {
        if (size == array.length) {
            resize(size * 2);
        }
    }

    @Override
    public void insert(T key) {
        if (key == null) throw new NullPointerException();

        ensureCapacity();

        int i = size - 1;
        while (i >= 0 && comparator.compare(array[i], key) > 0) {
            array[i + 1] = array[i];
            i--;
        }
        array[i + 1] = key;
        size++;
    }

    @Override
    public T pop() {
        if (size == 0) throw new NoSuchElementException();

        T temp = array[size - 1];
        array[size - 1] = null;
        size--;

        if (0.25 * size <= array.length && array.length > 5) {
            resize(Math.max(size, 5)); // asegurar que el nuevo tamaño es al menos igual a size o 5
        }

        return temp;
    }

    @Override
    public T peek() {
        if (size == 0) throw new NoSuchElementException();
        return array[size - 1];
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
