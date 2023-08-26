package anaydis.sort;

import org.jetbrains.annotations.NotNull;

import java.util.Comparator;
import java.util.List;

public class HSorter extends AbstractSorter {

    public HSorter() {
        super(SorterType.H);
    }

    public <T> void sort(Comparator<T> comparator, List<T> list){
        sort(comparator, list, 1);
    }

    /**
     * H-Sort list. Basically a BubbleSort in sets of elements separated by h
     */
    public <T> void sort(Comparator<T> comparator, List<T> list, int h) {
        int size = list.size();
        for(int i = 0; i < size - h; i++) {
            for (int j = 0; j < size - i - h; j += h) { //Size - i - h It is for not compare ordered items
                notifyBox(j, j + h);
                if (less(list, j + h, j, comparator)){ // If j+1 < j -> exchange
                    exch(list, j, j + h);
                }
            }
        }
    }
        @Override
    public @NotNull SorterType getType() {
        return SorterType.H;
    }
}
