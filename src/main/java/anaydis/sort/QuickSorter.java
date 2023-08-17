package anaydis.sort;

import org.jetbrains.annotations.NotNull;

import java.util.Comparator;
import java.util.List;

public class QuickSorter extends AbstractSorter {
    public QuickSorter() {
        super(SorterType.QUICK);
    }

    @Override
    public <T> void sort(@NotNull Comparator<T> comparator, @NotNull List<T> list) {
        sort(comparator, list, 0, list.size()-1);
    }

    private <T> void sort(Comparator<T> comparator, List<T> list, int start, int end){
        if (end >= start){return;}

        int pivot = partition(comparator, list, start, end);

        sort(comparator, list, start, pivot-1);
        sort(comparator, list, pivot+1, end);
    }

    private <T> int partition(Comparator<T> comparator, List<T> list, int start, int end) {
        int i = start-1;

        for (int j= start; j <= end-1; j++){
            if (less(list, j, end, comparator)) {
                i++;
                exch(list, i, j);
            }
        }
        i++;
        exch(list, i, end);
        return i;
    }

    @Override
    public @NotNull SorterType getType() {
        return SorterType.QUICK;
    }
}
