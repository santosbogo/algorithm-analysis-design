package anaydis.sort;

import org.jetbrains.annotations.NotNull;

import java.util.Comparator;
import java.util.List;

public class HSorter extends AbstractSorter {

    public HSorter() {
        super(SorterType.H);
    }

    public <T> void sort(Comparator<T> comparator, List<T> list){
        sort(comparator, list, 4);
    }

    /**
     * H-Sort list. Basically a BubbleSort in sets of elements separated by h
     */
    public <T> void sort(Comparator<T> comparator, List<T> list, int h)
    {
        int size = list.size();
        for(int i = 0; i < size - h; i++){
            int temp = i;
            int j = temp + h;
            if (less(list, j, i, comparator)){
                exch(list, i, j);
                while (less(list, j, j-h,comparator) && j-h >= 0){
                    exch(list, j-h, j);
                    j = j-h;
                }
            }

        }
    }
        @Override
    public @NotNull SorterType getType() {
        return SorterType.H;
    }
}
