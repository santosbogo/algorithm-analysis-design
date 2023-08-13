package anaydis.sort;

import org.jetbrains.annotations.NotNull;

import java.util.Comparator;
import java.util.List;

public class SelectionSorter extends AbstractSorter {
    @Override
    public <T> void sort(@NotNull Comparator<T> comparator, @NotNull List<T> list) {
        Object[] arrayList = list.toArray();
        int size = list.size();
        for (int i = 0; i < size; i++){
            int min = i;
            for (int j = i+1; j < size; j++){
                if (less(arrayList[j], arrayList[min], comparator))
                    min = j;
            }
            exch(arrayList, min, i);
        }
    }

    @Override
    public @NotNull SorterType getType() {
        return null;
    }
}
