package anaydis.sort;

import org.jetbrains.annotations.NotNull;

import java.util.Comparator;
import java.util.List;

public class SelectionSorter extends AbstractSorter {
    SelectionSorter(@NotNull SorterType type) {
        super(SorterType.SELECTION);
    }

    @Override
    public <T> void sort(@NotNull Comparator<T> comparator, @NotNull List<T> list) {
        int size = list.size();
        for (int i = 0; i < size; i++){
            int min = i;
            for (int j = i + 1; j < size; j++){
                if (less(list, j, min, comparator))
                    min = j;
            }
            exch(list, i, min);
        }
    }

    @Override
    public @NotNull SorterType getType() {
        return SorterType.SELECTION;
    }
}
