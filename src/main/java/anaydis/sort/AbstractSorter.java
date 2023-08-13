package anaydis.sort;

import java.util.Comparator;

/**
 * Abstract sorter: all sorter implementations should subclass this class.
 */
abstract class AbstractSorter<T> implements Sorter{

    boolean less(T a, T b, Comparator<T> comparator) {
        return comparator.compare(a,b) < 0;
    }

    void exch(T[] list, int i, int j){
        T temp = list[i];
        list[i] = list[j];
        list[j] = temp;
    }

    void compExch(T[] list, int i, int j, Comparator<T> comparator){
        if (less(list[j], list[i], comparator)){
            exch(list, i, j);
        }
    }
}
