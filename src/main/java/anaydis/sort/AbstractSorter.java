package anaydis.sort;


import anaydis.sort.gui.ObservableSorter;
import org.jetbrains.annotations.NotNull;
import anaydis.sort.gui.SorterListener;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

/**
 * Abstract sorter: all sorter implementations should subclass this class.
 */
abstract class AbstractSorter<T> implements ObservableSorter{

    private final List<SorterListener> listeners;
    private final SorterType type;

    AbstractSorter(@NotNull final SorterType type) {
        this.type = type;
        listeners = new LinkedList<>();
    }

    boolean less(List<T> list, int a, int b, Comparator<T> comparator) {
        boolean result = comparator.compare(list.get(a), list.get(b)) < 0;
        notifyLess(a, b);
        return result;
    }

    void exch(List<T> list, int i, int j) {
        T temp = list.get(i);
        list.set(i, list.get(j));
        list.set(j, temp);
        notifySwap(i, j);
    }

    void compExch(List<T> list, int i, int j, Comparator<T> comparator) {
        if (less(list, i, j, comparator)) {
            exch(list, i, j);
        }
    }

    int partition(List<T> list, Comparator<T> comparator, int low, int high) {
        int a = low; //Left pointer
        int b = high; //Right pointer

        while(a < b){
            //Moving the left pointer to the right until it finds a bigger than the pivot
            while(less(list, a, high, comparator)){
                a++;
                if (a == b) break;
            }
            //Moving the right pointer to the left until it finds a lower than the pivot
            while (!less(list, b, high, comparator)){
                notifyGreater(a, b);
                --b;
                if (a == b) break;
            }

            exch(list, a, b);
        }
        exch(list, a, high); //Move the pivot to the partitioning position
        return a; //Partitioning position, the left elements are all less and the right elements are all big
    }

    void merge(Comparator<T> comparator, List<T> list, List<T> temp, int low, int mid, int high){
        int i = low;
        int j = mid + 1;

        for (int k = low; k <= high; k++) {
            temp.set(k, list.get(k));
//            notifyCopy(k, k, true);
        }


        for (int k = low; k <= high; k++) {
            if (i > mid) {
                list.set(k, temp.get(j++));
                notifyCopy(k, j - 1, false);
            } else if (j > high) {
                list.set(k, temp.get(i++));
                notifyCopy(k, i - 1, false);
            } else if (less(temp, j, i, comparator)) {
                list.set(k, temp.get(j++));
                notifyCopy(k, j - 1, false);
            } else {
                list.set(k, temp.get(i++));
                notifyCopy(k, i -1, false);
            }
        }
    }


    //Listener Methods
    public void addSorterListener(@NotNull final SorterListener listener) {
        listeners.add(listener);
    }

    public void removeSorterListener(@NotNull final SorterListener listener) {
        listeners.remove(listener);
    }

    void notifyBox(final int from, final int to) {
        listeners.forEach(l -> l.box(from, to));
    }

    void notifySwap(final int i, final int j) {
        listeners.forEach(l -> l.swap(i, j));
    }

    private void notifyLess(final int i, final int j) {
        listeners.forEach(l -> l.greater(i, j));
    }

    private void notifyGreater(final int i, final int j){
        listeners.forEach(l -> l.greater(j, i));
    }

    void notifyCopy(final int i, final int j, boolean fromSource){
        listeners.forEach(l -> l.copy(i, j, fromSource));
    }

}
