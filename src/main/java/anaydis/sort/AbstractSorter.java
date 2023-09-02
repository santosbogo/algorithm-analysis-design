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

    AbstractSorter(@NotNull final SorterType type) {
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

    //I WANT TO IMPLEMENT THIS PARTITION BUT I CANT UDERSTAND WHY DOES NOT WORK IN TEAM CITY
     int NEWpartition(List<T> list, Comparator<T> comparator, int low, int high) {
        int a = low; //Left pointer
        int b = high - 1; //Right pointer
         //high is the pivot position (last position)

        while(a < b){
            //Moving the left pointer to the right until it finds a bigger than the pivot
            while(less(list, a, high, comparator)){
                notifyBox(a, high);
                a++;
                if (a == b) break;
            }
            //Moving the right pointer to the left until it finds a lower than the pivot
            while (!less(list, b, high, comparator)){
                notifyBox(b, high);
                b--;
                if (a == b) break;
            }

            exch(list, a, b);

        }
        exch(list, high, a); //Move the pivot to the partitioning position
        return a; //Partitioning position, the left elements are all less and the right elements are all big
    }

    int partition(List<T> list, Comparator<T> comparator, int low, int high) {
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (less(list, j, high, comparator)) {
                i++;
                exch(list, i, j);
            }
        }
        exch(list, i + 1, high);
        return i + 1; // This value is a position, the left elements are all less and the right elements are all big
    }



// I CAN NOT MAKE THE NOTIFIERS WORK
    void merge(Comparator<T> comparator, List<T> list, List<T> temp, int low, int mid, int high){
        int i = low;
        int j = mid + 1;

        for (int k = low; k <= high; k++) {
            temp.set(k, list.get(k));
            notifyCopy(k, k, true);
        }


        for (int k = low; k <= high; k++) {
            if (i > mid) {
                list.set(k, temp.get(j++));
                notifyCopy(j-1, k, false);
                notifySwap(j-1, k);
            } else if (j > high) {
                list.set(k, temp.get(i++));
                notifyCopy(i-1, k, false);
                notifySwap(i-1, k);
            } else if (less(temp, j, i, comparator)) {
                list.set(k, temp.get(j++));
                notifyCopy(j-1, k, false);
            } else {
                list.set(k, temp.get(i++));
                notifyCopy(i-1, k, false);
            }
            if (i != k && j != k) {
                notifySwap(i, j);
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
