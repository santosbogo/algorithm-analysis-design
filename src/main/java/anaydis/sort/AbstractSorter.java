package anaydis.sort;

import java.util.Comparator;
import java.util.List;

/**
 * Abstract sorter: all sorter implementations should subclass this class.
 */
abstract class AbstractSorter<T> implements Sorter{

    boolean less(T a, T b, Comparator<T> comparator) {
        return comparator.compare(a,b) < 0;
    }

    void exch(List<T> list, int i, int j){
        T temp = list.get(i);
        list.set(i, list.get(j));
        list.set(j, temp);
    }

    void compExch(List<T> list, int i, int j, Comparator<T> comparator){
        if (less(list.get(i), list.get(j), comparator)){
            exch(list, i, j);
        }
    }


}
