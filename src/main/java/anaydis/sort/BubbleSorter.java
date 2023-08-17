package anaydis.sort;

import org.jetbrains.annotations.NotNull;

import java.util.Comparator;
import java.util.List;

public class BubbleSorter extends AbstractSorter{
    public BubbleSorter() {
        super(SorterType.BUBBLE);
    }

    @Override
    public <T> void sort(@NotNull Comparator<T> comparator, @NotNull List<T> list) {
        int size = list.size();
        for (int i = 0; i < size; i++){
            for (int j = 0; j < size-1; j++) {
                notifyBox(j, j+1);
                if (less(list, j + 1, j, comparator)){ // If j+1 < j -> exchange
                    exch(list, j, j + 1);
                }
            }
        }
    }

    @Override
    public @NotNull SorterType getType() {
        return SorterType.BUBBLE;
    }
}
