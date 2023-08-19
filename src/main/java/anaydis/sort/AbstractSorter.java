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
        listeners.forEach(l -> l.equals(j, i));
    }
}
