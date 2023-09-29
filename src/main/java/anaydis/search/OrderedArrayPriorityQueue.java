package anaydis.search;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class OrderedArrayPriorityQueue<T> implements PriorityQueue<T>{
    private T[] array;
    private int size = 0;
    private Comparator<T> comparator;

    public OrderedArrayPriorityQueue(Comparator<T> comparator){
        this.comparator = comparator;
        this.array = ((T[]) new Object[5]);
    }

    public OrderedArrayPriorityQueue(Comparator<T> comparator, int initialCapacity){
        this.comparator = comparator;
        this.array = ((T[]) new Object[initialCapacity]);
    }

    private void resize(int newsize){
        T[] temp = (T[]) new Object[newsize];
        int index = 0;
        for(T element: array)
            if(element != null)
                temp[index++] = element;

        array = temp;
    }

    @Override
    public void insert(T key) {
        if (key == null) throw new NullPointerException();

        array[size] = key;
        Arrays.sort(array, comparator);

        if(size == array.length)
            resize(size*2);
    }

    @Override
    public T pop() {
        if (size == 0) throw new NoSuchElementException();

        T temp = array[size];
        array[size] = null;
        size--;

        if (0.25*size <= array.length)
            resize(size/2);

        return temp;
    }

    @Override
    public T peek() {
        if (size == 0) throw new NoSuchElementException();
        return array[size];
    }

    @Override
    public int size() {
        return size;
    }

    @NotNull
    @Override
    public Iterator<T> iterator() {
        T[] iterArray = array;
        Arrays.sort(iterArray, comparator.reversed());
        return Arrays.stream(iterArray).iterator();
    }
}
