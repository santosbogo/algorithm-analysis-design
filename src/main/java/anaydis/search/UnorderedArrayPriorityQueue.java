package anaydis.search;

import org.jetbrains.annotations.NotNull;

import java.util.Comparator;
import java.util.Iterator;

public class UnorderedArrayPriorityQueue<T> implements PriorityQueue<T>{
    private T[] array;
    private int size = 0;
    private Comparator<T> comparator;

    public UnorderedArrayPriorityQueue(Comparator<T> comparator){
        this.comparator = comparator;
        this.array = ((T[]) new Object[5]);
    }

    public UnorderedArrayPriorityQueue(Comparator<T> comparator, int initialCapacity){
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
        array[size] = key;
        size++;

        if(size == array.length)
            resize(size*2);
    }

    @Override
    public T pop() {
        T max = array[0];
        int i;
        for(i = 1; i < size; i++){
            if(comparator.compare(array[i], max) > 0)
                max = array[i];
        }
        array[i] = null;
        size--;

        if (0.25*size <= array.length)
            resize(size/2);

        return max;
    }

    @Override
    public T peek() {
        T max = array[0];
        int i;
        for(i = 1; i < size; i++){
            if(comparator.compare(array[i], max) > 0)
                max = array[i];
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
        return new UnorderedArrayPriorityQueueIterator();
    }

    private class UnorderedArrayPriorityQueueIterator implements Iterator<T>{

        T[] iterArray = array;
        int iterSize = size;

        @Override
        public boolean hasNext() {
            T max = iterArray[0];
            int i;
            for(i = 1; i < iterSize; i++){
                if(comparator.compare(iterArray[i], max) > 0)
                    max = iterArray[i];
            }

            return  max != null;
        }

        @Override
        public T next() {
            T max = iterArray[0];
            int i;
            for(i = 1; i < iterSize; i++){
                if(comparator.compare(iterArray[i], max) > 0)
                    max = iterArray[i];
            }
            iterArray[i] = null;
            iterSize--;

            return max;
        }
    }
}
