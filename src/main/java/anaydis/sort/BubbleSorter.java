package anaydis.sort;

import org.jetbrains.annotations.NotNull;

import java.util.Comparator;
import java.util.List;

public class BubbleSorter extends AbstractSorter{
    @Override
    public <T> void sort(@NotNull Comparator<T> comparator, @NotNull List<T> list) {
        int size = list.size();
        for (int i = 0; i < size; i++){
            for (int j = 0; j < size-1; j++){
                if (less(list.get(j+1), list.get(j), comparator)) // If j+1 < j -> exchange
                    exch(list, j, j+1);
            }
        }
    }

    @Override
    public @NotNull SorterType getType() {
        return SorterType.BUBBLE;
    }
}
