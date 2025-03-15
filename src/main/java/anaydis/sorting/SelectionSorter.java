package anaydis.sorting;

import anaydis.sort.SorterType;
import org.jetbrains.annotations.NotNull;

import java.util.Comparator;
import java.util.List;

public class SelectionSorter extends AbstractSorter {
    public SelectionSorter() {
        super(SorterType.SELECTION);
    }

    @Override
    public <T> void sort(@NotNull Comparator<T> comparator, @NotNull List<T> list) {
        int size = list.size();
        for (int i = 0; i < size; i++){
            int min = i;
            for (int j = i + 1; j < size; j++) {
                notifyBox(min, j);
                if (less(list, j, min, comparator)){
                    min = j;
                }
            }
            if(i != min)
                exch(list, i, min);
        }
    }

    @Override
    public @NotNull SorterType getType() {
        return SorterType.SELECTION;
    }
}
